package JavaShootingGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;

public class ShootingGame extends JFrame {
    private Image bufferImage;
    private Graphics screenGraphic;

    private Image mainScreen = new ImageIcon("src/main/java/JavaShootingGame/Images/title.png").getImage();
    private Image backGroundScreen = new ImageIcon("src/main/java/JavaShootingGame/Images/title.png").getImage();
    Image backImg = backGroundScreen;
    ImageIcon startbtnimg  = new ImageIcon("src/main/java/JavaShootingGame/Images/start_btn.png");
    ImageIcon howbtnimg  = new ImageIcon("src/main/java/JavaShootingGame/Images/how_btn.png");
    ImageIcon titlebtnimg  = new ImageIcon("src/main/java/JavaShootingGame/Images/title_btn.png");
    private Image loadingScreen = new ImageIcon("src/main/java/JavaShootingGame/Images/how.png").getImage();
    private Image howToPlayScreen = new ImageIcon("src/main/java/JavaShootingGame/Images/how.png").getImage();
    private Image gameScreen = new ImageIcon("src/main/java/JavaShootingGame/Images/sky_storm.png").getImage();

    ImageIcon rapbtnimg  = new ImageIcon("src/main/java/JavaShootingGame/Images/airplane_red.png");
    ImageIcon gapbtnimg  = new ImageIcon("src/main/java/JavaShootingGame/Images/airplane_green.png");
    ImageIcon bapbtnimg  = new ImageIcon("src/main/java/JavaShootingGame/Images/airplane_blue.png");

    JButton gsbtn = new JButton(startbtnimg); // 게임시작 버튼
    JButton hpbtn = new JButton(howbtnimg); // 게임설명 버튼
    JButton titlebtn = new JButton(titlebtnimg); //메인화면 버튼

    JButton rapbtn = new JButton(rapbtnimg); // red 비행기
    JButton gapbtn = new JButton(gapbtnimg); //green 비행기
    JButton bapbtn = new JButton(bapbtnimg); // blue 비행기


    private boolean isMainScreen, isLoadingScreen, isGameScreen;


    private Game game = new Game();

    public ShootingGame(){
        setTitle("자바 횡스크롤 슈팅게임");
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);

        gsbtn.setBounds(590,260,100,30); // 중앙 상단
        hpbtn.setBounds(590,330,100,30); // 중앙 하단
        bapbtn.setBorderPainted(false);
        bapbtn.setContentAreaFilled(false);
        bapbtn.setFocusPainted(false);
        gapbtn.setBorderPainted(false);
        gapbtn.setContentAreaFilled(false);
        gapbtn.setFocusPainted(false);
        rapbtn.setBorderPainted(false);
        rapbtn.setContentAreaFilled(false);
        rapbtn.setFocusPainted(false);

        rapbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                game.setPlayerR(true);
                game.setPlayerG(false);
                game.setPlayerB(false);
                gameStart();
            }
        });
        gapbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                game.setPlayerR(true);
                game.setPlayerG(false);
                game.setPlayerB(false);
                gameStart();
            }
        });
        bapbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                game.setPlayerR(true);
                game.setPlayerG(false);
                game.setPlayerB(true);
                gameStart();
            }
        });

        add(gsbtn);   //배경에 버튼 추가
        add(hpbtn);
        add(titlebtn);
        add(rapbtn);
        add(gapbtn);
        add(bapbtn);

        addKeyListener(new KeyListener());
        init();
    }

    private void init(){
        isMainScreen = true;
        isLoadingScreen = false;
        isGameScreen = false;


        addKeyListener(new KeyListener());
    }

    private void gameStart(){
        /*isMainScreen = false;
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
        loadingTimer.schedule(loadingTask, 3000);*/
        isMainScreen=false;
        isGameScreen=true;
        game.start();
    }

    public void paint(Graphics g){
        bufferImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        screenGraphic = bufferImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(bufferImage, 0, 0, null);

    }

    public void screenDraw(Graphics g){
        if(isMainScreen) {
            g.drawImage(backImg,0,0,Main.SCREEN_WIDTH,Main.SCREEN_HEIGHT,this);
            paintComponents(g);

            gsbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    backImg = backGroundScreen;
                    gsbtn.setVisible(false);
                    hpbtn.setVisible(true);
                    titlebtn.setVisible(true);
                    rapbtn.setVisible(true);
                    gapbtn.setVisible(true);
                    bapbtn.setVisible(true);
                    rapbtn.setBounds(380, 10, 580, 210);
                    gapbtn.setBounds(380, 230, 580, 210);
                    bapbtn.setBounds(380, 450, 580, 210);
                    titlebtn.setBounds(30,10,100,30);
                    hpbtn.setBounds(160,10,100,30);
                    repaint();
                }
            });

            hpbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    backImg = howToPlayScreen;
                    g.drawImage(backImg,0,0,Main.SCREEN_WIDTH,Main.SCREEN_HEIGHT,null);
                    gsbtn.setVisible(true);
                    hpbtn.setVisible(true);
                    titlebtn.setVisible(false);
                    rapbtn.setVisible(false);
                    gapbtn.setVisible(false);
                    bapbtn.setVisible(false);
                    gsbtn.setBounds(590,260,100,30); // 버튼의 위치 중앙
                    hpbtn.setBounds(590,330,100,30);
                    repaint(); // 다시 그리기
                }
            });

            titlebtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    backImg = gameScreen;
                    gsbtn.setVisible(true);
                    hpbtn.setVisible(true);
                    titlebtn.setVisible(false);
                    rapbtn.setVisible(false);
                    gapbtn.setVisible(false);
                    bapbtn.setVisible(false);
                    gsbtn.setBounds(590,260,100,30); // 버튼의 위치 중앙
                    hpbtn.setBounds(590,330,100,30);
                    repaint(); // 다시 그리기
                }
            });



            /*g.drawImage(mainScreen, 0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.BOLD,80));
            g.drawString("Press ENTER to START", 200, 500);*/
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
}
