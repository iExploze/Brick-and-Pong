import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ToggleButton extends Button implements MouseListener{
    private Image buttonOn = ImageLoader.loadImage("Resources/ButtonToggleOn.png");
    private Image buttonOff = ImageLoader.loadImage("Resources/ButtonToggleOff.png");
    private boolean temp;
    private boolean soundPlayed = false;
    private boolean mouseClicked = false;
    private Rectangle rectangle;
    public ToggleButton(Rectangle rectangle)
    {
        super(rectangle, null);
        this.rectangle =rectangle;
    }

    public void draw(Graphics g)
    {
        super.soundEffect();
        if(mouseClicked)
        {
            soundPlayed =false;
            super.setImage(buttonOn);
        }
        else {
            if(!soundPlayed)
            {
                Music.playMusic("src/Resources/mouseClick.wav", 0.5);
                soundPlayed = true;
            }
            super.setImage(buttonOff);
        }
        super.drawNoSound(g);
    }

    public void setState(boolean isON)
    {
        mouseClicked = isON;
    }

    public boolean getState()
    {
        return mouseClicked;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(rectangle.contains(MouseInfo.getPointerInfo().getLocation())&& !mouseClicked) {
            temp = true;
            mouseClicked = true;
            Music.playMusic("src/Resources/mouseClick.wav", 0.5);
        }
        else if(rectangle.contains(MouseInfo.getPointerInfo().getLocation())&& mouseClicked) {
            mouseClicked = false;
            Music.playMusic("src/Resources/mouseClick.wav", 0.5);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
