import javax.print.DocFlavor;
import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Music {
    public static void playMusic(String filepath)
    {
        try
        {
            File musicPath = new File(filepath);

            if(musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                float dB = (float) (Math.log(1) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
                clip.start();
            }
            else
                {
                    System.out.println("Can't find file");
                }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void playMusic(String filepath, double volume)
    {
        try
        {
            File musicPath = new File(filepath);

            if(musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
                clip.start();
            }
            else
            {
                System.out.println("Can't find file");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public static void loopMusic(String filepath)
    {
        try
        {
            File musicPath = new File(filepath);

            if(musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
            else
            {
                System.out.println("Can't find file");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
