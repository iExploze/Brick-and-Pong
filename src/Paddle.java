import java.awt.*;

public class Paddle extends Brick {

    private static final int SCREEN_WIDTH = Main.SCREEN_WIDTH;
    private static final int SCREEN_HEIGHT = Main.SCREEN_HEIGHT;

    private int speed;

    public Paddle(int x, int y, int width, int height, Color color, int speed) {//constructor
        super(x, y, width, height, color, true);
        this.speed = speed;
    }

    public int getSpeed()//return speed
    {
        return speed;
    }

    public void setSpeed(int speed)//set the speed
    {
        this.speed = speed;
    }

    public void hit() {
        Music.playMusic("src/Resources/BounceSound.wav", 0.6);
        setVisible(true);
    }

    public void moveUp()//move up
    {
        this.setY(this.getY() - speed);
        if (this.getY() < 0) {
            this.setY(0);
        }
    }

    public void moveDown()//move down
    {
        this.setY(this.getY() + speed);
        if (this.getY() + this.getHeight() > SCREEN_HEIGHT - 5) {
            this.setY(SCREEN_HEIGHT - this.getHeight());
        }
    }

    public String toString()// to string method useful to detect the type of object that the ball collided with
    {
        return "Paddle";
    }
}
