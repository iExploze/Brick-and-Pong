import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;

public class Button implements MouseListener {
    private Rectangle rectangle;
    private Image image;
    private boolean mouseClicked;
    private boolean musicPlayed;

    public Button(Rectangle rectangle, Image image)
    {
        this.rectangle = rectangle;
        this.image = image;
    }

    public void draw(Graphics g)
    {
        if(this.image != null)
        {
            g.drawImage(image,rectangle.x,rectangle.y,rectangle.width,rectangle.height,null);
            soundEffect();
            if(rectangle.contains(MouseInfo.getPointerInfo().getLocation()))
            {
                g.drawImage(image,rectangle.x-20,rectangle.y-20,rectangle.width+40,rectangle.height+40,null);
            }
        }
    }

    protected void soundEffect()
    {
        if(rectangle.contains(MouseInfo.getPointerInfo().getLocation())&&!musicPlayed)
        {
            Music.playMusic("src/Resources/mouseHover.wav", 2);
            musicPlayed = true;
        }
        if(!rectangle.contains(MouseInfo.getPointerInfo().getLocation()))
        {
            musicPlayed = false;
        }
    }

    protected void drawNoSound(Graphics g)
    {
        g.drawImage(image,rectangle.x,rectangle.y,rectangle.width,rectangle.height,null);
        if(rectangle.contains(MouseInfo.getPointerInfo().getLocation()))
        {
            g.drawImage(image,rectangle.x-10,rectangle.y-10,rectangle.width+20,rectangle.height+20,null);
        }
    }

    protected void setImage(Image image)
    {
        this.image = image;
    }

    public boolean isClicked()
    {
        return mouseClicked;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(rectangle.contains(MouseInfo.getPointerInfo().getLocation())&& !mouseClicked) {
            mouseClicked = true;
            Music.playMusic("src/Resources/mouseClick.wav", 0.5);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
