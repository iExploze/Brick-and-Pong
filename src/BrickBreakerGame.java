import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BrickBreakerGame extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    private static final int SCREEN_WIDTH = Main.SCREEN_WIDTH;
    private static final int SCREEN_HEIGHT = Main.SCREEN_HEIGHT;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Images for Menu Screen
    private final Image StartImage = ImageLoader.loadImage("Resources/Start.png");
    private final Image LogoImage = ImageLoader.loadImage("Resources/Logo.png");
    private final Image OptionImage = ImageLoader.loadImage("Resources/Option.png");

    //Images for Mode Screen
    private final Image ClassicModeImage = ImageLoader.loadImage("Resources/Classic Mode Button.png");
    private final Image MayhemModeImage = ImageLoader.loadImage("Resources/3Ball Mode Button.png");
    private final Image ExplosiveBrickModeImage = ImageLoader.loadImage("Resources/Explosive Mode Button.png");

    //Images for Game Screen

    //Images for Option Screen
    private final Image ExitImage = ImageLoader.loadImage("Resources/Exit Button.png");
    private final Image UpArrowImage = ImageLoader.loadImage("Resources/UpArrow.png");
    private final Image DownArrowImage = ImageLoader.loadImage("Resources/DownArrow.png");
    private final Image ResetButtonImage = ImageLoader.loadImage("Resources/Reset Button.png");

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Objects for Menu Screen
    private final Button startButton;
    private final Button OptionButton;

    //Objects for Mode Screen
    private final Button ClassicModeButton;
    private final Button MayhemModeButton;
    private final Button ExplosiveModeButton;
    private String GameMode;
    private final String[] GameModeArray = {"Classic", "Mayhem", "Explosive", "Inverse", "Dodge", "Invisible"};

    //Objects for Game Screen
    private Ball ball1;
    private Player playerLeft, playerRight;
    private int TimerCounter;
    private int clock;
    private int scoreToWin;
    private int GameTimeChecker;

    private final Ball[] MayhemBallArray = new Ball[3];

    private int RoundTime;
    private int suddenDeathLimit;
    private int DecayRate;

    private boolean isRightBot;
    private boolean isLeftBot;
    //Objects for Option Screen
    private final Button exitButton;
    private final ToggleButton RPaddleSelected;
    private final ToggleButton LPaddleSelected;
    private final ToggleButton RBrickSelected;
    private final ToggleButton LBrickSelected;
    private final Button UpButtonRed;
    private final Button UpButtonGreen;
    private final Button UpButtonBlue;
    private final Button DownButtonRed;
    private final Button DownButtonBlue;
    private final Button DownButtonGreen;
    private final Button UpDecayRate;
    private final Button DownDecayRate;
    private final Button UpScoreToWin;
    private final Button DownScoreToWin;
    private final Button UpSuddenDeathLimit;
    private final Button DownSuddenDeathLimit;
    private final Button UpGameTime;
    private final Button DownGameTime;
    private final ToggleButton suddenDeathOn;
    private final ToggleButton BallColorSelected;
    private final Button resetButton;
    private final ToggleButton RightBotButton;
    private final ToggleButton LeftBotButton;

    private int red = 255;
    private int green = 255;
    private int blue = 255;
    private boolean clicked = false;

    //Objects for Win Screen
    private boolean RightWon;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final Timer timer;
    private int Screen = 0;

    public BrickBreakerGame() {
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        ActionListener repaint = e -> repaint();
        timer = new Timer(1, repaint);
        timer.start();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        //initialization for objects in Menu Screen, only if need it to be initialized once
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        startButton = new Button(new Rectangle(SCREEN_WIDTH / 2 - 200, SCREEN_HEIGHT / 2 - 100, 400, 200), StartImage);
        addMouseListener(startButton);
        OptionButton = new Button(new Rectangle(SCREEN_WIDTH / 2 - 200, SCREEN_HEIGHT / 2 + 150, 400, 200), OptionImage);
        addMouseListener(OptionButton);

        //initialization for objects in Mode Screen, only if need it to be initialized once
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ClassicModeButton = new Button(new Rectangle(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4, 200, 100), ClassicModeImage);
        addMouseListener(ClassicModeButton);

        MayhemModeButton = new Button(new Rectangle(SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 4, 200, 100), MayhemModeImage);
        addMouseListener(MayhemModeButton);

        ExplosiveModeButton = new Button(new Rectangle((SCREEN_WIDTH / 2 + SCREEN_WIDTH / 8), SCREEN_HEIGHT / 4, 200, 100), ExplosiveBrickModeImage);
        addMouseListener(ExplosiveModeButton);

        //initialization for objects in Game Screen/Option Screen, only if need it to be initialized once
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        initialization();
        RPaddleSelected = new ToggleButton(new Rectangle(400, 70, 50, 50));
        addMouseListener(RPaddleSelected);

        LPaddleSelected = new ToggleButton(new Rectangle(400, 270, 50, 50));
        addMouseListener(LPaddleSelected);

        RBrickSelected = new ToggleButton(new Rectangle(400, 130, 50, 50));
        addMouseListener(RBrickSelected);

        LBrickSelected = new ToggleButton(new Rectangle(400, 330, 50, 50));
        addMouseListener(LBrickSelected);

        UpButtonRed = new Button(new Rectangle(50, 550, 100, 100), UpArrowImage);
        addMouseListener(UpButtonRed);

        UpButtonGreen = new Button(new Rectangle(370, 550, 100, 100), UpArrowImage);
        addMouseListener(UpButtonGreen);

        UpButtonBlue = new Button(new Rectangle(700, 550, 100, 100), UpArrowImage);
        addMouseListener(UpButtonBlue);

        DownButtonRed = new Button(new Rectangle(50, 670, 100, 100), DownArrowImage);
        addMouseListener(DownButtonRed);

        DownButtonGreen = new Button(new Rectangle(370, 670, 100, 100), DownArrowImage);
        addMouseListener(DownButtonGreen);

        DownButtonBlue = new Button(new Rectangle(700, 670, 100, 100), DownArrowImage);
        addMouseListener(DownButtonBlue);

        resetButton = new Button(new Rectangle(SCREEN_WIDTH - 150, SCREEN_HEIGHT - 75, 100, 50), ResetButtonImage);
        addMouseListener(resetButton);

        BallColorSelected = new ToggleButton(new Rectangle(SCREEN_WIDTH - 400, 80, 50, 50));
        addMouseListener(BallColorSelected);

        UpDecayRate = new Button(new Rectangle(SCREEN_WIDTH - 250, 200, 100, 100), UpArrowImage);
        addMouseListener(UpDecayRate);

        DownDecayRate = new Button(new Rectangle(SCREEN_WIDTH - 150, 200, 100, 100), DownArrowImage);
        addMouseListener(DownDecayRate);

        UpScoreToWin = new Button(new Rectangle(SCREEN_WIDTH - 250, 330, 100, 100), UpArrowImage);
        addMouseListener(UpScoreToWin);

        DownScoreToWin = new Button(new Rectangle(SCREEN_WIDTH - 150, 330, 100, 100), DownArrowImage);
        addMouseListener(DownScoreToWin);

        UpSuddenDeathLimit = new Button(new Rectangle(SCREEN_WIDTH - 250, 460, 100, 100), UpArrowImage);
        addMouseListener(UpSuddenDeathLimit);

        DownSuddenDeathLimit = new Button(new Rectangle(SCREEN_WIDTH - 150, 460, 100, 100), DownArrowImage);
        addMouseListener(DownSuddenDeathLimit);

        UpGameTime = new Button(new Rectangle(SCREEN_WIDTH - 250, 600, 100, 100), UpArrowImage);
        addMouseListener(UpGameTime);

        DownGameTime = new Button(new Rectangle(SCREEN_WIDTH - 150, 600, 100, 100), DownArrowImage);
        addMouseListener(DownGameTime);

        suddenDeathOn = new ToggleButton(new Rectangle(SCREEN_WIDTH - 400, SCREEN_HEIGHT - 140, 50, 50));
        //.setState(true);
        addMouseListener(suddenDeathOn);

        RightBotButton = new ToggleButton(new Rectangle(800, 70, 50, 50));
        addMouseListener(RightBotButton);

        LeftBotButton = new ToggleButton(new Rectangle(800, 270, 50, 50));
        addMouseListener(LeftBotButton);

        //initialization for objects in Setting Screen, only if need it to be initialized once
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        exitButton = new Button(new Rectangle(SCREEN_WIDTH / 2 - 60, SCREEN_HEIGHT - 75, 100, 50), ExitImage);
        addMouseListener(exitButton);
    }

    public void paint(Graphics g) {
        clock++;//counts the time3
        TimerCounter = clock / 60;
        if (Screen == 0) {
            MenuScreen(g);
        } else if (Screen == 1) {
            ModeScreen(g);
        } else if (Screen == 2) {
            GameScreen(g);
        } else if (Screen == 3) {
            SettingsScreen(g);
        } else if (Screen == 4) {
            winScreen(g);
        }
    }

    private void MenuScreen(Graphics g)// Screen == 0
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, (SCREEN_WIDTH), (SCREEN_HEIGHT));

        g.drawImage(LogoImage, 0, 0, null);
        if (startButton.isClicked()) {
            playerLeft.setScore(0);
            playerRight.setScore(0);
            Screen = 1;//go to mode screen
        }
        if (OptionButton.isClicked()) {
            Screen = 3;//go to settings screen
        }
        OptionButton.draw(g);
        startButton.draw(g);
    }

    private void ModeScreen(Graphics g)// Screen == 1
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, (SCREEN_WIDTH), (SCREEN_HEIGHT));

        g.setColor(Color.white);
        g.setFont(new Font("Monospaced", Font.BOLD, 40));
        g.drawString("More mode to be added", SCREEN_WIDTH / 2 - g.getFontMetrics().stringWidth("More mode to be added") / 2, SCREEN_HEIGHT / 2 + 100);
        if (ClassicModeButton.isClicked()) {
            playerRight.reset();
            playerLeft.reset();
            GameMode = GameModeArray[0];
            Screen = 2;
            clock = 0;

            ball1.reset();
        }
        ClassicModeButton.draw(g);

        if (MayhemModeButton.isClicked()) {
            playerRight.reset();
            playerLeft.reset();
            GameMode = GameModeArray[1];
            Screen = 2;
            clock = 0;

            for (int i = 0; i < MayhemBallArray.length; i++) {
                MayhemBallArray[i] = new Ball();
                MayhemBallArray[i].setColor(ball1.getColor());
            }
        }
        MayhemModeButton.draw(g);

        if (ExplosiveModeButton.isClicked()) {
            playerRight.reset();
            playerLeft.reset();
            GameMode = GameModeArray[2];
            Screen = 2;
            clock = 0;

            playerLeft.addExplosiveBrick(20);
            playerRight.addExplosiveBrick(20);
            ball1.reset();
        }
        ExplosiveModeButton.draw(g);

        if (exitButton.isClicked()) {
            Screen = 0;
        }
        exitButton.draw(g);
    }

    private void GameScreen(Graphics g)// Screen == 2// scores and painting the background
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, (SCREEN_WIDTH), (SCREEN_HEIGHT));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////sudden death
        if (RoundTime - TimerCounter < 0 && playerRight.getBrickCount() > suddenDeathLimit && playerLeft.getBrickCount() > suddenDeathLimit && suddenDeathOn.getState()) {
            if (GameTimeChecker != TimerCounter * DecayRate) {
                GameTimeChecker++;
                int random;

                ArrayList<Brick> temp = new ArrayList<>(playerLeft.getVisibleBrickList());
                random = (int) (temp.size() * Math.random());
                temp.get(random).hit();

                temp.clear();
                temp.addAll(playerRight.getVisibleBrickList());
                random = (int) (temp.size() * Math.random());
                temp.get(random).hit();
            }
        } else {
            GameTimeChecker = TimerCounter * DecayRate;
        }
        switch (GameMode) {
            case "Classic" -> {
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////movement
                ball1.CheckCollidesWith(playerLeft.getPaddle());
                ball1.CheckCollidesWith(playerRight.getPaddle());
                ball1.CheckCollidesWith(playerRight.getBrickList());
                ball1.CheckCollidesWith(playerLeft.getBrickList());
                ball1.move();
                ball1.draw(g);
                if (isLeftBot) {
                    if ((ball1.getX() + ball1.getSize() < playerLeft.getPaddle().getX() + playerLeft.getPaddle().getWidth() + 175)
                            && (ball1.getX() > playerLeft.getPaddle().getX())) {
                        if (ball1.getY() < playerLeft.getPaddle().getY()) {
                            playerLeft.getPaddle().moveUp();
                        } else if (ball1.getY() + ball1.getSize() > playerLeft.getPaddle().getY() + playerLeft.getPaddle().getHeight()) {
                            playerLeft.getPaddle().moveDown();
                        }
                    }
                }
                if (isRightBot) {
                    if ((ball1.getX() > playerRight.getPaddle().getX() - 175)
                            && (ball1.getX() + ball1.getSize() < playerRight.getPaddle().getX())) {
                        if (ball1.getY() < playerRight.getPaddle().getY()) {
                            playerRight.getPaddle().moveUp();
                        } else if (ball1.getY() + ball1.getSize() > playerRight.getPaddle().getY() + playerRight.getPaddle().getHeight()) {
                            playerRight.getPaddle().moveDown();
                        }
                    }
                }
                playerLeft.moveDraw(g);
                playerRight.moveDraw(g);
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////winner check
                if (playerRight.getBrickCount() == 0 || playerLeft.getBrickCount() == 0) {
                    if (playerLeft.getBrickCount() != 0)
                        playerLeft.addScore();
                    if (playerRight.getBrickCount() != 0)
                        playerRight.addScore();

                    ball1.reset();
                    playerLeft.reset();
                    playerRight.reset();
                    clock = 0;
                    GameTimeChecker = 0;
                }
            }
            case "Mayhem" -> {
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////movement
                for (Ball i : MayhemBallArray) {
                    i.move();
                    i.CheckCollidesWith(playerRight.getBrickList());
                    i.CheckCollidesWith(playerLeft.getBrickList());
                    i.CheckCollidesWith(playerRight.getPaddle());
                    i.CheckCollidesWith(playerLeft.getPaddle());
                    i.draw(g);
                }
                if (isLeftBot) {
                    Ball closestBall = new Ball();
                    double closestD = 10000000;
                    for (Ball i : MayhemBallArray) {
                        if (Player.MinDistance(i.getRect(), playerLeft.getPaddle().getRect()) < closestD) {
                            closestD = Player.MinDistance(i.getRect(), playerLeft.getPaddle().getRect());
                            closestBall = i;
                        }
                    }
                    if ((closestBall.getX() + closestBall.getSize() < playerLeft.getPaddle().getX() + playerLeft.getPaddle().getWidth() + 175)
                            && (closestBall.getX() > playerLeft.getPaddle().getX())) {
                        if (closestBall.getY() < playerLeft.getPaddle().getY()) {
                            playerLeft.getPaddle().moveUp();
                        } else if (closestBall.getY() + closestBall.getSize() > playerLeft.getPaddle().getY() + playerLeft.getPaddle().getHeight()) {
                            playerLeft.getPaddle().moveDown();
                        }
                    }
                }
                if (isRightBot) {
                    Ball closestBall = new Ball();
                    double closestD = 10000000;
                    for (Ball i : MayhemBallArray) {
                        if (Player.MinDistance(i.getRect(), playerRight.getPaddle().getRect()) < closestD) {
                            closestD = Player.MinDistance(i.getRect(), playerRight.getPaddle().getRect());
                            closestBall = i;
                        }
                    }
                    if ((closestBall.getX() > playerRight.getPaddle().getX() - 175)
                            && (closestBall.getX() + closestBall.getSize() < playerRight.getPaddle().getX())) {
                        if (closestBall.getY() < playerRight.getPaddle().getY()) {
                            playerRight.getPaddle().moveUp();
                        } else if (closestBall.getY() + closestBall.getSize() > playerRight.getPaddle().getY() + playerRight.getPaddle().getHeight()) {
                            playerRight.getPaddle().moveDown();
                        }
                    }
                }
                playerLeft.moveDraw(g);
                playerRight.moveDraw(g);
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////winner check
                if (playerRight.getBrickCount() == 0 || playerLeft.getBrickCount() == 0) {
                    if (playerLeft.getBrickCount() != 0)
                        playerLeft.addScore();
                    if (playerRight.getBrickCount() != 0)
                        playerRight.addScore();

                    for (Ball ball : MayhemBallArray) {
                        ball.reset();
                        ball.setColor(ball1.getColor());
                    }
                    playerRight.reset();
                    playerLeft.reset();
                    clock = 0;
                    GameTimeChecker = 0;
                }
            }
            case "Explosive" -> {
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////movement
                ball1.CheckCollidesWith(playerLeft.getPaddle());
                ball1.CheckCollidesWith(playerRight.getPaddle());
                ball1.CheckCollidesWith(playerRight.getBrickList());
                ball1.CheckCollidesWith(playerLeft.getBrickList());
                ball1.move();
                ball1.draw(g);
                if (isLeftBot) {
                    if ((ball1.getX() + ball1.getSize() < playerLeft.getPaddle().getX() + playerLeft.getPaddle().getWidth() + 175)
                            && (ball1.getX() > playerLeft.getPaddle().getX())) {
                        if (ball1.getY() < playerLeft.getPaddle().getY()) {
                            playerLeft.getPaddle().moveUp();
                        } else if (ball1.getY() + ball1.getSize() > playerLeft.getPaddle().getY() + playerLeft.getPaddle().getHeight()) {
                            playerLeft.getPaddle().moveDown();
                        }
                    }
                }
                if (isRightBot) {
                    if ((ball1.getX() > playerRight.getPaddle().getX() - 175)
                            && (ball1.getX() + ball1.getSize() < playerRight.getPaddle().getX())) {
                        if (ball1.getY() < playerRight.getPaddle().getY()) {
                            playerRight.getPaddle().moveUp();
                        } else if (ball1.getY() + ball1.getSize() > playerRight.getPaddle().getY() + playerRight.getPaddle().getHeight()) {
                            playerRight.getPaddle().moveDown();
                        }
                    }
                }
                playerLeft.moveDraw(g);
                playerRight.moveDraw(g);
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////winner check
                if (playerRight.getBrickCount() == 0 || playerLeft.getBrickCount() == 0) {
                    if (playerLeft.getBrickCount() != 0)
                        playerLeft.addScore();
                    if (playerRight.getBrickCount() != 0)
                        playerRight.addScore();

                    ball1.reset();
                    playerRight.reset();
                    playerLeft.reset();
                    playerLeft.addExplosiveBrick(20);
                    playerRight.addExplosiveBrick(20);
                    clock = 0;
                    GameTimeChecker = 0;
                }
            }
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Score
        if (playerRight.getScore() == scoreToWin) {
            RightWon = true;
            Screen = 4;
        } else if (playerLeft.getScore() == scoreToWin) {
            RightWon = false;
            Screen = 4;
        }

        g.setColor(Color.white);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.drawString("Score: ", SCREEN_WIDTH / 2 - g.getFontMetrics().stringWidth("Score: ") - g.getFontMetrics().stringWidth(String.valueOf(playerLeft.getScore())) - 50, 50);
        g.drawString("Score: ", SCREEN_WIDTH / 2 + 50, 50);
        g.drawString(String.valueOf(playerLeft.getScore()), SCREEN_WIDTH / 2 - g.getFontMetrics().stringWidth(String.valueOf(playerLeft.getScore())) - 50, 50);
        g.drawString(String.valueOf(playerRight.getScore()), SCREEN_WIDTH / 2 + 50 + g.getFontMetrics().stringWidth("Score: "), 50);

        g.setFont(new Font("Monospaced", Font.PLAIN, 40));
        g.drawString(String.valueOf(RoundTime - TimerCounter), SCREEN_WIDTH / 2 - g.getFontMetrics().stringWidth(String.valueOf(TimerCounter)) / 2, SCREEN_HEIGHT / 2 - 20);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////exit button
        exitButton.draw(g);
        if (exitButton.isClicked()) {
            Screen = 0;
        }
    }

    private void SettingsScreen(Graphics g)// Screen == 3
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, (SCREEN_WIDTH), (SCREEN_HEIGHT));

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 50));
        g.drawString("Right Settings:", 50, 50);
        g.drawString("Left Settings:", 50, 250);
        g.drawString("Global Settings: ", SCREEN_WIDTH - g.getFontMetrics().stringWidth("Global Settings: ") - 200, 50);
        g.drawString("Color Changer: ", 50, 450);

        g.setFont(new Font("Monospaced", Font.PLAIN, 30));
        g.drawString("Paddle Color: ", 50, 100);
        g.drawString("is Bot Player: ", 500, 100);
        g.drawString("Brick Color: ", 50, 100 + 50);
        g.drawString("Paddle Color: ", 50, 300);
        g.drawString("is Bot Player: ", 500, 300);
        g.drawString("Brick Color: ", 50, 300 + 50);
        g.drawString("Ball Color: ", SCREEN_WIDTH - g.getFontMetrics().stringWidth("Global Settings: ") - 400, 110);
        g.drawString("Decay Rate: " + DecayRate, SCREEN_WIDTH - g.getFontMetrics().stringWidth("Global Settings: ") - 400, 250);
        g.drawString("Score to Win: " + scoreToWin, SCREEN_WIDTH - g.getFontMetrics().stringWidth("Global Settings: ") - 400, SCREEN_HEIGHT / 2 - 80);
        g.drawString("Sudden Death Limit: " + suddenDeathLimit, SCREEN_WIDTH - g.getFontMetrics().stringWidth("Global Settings: ") - 400, SCREEN_HEIGHT / 2 + 60);
        g.drawString("Game Time: " + RoundTime, SCREEN_WIDTH - g.getFontMetrics().stringWidth("Global Settings: ") - 400, SCREEN_HEIGHT / 2 + 200);
        g.drawString("Sudden Death On: ", SCREEN_WIDTH - g.getFontMetrics().stringWidth("Sudden Death On: ") - 400, SCREEN_HEIGHT - 110);

        g.setColor(ball1.getColor());
        g.fillRect(SCREEN_WIDTH - g.getFontMetrics().stringWidth("Global Settings: ") - 180, 80, 50, 50);
        g.setColor(playerRight.getPaddle().getColor());
        g.fillRect(50 + g.getFontMetrics().stringWidth("Paddle Color: "), 100 - 30, 50, 50);
        g.setColor(playerRight.getBrickColor());
        g.fillRect(50 + g.getFontMetrics().stringWidth("Paddle Color: "), 100 + 30, 50, 50);
        g.setColor(playerLeft.getPaddle().getColor());
        g.fillRect(50 + g.getFontMetrics().stringWidth("Paddle Color: "), 300 - 30, 50, 50);
        g.setColor(playerLeft.getBrickColor());
        g.fillRect(50 + g.getFontMetrics().stringWidth("Paddle Color: "), 300 + 30, 50, 50);

        g.setFont(new Font("Monospaced", Font.PLAIN, 45));
        g.setColor(Color.white);
        g.drawString("Red: " + red + " Green: " + green + " Blue: " + blue, 50, 500);

        if (RPaddleSelected.getState()) {
            playerRight.getPaddle().setColor(new Color(red, green, blue));
        }
        if (LPaddleSelected.getState()) {
            playerLeft.getPaddle().setColor(new Color(red, green, blue));
        }
        if (RBrickSelected.getState()) {
            playerRight.setBrickColor(new Color(red, green, blue));
        }
        if (LBrickSelected.getState()) {
            playerLeft.setBrickColor(new Color(red, green, blue));
        }
        if (BallColorSelected.getState()) {
            ball1.setColor(new Color(red, green, blue));
        }
        isLeftBot = LeftBotButton.getState();
        isRightBot = RightBotButton.getState();

        if (resetButton.isClicked()) {
            RPaddleSelected.setState(false);
            LPaddleSelected.setState(false);
            RBrickSelected.setState(false);
            LBrickSelected.setState(false);
            BallColorSelected.setState(false);
            suddenDeathOn.setState(true);

            initialization();
        } else if (exitButton.isClicked()) {
            Screen = 0;
        } else if (UpButtonRed.isClicked()) {
            if (red < 255) {
                red++;
            }
        } else if (DownButtonRed.isClicked()) {
            if (red > 0) {
                red--;
            }
        } else if (UpButtonGreen.isClicked()) {
            if (green < 255) {
                green++;
            }
        } else if (DownButtonGreen.isClicked()) {
            if (green > 0) {
                green--;
            }
        } else if (UpButtonBlue.isClicked()) {
            if (blue < 255) {
                blue++;
            }
        } else if (DownButtonBlue.isClicked()) {
            if (blue > 0) {
                blue--;
            }
        }
        if (UpGameTime.isClicked()) {
            if (RoundTime < 180) {
                RoundTime++;
            }
        }
        if (DownGameTime.isClicked()) {
            if (RoundTime > 0) {
                RoundTime--;
            }
        }
        if (UpScoreToWin.isClicked() && !clicked) {
            clicked = true;
            if (scoreToWin < 20) {
                scoreToWin++;
            }
        }
        if (DownScoreToWin.isClicked() && !clicked) {
            clicked = true;
            if (scoreToWin > 1) {
                scoreToWin--;
            }
        }
        if (UpSuddenDeathLimit.isClicked() && !clicked) {
            clicked = true;
            if (suddenDeathLimit < 20) {
                suddenDeathLimit++;
            }
        }
        if (DownSuddenDeathLimit.isClicked() && !clicked) {
            clicked = true;
            if (suddenDeathLimit > 0) {
                suddenDeathLimit--;
            }
        }
        if (UpDecayRate.isClicked() && !clicked) {
            clicked = true;
            if (DecayRate < 10) {
                DecayRate++;
            }
        }
        if (DownDecayRate.isClicked() && !clicked) {
            clicked = true;
            if (DecayRate > 1) {
                DecayRate--;
            }
        }
        if (!UpScoreToWin.isClicked() && !DownScoreToWin.isClicked() && !UpSuddenDeathLimit.isClicked() && !DownSuddenDeathLimit.isClicked() && !UpDecayRate.isClicked() && !DownDecayRate.isClicked() && clicked) {
            clicked = false;
        }
        exitButton.draw(g);
        RPaddleSelected.draw(g);
        LPaddleSelected.draw(g);
        RBrickSelected.draw(g);
        LBrickSelected.draw(g);
        UpButtonRed.draw(g);
        UpButtonGreen.draw(g);
        UpButtonBlue.draw(g);
        DownButtonRed.draw(g);
        DownButtonGreen.draw(g);
        DownButtonBlue.draw(g);
        resetButton.draw(g);
        BallColorSelected.draw(g);
        suddenDeathOn.draw(g);
        UpGameTime.draw(g);
        UpDecayRate.draw(g);
        UpSuddenDeathLimit.draw(g);
        UpScoreToWin.draw(g);
        DownGameTime.draw(g);
        DownDecayRate.draw(g);
        DownSuddenDeathLimit.draw(g);
        DownScoreToWin.draw(g);
        LeftBotButton.draw(g);
        RightBotButton.draw(g);
    }

    private void initialization() {
        isLeftBot = false;
        isRightBot = false;
        scoreToWin = 3;
        RoundTime = 45;
        suddenDeathLimit = 1;
        DecayRate = 5;
        ball1 = new Ball();
        //suddenDeathOn.setState(true);
        playerRight = new Player(true);
        addKeyListener(playerRight);
        playerLeft = new Player(false);
        addKeyListener(playerLeft);
    }

    private void winScreen(Graphics g) {
        g.setColor(new Color(255, 255, 255, 100));
        g.fillRect(0, (SCREEN_HEIGHT) / 2 - 100, (SCREEN_WIDTH), 200);

        g.setColor(Color.black);
        g.fillRect(SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT - 100, 200, 100);

        g.setFont(new Font("Monospaced", Font.BOLD, 120));
        if (RightWon) {
            g.setColor(Color.black);
            g.drawString("Right Wins!", SCREEN_WIDTH / 2 - g.getFontMetrics().stringWidth("Right Wins!") / 2, (SCREEN_HEIGHT) / 2);
        } else {
            g.setColor(Color.black);
            g.drawString("Left Wins!", SCREEN_WIDTH / 2 - g.getFontMetrics().stringWidth("Right Wins!") / 2, (SCREEN_HEIGHT) / 2);
        }

        if (exitButton.isClicked()) {
            Screen = 0;
        }
        exitButton.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
