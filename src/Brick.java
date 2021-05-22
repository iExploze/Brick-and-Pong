import java.awt.*;
import java.util.ArrayList;

public class Brick {
    private boolean visible;
    private final ArrayList<Brick> brickList = new ArrayList<>();
    private Color color;
    private boolean gotHit = false;
    private int count = 0;
    private final Image hitMarker = ImageLoader.loadImage("Resources/Hit Marker.png");
    private int x, y, width, height;

    public Brick(int x, int y, int width, int height, Color color, boolean visible) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.visible = visible;
    }

    public static int getCentreY(Brick brick) {
        return (brick.y + brick.getHeight() / 2);
    }

    public static int getCentreX(Brick brick) {
        return (brick.x + brick.getWidth() / 2);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void draw(Graphics g) {
        if (this.isVisible()) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
        if (gotHit) {
            g.drawImage(hitMarker, getCentreX(this) - hitMarker.getWidth(null) / 2, getCentreY(this) - hitMarker.getHeight(null) / 2, null);
            count++;
            if (count > 5) {
                gotHit = false;
            }
        }
    }

    public static Color invert(Color color) {
        int a = color.getAlpha();
        int r = 255 - color.getRed();
        int g = 255 - color.getGreen();
        int b = 255 - color.getBlue();

        return new Color(r, g, b, a);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public void hit() {
        gotHit = true;
        this.setVisible(false);
        Music.playMusic("src/Resources/HitSound.wav", 0.03);
    }

    public String toString() {
        return "Brick";
    }

    public void addBrickList(ArrayList<Brick> brickList) {
        this.brickList.addAll(brickList);
    }
}
