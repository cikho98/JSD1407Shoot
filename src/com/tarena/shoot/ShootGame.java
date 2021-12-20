package com.tarena.shoot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;

//游戏界面
public class ShootGame extends JPanel {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 654;
    public static BufferedImage background;
    public static BufferedImage start;
    public static BufferedImage gameover;
    public static BufferedImage pause;
    public static BufferedImage bee;
    public static BufferedImage bullet;
    public static BufferedImage airplane;
    public static BufferedImage hero0;
    public static BufferedImage hero1;

    public Hero hero = new Hero();
    public Bullet[] bullets = {};
    public FlyingObject[] flyings = {};
    private int score = 0;

    public ShootGame() {
        flyings = new FlyingObject[2];
        flyings[0] = new Airplane();
        flyings[1] = new Bee();

        bullets = new Bullet[1];
        bullets[0] = new Bullet(200, 350);
    }


    static {
        try {
            background = ImageIO.read(ShootGame.class.getResource("background.png"));
            start = ImageIO.read(ShootGame.class.getResource("start.png"));
            gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
            pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
            bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
            bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
            airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
            hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
            hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);
        paintHero(g);
        paintBullet(g);
        paintFlyingObjects(g);
        paintScore(g);
    }

    public void paintHero(Graphics g) {
        g.drawImage(hero.image, hero.x, hero.y, null);
    }

    public void paintBullet(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.image, b.x, b.y, null);
        }
    }

    public void paintScore(Graphics g){
        int x=10;
        int y=25;
        g.setColor(new Color(0xC01900FF, true));
        g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
        g.drawString("SCORE:"+score,x,y);
        int life=hero.getLife();
        g.drawString("LIFE :"+life,x,y+20);
    }


    private Timer timer;
    private int interval = 10;

    public void action() {
        MouseAdapter l = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                hero.moveTo(x, y);
            }
        };
        this.addMouseListener(l);
        this.addMouseMotionListener(l);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                enterAction();
                stepAction();
                shootAction();
                bangAction();
                outofBoundsAction();
                repaint();
            }
        }, interval, interval);
    }

    int flyEnteredIndex = 0;

    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 40 == 0) {
            FlyingObject obj = nextOne();
            flyings = Arrays.copyOf(flyings, flyings.length + 1);
            flyings[flyings.length - 1] = obj;
        }

    }

//    生成小飞机或者小蜜蜂
    public static FlyingObject nextOne() {
        Random rand = new Random();
        int type = rand.nextInt(20);
        if (type == 0) {
            return new Bee();
        } else {
            return new Airplane();
        }
    }

    public void stepAction() {
        for (int i = 0; i < flyings.length; i++) {
            flyings[i].step();
        }
        for (int i = 0; i < bullets.length; i++) {
            bullets[i].step();

        }
    }

    int shootIndex = 0;

    public void shootAction() {
        shootIndex++;
        if (shootIndex % 30 == 0) {
            Bullet[] bs = hero.shoot();
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
        }
    }

    public void bangAction() {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            bang(b);

        }
    }

    public void outofBoundsAction(){
        int index=0;
        FlyingObject[] flyingLives=new FlyingObject[flyings.length];
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f =flyings[i];
            if (!f.outofBounds()){
                flyingLives[index++]=f;
            }
        }
        flyings=Arrays.copyOf(flyingLives,index);

        index=0;
        Bullet[] bulleLives=new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            Bullet b=bullets[i];
            if (!b.outofBounds()){
                bulleLives[index++]=b;
            }
        }
        bullets=Arrays.copyOf(bulleLives,index);


    }

    public void bang(Bullet b) {
        int index = -1;
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject obj = flyings[i];
            if (obj.shootBy(b)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            FlyingObject one = flyings[index];
            if (one instanceof Enemy) {
                Enemy e = (Enemy) one;
                score += e.getScore();
            } else if (one instanceof Award) {
                Award a = (Award) one;
                int type = a.getType();
                switch (type) {
                    case Award.DOUBLE_FIRE:
                        hero.addDoubleFire();
                        break;
                    case Award.LIFE:
                        hero.addLife();
                        break;
                }
            }
            FlyingObject t =flyings[index];
            flyings[index]=flyings[flyings.length-1];
            flyings[flyings.length-1]=t;
            flyings=Arrays.copyOf(flyings,flyings.length-1);

        }
    }

    public void paintFlyingObjects(Graphics g) {
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            g.drawImage(f.image, f.x, f.y, null);

        }
    }

    public static void main(String[] args) {
        JFrame jframe = new JFrame("Fly");
        ShootGame game = new ShootGame();
        jframe.add(game);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setAlwaysOnTop(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

        game.action();
    }

}
