package com.tarena.shoot;

import java.util.Random;

//敌机：是飞行物，也是敌人
//先继承飞行物，后实现敌人
public class Airplane extends FlyingObject implements Enemy{
    private int speed=2;

    public Airplane(){
        image= ShootGame.airplane;
        width=image.getWidth();
        height= image.getHeight();
        y=-height;
        Random rand=new Random();
        x= rand.nextInt(ShootGame.WIDTH-width);
    }
    public void step(){
        y+=speed;
    }

    public int getScore(){
        return 5;
    }

    public boolean outofBounds(){
        return y>ShootGame.HEIGHT;
    }
}
