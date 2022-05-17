package JavaShootingGame;

import javax.swing.*;
import java.awt.*;

public class Enemy {
    Image image = new ImageIcon("src/main/java/JavaShootingGame/Images/e1.png").getImage();
    int x, y;
    int width = image.getWidth(null);
    int height = image.getHeight(null);
    int hp = 10;

    public Enemy(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void move(){
        this.x -=7;
    }

}
