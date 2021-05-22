import javax.swing.*;

public class Main {

    public static final int SCREEN_WIDTH = 1600;
    public static final int SCREEN_HEIGHT = 900;

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setBounds(0, 0, SCREEN_WIDTH + 15, SCREEN_HEIGHT + 35);
        //due to the edge missing on the JFrame for intellij and the newest blueJ
        //added this fix this missing edge

        frame.setTitle("Brick and Pong");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BrickBreakerGame game = new BrickBreakerGame();
        frame.add(game);
        frame.setVisible(true);
    }


}
