package JavaShootingGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TimerTask;
import java.util.Timer;

public class ShootingGame extends JFrame {
    private Image bufferImage;
    private Graphics screenGraphic;

    private Image mainScreen = new ImageIcon("src/main/java/JavaShootingGame/Images/title.png").getImage();
    private Image loadingScreen = new ImageIcon("src/main/java/JavaShootingGame/Images/how.png").getImage();
    private Image selectScreen = new ImageIcon("src/main/java/JavaShootingGame/Images/sky_storm.png").getImage();
    private Image gameScreen = new ImageIcon("src/main/java/JavaShootingGame/Images/sky_storm.png").getImage();

    private boolean isMainScreen, isLoadingScreen, isGameScreen;

    private Game game = new Game();

    private JButton startBtn = new JButton("START", new ImageIcon("src/main/java/JavaShootingGame/Images/abc.png"));
    public ShootingGame(){
        setTitle("자바 횡스크롤 슈팅게임");
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);

        addKeyListener(new KeyListener());
        init();

        startBtn.setBounds(600,300, 200, 200);
        startBtn.setBorderPainted(false);
        startBtn.setContentAreaFilled(false);
        startBtn.setFocusPainted(false);
        add(startBtn);
    }

    private void init(){
        isMainScreen = true;
        isLoadingScreen = false;
        isGameScreen = false;

        addKeyListener(new KeyListener());
    }

    private void gameStart(){
        isMainScreen = false;
        isLoadingScreen = true;

        Timer loadingTimer = new Timer();
        TimerTask loadingTask = new TimerTask() {
            @Override
            public void run() {
                isLoadingScreen = true;
                isGameScreen = true;
                game.start();
            }
        };
        loadingTimer.schedule(loadingTask, 3000);
    }

    public void paint(Graphics g){
        bufferImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        screenGraphic = bufferImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(bufferImage, 0, 0, null);

    }

    public void screenDraw(Graphics g){
        if(isMainScreen) {
            g.drawImage(mainScreen, 0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.BOLD,80));
            g.drawString("Press ENTER to START", 200, 500);
        }
        if(isLoadingScreen){
            g.drawImage(loadingScreen, 0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, null);
        }
        if(isGameScreen){
            g.drawImage(gameScreen, 0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, null);
            game.gameDraw(g);
        }
        this.repaint();

    }

    class KeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP:
                    game.setUp(true);
                    break;
                case KeyEvent.VK_DOWN:
                    game.setDown(true);
                    break;
                case KeyEvent.VK_RIGHT:
                    game.setRight(true);
                    break;
                case KeyEvent.VK_LEFT:
                    game.setLeft(true);
                    break;
                case KeyEvent.VK_R:
                    if(game.isOver()) game.reset();
                    break;
                case KeyEvent.VK_SPACE:
                    game.setShooting(true);
                    break;
                case KeyEvent.VK_ENTER:
                    if(isMainScreen){
                        gameStart();
                    }
                    break;

                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
            }
        }

        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP:
                    game.setUp(false);
                    break;
                case KeyEvent.VK_DOWN:
                    game.setDown(false);
                    break;
                case KeyEvent.VK_RIGHT:
                    game.setRight(false);
                    break;
                case KeyEvent.VK_LEFT:
                    game.setLeft(false);
                    break;
                case KeyEvent.VK_SPACE:
                    game.setShooting(false);
                    break;
            }
        }
    }

    class MouseListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getButton() == MouseEvent.BUTTON1);
        }
    }
}
