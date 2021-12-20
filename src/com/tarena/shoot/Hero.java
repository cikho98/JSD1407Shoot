package com.tarena.shoot;
import java.awt.image.BufferedImage;
public class Hero extends FlyingObject{
    private BufferedImage[] images={};
    private int index;
    private int doublefire;
    private int life;


    public Hero(){
        image=ShootGame.hero0;
        width=image.getWidth();
        height= image.getHeight();
        x=150;
        y=400;
        doublefire=0;
        life=3;
        images=new BufferedImage[]{
                ShootGame.hero0,
                ShootGame.hero1
        };

    }

    public void step(){
        int num = index++/10%images.length;
        image=images[num];

    }

    public Bullet[] shoot(){
        int xStep=this.width/4;
        int yStep=20;
        if (doublefire>0){
            Bullet[] bullets = new Bullet[2];
            bullets[0]=new Bullet(this.x+1*xStep,this.y-yStep);
            bullets[1]=new Bullet(this.x+3*xStep,this.y-yStep);
            return bullets;
        }else{
            Bullet[] bullets = new Bullet[1];
            bullets[0]=new Bullet(this.x+2*xStep,this.y-yStep);
            return bullets;
        }
    }

    public void moveTo(int x, int y){
        this.x=x-this.width/2;
        this.y=y-this.height/2;
    }

    public void addDoubleFire(){
        doublefire +=40;
    }

    public void addLife(){
        life++;
    }

    public int getLife(){
        return life;
    }

    public boolean outofBounds(){
        return false;
    }
}
