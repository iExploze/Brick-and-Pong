import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Player extends JPanel implements KeyListener {
    private static final int SCREEN_WIDTH = Main.SCREEN_WIDTH;
    private static final int SCREEN_HEIGHT = Main.SCREEN_HEIGHT;

    private boolean UArrowPressed, DArrowPressed, WPressed, SPressed;//boolean used to check inputs

    private int score;
    private Paddle paddle;
    private boolean isRight;//side
    private Color savedColor;
    private ArrayList<Brick> brickList;//array list to contain all the bricks

    public Player(boolean isRight, int density, int speed, int size, Color paddleColor, Color brickColor) // custom constructor
    {
        score = 0;
        this.isRight = isRight;
        brickList = MakeBricks(100, 20, 3, density/8, density/20, isRight, brickColor);
        if(!isRight)
        {
            paddle = new Paddle(300,SCREEN_HEIGHT/2,20,size,paddleColor,speed);
        }
        else {
            paddle = new Paddle(SCREEN_WIDTH-300-20,SCREEN_HEIGHT/2,20,size,paddleColor,speed);
        }
    }

    public Player(boolean isRight)//default constructor, but still need side
    {
        score = 0;
        this.isRight = isRight;
        if(!isRight)
        {
            brickList = MakeBricks(100, 20, 3, 10, 12, false, Color.red);
            paddle = new Paddle(300,SCREEN_HEIGHT/2,20,100,Color.white,20);
            savedColor=Color.RED;
        }
        else {
            brickList = MakeBricks(100, 20, 3, 10, 12, true, Color.blue);
            paddle = new Paddle(SCREEN_WIDTH-300-20,SCREEN_HEIGHT/2,20,100,Color.white,20);
            savedColor=Color.blue;
        }
    }

    public void reset()
    {
        if(!isRight)
        {
            brickList = MakeBricks(100, 20, 3, 8, 12, false, savedColor);
            paddle = new Paddle(300,SCREEN_HEIGHT/2,20,100, paddle.getColor(),20);
        }
        else {
            brickList = MakeBricks(100, 20, 3, 8, 12, true, savedColor);
            paddle = new Paddle(SCREEN_WIDTH-300-20,SCREEN_HEIGHT/2,20,100, paddle.getColor(),20);
        }
    }

    public void setBrickColor(Color color)
    {
        savedColor =color;
        for(Brick i : brickList)
        {
            i.setColor(color);
        }
    }

    public void moveDraw(Graphics g)
    {
        this.move();
        this.draw(g);
    }

    public void move()//moves the paddle based on inputs
    {
        if(isRight) {
            if(UArrowPressed)
            {
                paddle.moveUp();
            }
            if(DArrowPressed)
            {
                paddle.moveDown();
            }
        }
        else {
            if(WPressed)
            {
                paddle.moveUp();
            }
            if(SPressed)
            {
                paddle.moveDown();
            }
        }
    }

    public void draw(Graphics g)//draw all the bricks and the paddle
    {
        for(Brick brick : brickList)
        {
            brick.draw(g);
        }
        paddle.draw(g);
    }

    public Color getBrickColor()
    {
        return savedColor;
    }

    public int getBrickCount()//get how many bricks are left
    {
        int count = 0;
        for(Brick brick : brickList)
        {
            if(brick.isVisible())
            {
                count++;
            }
        }
        return count;
    }

    public ArrayList<Brick> getBrickList()//get the list
    {
        return brickList;
    }

    public void setBrickList(ArrayList<Brick> list)
    {
        brickList.clear();
        for(Brick i : list)
        {
            brickList.add(i);
        }
    }

    public ArrayList<Brick> getVisibleBrickList()//get the list, but only the visible bricks
    {
        ArrayList<Brick> temp = new ArrayList<>();
        for(Brick i : brickList)
        {
            if(i.isVisible())
            {
                temp.add(i);
            }
        }
        return temp;
    }

    public void setPaddle(Paddle paddle)//set the paddle just incase
    {
        this.paddle = paddle;
    }

    public Paddle getPaddle()//return the paddle
    {
        return paddle;
    }

    public void addScore()//add to the score
    {
        this.score++;
    }

    public int getScore()//get the score
    {
        return score;
    }

    public void setScore(int score)//set the score
    {
        this.score = score;
    }

    public static double MinDistance(Rectangle rectangle1, Rectangle rectangle2)
    {
        double min_dist;
        //First calculate the center points of the two rectangles
        Point C1 = new Point();
        Point C2 = new Point();
        C1.x = rectangle1.x + (rectangle1.width / 2);
        C1.y = rectangle1.y + (rectangle1.height / 2);
        C2.x = rectangle2.x + (rectangle2.width / 2);
        C2.y = rectangle2.y + (rectangle2.height / 2);
        // Calculate the distance between the center points of the two rectangles in the X axis and Y axis directions respectively
        int Dx, Dy;
        Dx = Math.abs(C2.x - C1.x);
        Dy = Math.abs(C2.y - C1.y);
        //The two rectangles do not intersect, and there are two rectangles partially overlapping in the X-axis direction. The minimum distance is the distance between the lower line of the upper rectangle and the upper line of the lower rectangle
        if((Dx < ((rectangle1.width + rectangle2.width)/ 2)) && (Dy >= ((rectangle1.height + rectangle2.height) / 2)))
        {
            min_dist = Dy - ((rectangle1.height + rectangle2.height) / 2);
        }
        //The two rectangles do not intersect. There are two partially overlapping rectangles in the Y-axis direction. The minimum distance is the distance between the right line of the left rectangle and the left line of the right rectangle
        else if((Dx >= ((rectangle1.width + rectangle2.width)/ 2)) && (Dy < ((rectangle1.height + rectangle2.height) / 2)))
        {
            min_dist = Dx - ((rectangle1.width + rectangle2.width)/ 2);
        }
        //Two rectangles do not intersect, two rectangles that do not overlap in the X-axis and Y-axis directions, the minimum distance is the distance between the two closest vertices,
        // Using the Pythagorean theorem, it is easy to calculate this distance
        else if((Dx >= ((rectangle1.width + rectangle2.width)/ 2)) && (Dy >= ((rectangle1.height + rectangle2.height) / 2)))
        {
            int delta_x = Dx - ((rectangle1.width + rectangle2.width)/ 2);
            int delta_y = Dy - ((rectangle1.height + rectangle2.height)/ 2);
            min_dist = Math.sqrt(delta_x * delta_x  + delta_y * delta_y);
        }
        //The intersection of two rectangles, the minimum distance is negative, return -1
        else
        {
            min_dist = -1;
        }

        return min_dist;
    }

    private static ArrayList<Brick> MakeBricks(int x, int width, int gapSize, int columns, int rows, boolean isRightSide, Color color)//custom function to make all the bricks and returns a list
    {
        ArrayList<Brick> arrayList = new ArrayList<>();
        if(isRightSide)
        {
            x=SCREEN_WIDTH-(columns*width+(columns+1)*gapSize+x);
            int height = (SCREEN_HEIGHT-10-(gapSize*(rows+1)))/rows;
            for(int i = 0; i < columns; i++)
            {
                int y = 5+gapSize;
                for(int j = 0; j < rows; j++)
                {
                    arrayList.add(new Brick(x, y, width, height, color, true));
                    y=y+gapSize+height;
                }
                x = width+gapSize+x;
            }
        }
        else
        {
            int height = (SCREEN_HEIGHT-10-(gapSize*(rows+1)))/rows;
            for(int i = 0; i < columns; i++)
            {
                int y = 5+gapSize;
                for(int j = 0; j < rows; j++)
                {
                    arrayList.add(new Brick(x, y, width, height, color, true));
                    y=y+gapSize+height;
                }
                x = width+gapSize+x;
            }
        }
        return arrayList;
    }

    public void addExplosiveBrick(int amount)
    {
        ArrayList<Brick> temp = new ArrayList<>();
        temp.clear();
        for(Brick i : brickList)
        {
            temp.add(i);
        }
        for(int i = 0; i < amount; i++)
        {
            int random = (int)(Math.random()*temp.size());
            if(temp.get(random).toString().equals("Explosive Brick"))
            {
                i--;
            }
            else
            {
                temp.set(random, new ExplosiveBrick(brickList.get(random).getX(), brickList.get(random).getY(), brickList.get(random).getWidth(), brickList.get(random).getHeight(), Brick.invert(brickList.get(random).getColor()), true));
            }
        }
        brickList.clear();
        brickList.addAll(temp);
        for(Brick i : brickList)
        {
            i.addBrickList(brickList);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP)
            UArrowPressed = true;

        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            DArrowPressed = true;

        if(e.getKeyCode() == KeyEvent.VK_W)
            WPressed = true;

        if(e.getKeyCode() == KeyEvent.VK_S)
            SPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP)
            UArrowPressed = false;

        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            DArrowPressed = false;

        if(e.getKeyCode() == KeyEvent.VK_W)
            WPressed = false;

        if(e.getKeyCode() == KeyEvent.VK_S)
            SPressed = false;
    }
}
