import java.awt.*;
import java.util.ArrayList;

public class Ball {
    //test
    //instance variable
    private int x;    //top left x value
    private int y;    //top left y value
    private int size; //diameter
    private int dx;   //change in x
    private int dy;   //change in y
    private Color color;
    private boolean trail;
    private final ArrayList<Rectangle> Tail = new ArrayList<>();

    private static final int SCREEN_WIDTH = Main.SCREEN_WIDTH;
    private static final int SCREEN_HEIGHT = Main.SCREEN_HEIGHT;

    public Ball(int x, int y, int size, int dx, int dy, Color color, boolean trail) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
        this.trail = trail;
    }

    public Ball() {
        this.x = SCREEN_WIDTH / 2;
        this.y = SCREEN_HEIGHT / 2;
        this.size = 30;
        int randomX = (int) ((21 * Math.random()) - 10);
        this.dx = randomX;
        int randomY = (int) ((21 * Math.random()) - 10);
        this.dy = randomY;
        this.trail = false;

        if (randomX == 0 || randomY == 0) {
            while (randomX == 0 || randomY == 0) {
                randomX = (int) ((21 * Math.random()) - 10);
                this.dx = randomX;
                randomY = (int) ((21 * Math.random()) - 10);
                this.dy = randomY;
            }
        }

        this.color = Color.cyan;
        this.trail = true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isTrailing() {
        return trail;
    }

    public void setTrail(boolean isTrailing) {
        this.trail = isTrailing;
    }

    public void move() {
        if (x < 5) {
            //dx = dx+5;
            this.dx = (int) (11 * Math.random() + 5);
            Music.playMusic("src/Resources/BounceSound.wav", 0.2);
        } else if ((x + size) > SCREEN_WIDTH - 5) {
            //dx = dx+5;
            this.dx = (int) (-11 * Math.random() - 5);
            Music.playMusic("src/Resources/BounceSound.wav", 0.2);
        }

        if (y < 5) {
            //dy = dy+5;
            this.dy = (int) (11 * Math.random() + 5);
            Music.playMusic("src/Resources/BounceSound.wav", 0.2);
        } else if ((y + size) > SCREEN_HEIGHT - 5) {
            //dy = dy+5;
            this.dy = (int) (-11 * Math.random() - 5);
            Music.playMusic("src/Resources/BounceSound.wav", 0.2);
        }

        x = x + dx;
        y = y + dy;
    }

    public void draw(Graphics g) {
        if (trail) {
            Tail.add(new Rectangle(x, y, size, size));
            for (Rectangle i : Tail) {
                i.setSize((int) i.getWidth() - 1, (int) i.getHeight() - 1);
                g.setColor(Brick.invert(color));
                g.fillOval((int) i.getX(), (int) i.getY(), (int) i.getWidth(), (int) i.getHeight());
            }
        }
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, size, size);
    }

    public void CheckCollidesWith(Brick brick) {
        Rectangle collider = new Rectangle(x, y, size, size);
        Rectangle brickCollider = brick.getRect();

        if (collider.intersects(brickCollider) && brick.isVisible()) {
            brick.hit();

            if (x < brick.getX() || x + size > brick.getX() + brick.getWidth()) {
                if (brick.toString().equals("Paddle")) {
                    int random;
                    if (dx > 0) {
                        random = (int) (-11 * Math.random() - 5);
                    } else {
                        random = (int) (11 * Math.random() + 5);
                    }
                    dx = random;
                } else if (y < brick.getY() || y + size > brick.getY() + brick.getHeight()) {
                    if (brick.toString().equals("Paddle")) {
                        int random;
                        if (dy > 0) {
                            random = (int) (-11 * Math.random() - 5);
                        } else {
                            random = (int) (11 * Math.random() + 5);
                        }
                        dy = random;
                    } else
                        dy = -dy;
                } else
                    dx = -dx;
            }
        }
    }

    public void CheckCollidesWith(ArrayList<Brick> list) {
        for (Brick brick : list) {
            CheckCollidesWith(brick);
        }
    }

    public void reset() {
        this.x = SCREEN_WIDTH / 2;
        this.y = SCREEN_HEIGHT / 2;
        this.size = 30;
        int randomX = (int) ((21 * Math.random()) - 10);
        this.dx = randomX;
        int randomY = (int) ((21 * Math.random()) - 10);
        this.dy = randomY;
        this.trail = false;

        if (randomX == 0 || randomY == 0) {
            while (randomX == 0 || randomY == 0) {
                randomX = (int) ((21 * Math.random()) - 10);
                this.dx = randomX;
                randomY = (int) ((21 * Math.random()) - 10);
                this.dy = randomY;
            }
        }

        this.color = this.getColor();
        this.trail = true;
    }
}
