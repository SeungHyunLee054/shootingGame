package JavaShootingGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Game extends Thread {
    private int delay = 20;
    private long pretime;
    private int cnt;

    private int score;

    private Image playerR = new ImageIcon("src/main/java/JavaShootingGame/Images/airplane_red.png").getImage();
    private Image playerG = new ImageIcon("src/main/java/JavaShootingGame/Images/airplane_green.png").getImage();
    private Image playerB = new ImageIcon("src/main/java/JavaShootingGame/Images/airplane_blue.png").getImage();

    private int playerX, playerY;
    private int playerWidth = playerR.getWidth(null);
    private int playerHeight = playerR.getHeight(null);
    private int playerSpeed = 10;
    private int playerHp = 30;

    private boolean up, down, left, right, shooting;

    private boolean isOver;

    private ArrayList<PlayerAttack> playerAttackArrayList = new ArrayList<PlayerAttack>();
    private ArrayList<Enemy> enemyArrayList = new ArrayList<Enemy>();
    private ArrayList<EnemyAttack> enemyAttackArrayList = new ArrayList<EnemyAttack>();
    private PlayerAttack playerAttack;
    private Enemy enemy;
    private EnemyAttack enemyAttack;

    private boolean isPlayerR, isPlayerG, isPlayerB;


    public void run(){

        reset();

        while (true){
            while (!isOver) {
                pretime = System.currentTimeMillis();
                if (System.currentTimeMillis() - pretime < delay) {
                    try {
                        Thread.sleep(delay - System.currentTimeMillis() + pretime);
                        keyProcess();
                        playerAttackProcess();
                        enemyAppearProcess();
                        enemyMoveProcess();
                        enemyAttackProcess();
                        cnt++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void reset(){
        isOver = false;

        cnt = 0;
        score = 0;
        playerHp = 30;
        playerX = 10;
        playerY = (Main.SCREEN_HEIGHT-playerHeight) / 2;

        playerAttackArrayList.clear();
        enemyArrayList.clear();
        enemyAttackArrayList.clear();
    }

    private void keyProcess(){
        if (up && playerY - playerSpeed > 0) playerY -= playerSpeed;
        if (down && playerY + playerHeight/3 + playerSpeed < Main.SCREEN_HEIGHT) playerY += playerSpeed;
        if (left && playerX - playerSpeed > 0) playerX -= playerSpeed;
        if (right && playerX + playerWidth/3 + playerSpeed < Main.SCREEN_WIDTH) playerX += playerSpeed;
        if (shooting && cnt % 15 ==0){
            playerAttack = new PlayerAttack(playerX + 222, playerY + 25);
            playerAttackArrayList.add(playerAttack);
        }
    }

    private  void playerAttackProcess(){
        for (int i = 0; i < playerAttackArrayList.size(); i++){
            playerAttack = playerAttackArrayList.get(i);
            playerAttack.fire();

            for (int j = 0; j < enemyArrayList.size(); j++){
                enemy = enemyArrayList.get(j);
                if(playerAttack.x > enemy.x && playerAttack.x < enemy.x + enemy.width/3
                        && playerAttack.y > enemy.y && playerAttack.y < enemy.y + enemy.height/3){
                    enemy.hp -= playerAttack.attack;
                    playerAttackArrayList.remove(playerAttack);
                }
                if (enemy.hp <= 0){
                    enemyArrayList.remove(enemy);
                    score += 100;
                }
            }
        }
    }

    private void enemyAppearProcess(){
        if(cnt % 80 == 0){
            enemy = new Enemy(1120, (int)(Math.random()*621));
            enemyArrayList.add(enemy);
        }
    }

    private void enemyMoveProcess(){
        for(int i = 0; i < enemyArrayList.size(); i++){
            enemy = enemyArrayList.get(i);
            enemy.move();
        }
    }

    private void enemyAttackProcess(){
        if(cnt % 50 == 0){
            enemyAttack = new EnemyAttack(enemy.x - 79, enemy.y + 35);
            enemyAttackArrayList.add(enemyAttack);
        }

        for(int i = 0; i < enemyAttackArrayList.size(); i++){
            enemyAttack = enemyAttackArrayList.get(i);
            enemyAttack.fire();

            if(enemyAttack.x > playerX && enemyAttack.x < playerX + playerWidth/3
                    && enemyAttack.y > playerY && enemyAttack.y <playerY + playerHeight/3){
                playerHp -= enemyAttack.attack;
                enemyAttackArrayList.remove(enemyAttack);
                if (playerHp <= 0) isOver = true;
            }
        }
    }

    public void gameDraw(Graphics g){
        playerDraw(g);
        enemyDraw(g);
        infoDraw(g);
    }

    public void infoDraw(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,40));
        g.drawString("SCORE : " +score, 40, 80);
        if (isOver){
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial",Font.BOLD,80));
            g.drawString("Press R to restart", 295, 380);
        }
    }

    public void playerDraw(Graphics g){
        if(isPlayerR) {
            g.drawImage(playerR, playerX, playerY, playerWidth / 3, playerHeight / 3, null);
        }
        if (isPlayerG){
            g.drawImage(playerG, playerX, playerY, playerWidth / 3, playerHeight / 3, null);
        }
        if (isPlayerB){
            g.drawImage(playerB, playerX, playerY, playerWidth / 3, playerHeight / 3, null);
        }
        g.setColor(Color.RED);
        g.fillRect(playerX-1, playerY-40, playerHp *6, 20);
        for (int i = 0; i < playerAttackArrayList.size(); i++){
            playerAttack = playerAttackArrayList.get(i);
            g.drawImage(playerAttack.image, playerAttack.x, playerAttack.y, null);
        }
    }

    public void enemyDraw(Graphics g){
        for(int i = 0; i < enemyArrayList.size(); i++){
            enemy = enemyArrayList.get(i);
            g.drawImage(enemy.image, enemy.x, enemy.y, enemy.image.getWidth(null)/3, enemy.image.getHeight(null)/3, null);
            g.setColor(Color.RED);
            g.fillRect(enemy.x+1,enemy.y-40, enemy.hp*15, 20);
        }

        for(int i = 0; i < enemyAttackArrayList.size(); i++){
            enemyAttack = enemyAttackArrayList.get(i);
            g.drawImage(enemyAttack.image, enemyAttack.x, enemyAttack.y, null);
        }
    }

    public boolean isOver() {
        return isOver;
    }

    public void setPlayerR(boolean playerR) {
        this.isPlayerR=playerR;
    }

    public void setPlayerG(boolean playerG) {
        this.isPlayerG=playerG;
    }

    public void setPlayerB(boolean playerB) {
        this.isPlayerB=playerB;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }
}
