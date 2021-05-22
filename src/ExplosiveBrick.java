import java.awt.*;
import java.util.ArrayList;

public class ExplosiveBrick extends Brick {
    //TODO add comments

    private final ArrayList<Brick> brickList = new ArrayList<>();
    private boolean gotHit = false;
    private int count = 0;
    Image explosionImage = ImageLoader.loadImage("Resources/Explosion.png");

    public ExplosiveBrick(int x, int y, int width, int height, Color color, boolean isVisible) {
        super(x, y, width, height, color, isVisible);
    }

    public void draw(Graphics g) {
        super.draw(g);
        if (gotHit) {
            g.drawImage(explosionImage, this.getX() - this.getWidth(), this.getY() - this.getHeight(), this.getWidth() * 3, this.getHeight() * 3, null);
            count++;
        }
        if (count > 5) {
            gotHit = false;
        }
    }

    public void addBrickList(ArrayList<Brick> brickList) {
        this.brickList.addAll(brickList);
    }

    @Override
    public void hit() {
        Rectangle ExplosionCollider = new Rectangle(this.getX() - this.getWidth(), this.getY() - this.getHeight(), this.getWidth() * 3, this.getHeight() * 3);
        super.hit();
        gotHit = true;
        Music.playMusic("src/Resources/ExplosionSound.wav", 0.25);
        for (Brick i : brickList) {
            if (ExplosionCollider.intersects(i.getRect()) && i.isVisible() && i != this) {
                i.hit();
            }
        }
    }

    @Override
    public String toString() {
        return "Explosive Brick";
    }
}
