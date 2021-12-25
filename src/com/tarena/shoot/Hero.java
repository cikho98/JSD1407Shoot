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

    public boolean hit(FlyingObject other){
        int x1=other.x-this.width/2;
        int x2=other.x+ other.width+this.width;
        int y1=other.y+this.height/2;
        int y2=other.y+other.height+this.height/2;
        int heroX=this.x+this.width/2;
        int heroY=this.y+this.height/2;

        return heroX>x1 && heroX<x2
                &&
                heroY>y1 && heroY<y2;
    }

    public  void subtractLife(){
        life--;
    }
    public void setDoublefire(int doublefire){
        this.doublefire=doublefire;
    }
}
