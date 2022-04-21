package AirplaneMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class AirplaneMove {
    public static void main(String[] ar){
        game_Frame fms = new game_Frame();
    }
}

class game_Frame extends JFrame implements KeyListener, Runnable{
    int f_width = 800;
    int f_height = 600;

    int x, y;
    boolean KeyUp = false;
    boolean KeyDown = false;
    boolean KeyLeft = false;
    boolean KeyRight = false;
    Thread th;
    Toolkit tk = Toolkit.getDefaultToolkit();
    Image me_img = tk.getImage("src/main/resources/abc.png");
    Image buffImage; //더블 버퍼링용
    Graphics buffg; //더블 버퍼링용

    game_Frame(){
        init();
        start();

        setTitle("슈팅 게임 만들기");
        setSize(f_width, f_height);
        Dimension screen = tk.getScreenSize();

        int f_xpos = (int)(screen.getWidth() / 2 - f_width / 2);
        int f_ypos = (int)(screen.getHeight() / 2 - f_height / 2);

        setLocation(f_xpos, f_ypos);
        setResizable(false);
        setVisible(true);
    }
    public void init(){
        x = 100;
        y = 100;
    }
    public void start(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addKeyListener(this);
        th = new Thread(this);
        th.start();

    }

    public void run(){
        try{
            while(true){
                KeyProcess();
                repaint();
                Thread.sleep(20);
            }
        }catch (Exception e){}
    }

    public void paint(Graphics g){
        buffImage = createImage(f_width, f_height);
//더블버퍼링 버퍼 크기를 화면 크기와 같게 설정
        buffg = buffImage.getGraphics(); //버퍼의 그래픽 객체를 얻기

        update(g);
    }

    public void update(Graphics g){
        Draw_Char();// 실제로 그려진 그림을 가져온다

        g.drawImage(buffImage, 0, 0, this);
// 화면에 버퍼에 그린 그림을 가져와 그리기
    }

    public void Draw_Char(){ // 실제로 그림들을 그릴 부분
        buffg.clearRect(0, 0, f_width, f_height);
        buffg.drawImage(me_img, x, y, this);
    }


    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP :
                KeyUp = true;
                break;
            case KeyEvent.VK_DOWN :
                KeyDown = true;
                break;
            case KeyEvent.VK_LEFT :
                KeyLeft = true;
                break;
            case KeyEvent.VK_RIGHT :
                KeyRight = true;
                break;
        }
    }
    public void keyReleased(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP :
                KeyUp = false;
                break;
            case KeyEvent.VK_DOWN :
                KeyDown = false;
                break;
            case KeyEvent.VK_LEFT :
                KeyLeft = false;
                break;
            case KeyEvent.VK_RIGHT :
                KeyRight = false;
                break;
        }
    }
    public void keyTyped(KeyEvent e){}
    public void KeyProcess(){

        if(KeyUp == true) y -= 5;
        if(KeyDown == true) y += 5;
        if(KeyLeft == true) x -= 5;
        if(KeyRight == true) x += 5;
    }
}
