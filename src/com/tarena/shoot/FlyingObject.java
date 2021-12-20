package com.tarena.shoot;
import java.awt.image.BufferedImage;

/** 飞行物类*/
public abstract class FlyingObject {
    protected int width; //宽
    protected int height; // 高
    protected int x; // 坐标x
    protected int y; // 坐标y
    protected BufferedImage image; // 图片

    public abstract void step();

    public boolean shootBy(Bullet b){
        int x =b.x;
        int y=b.y;
        return  x>this.x && x<this.x+width && y>this.y && y<this.y+height;
    }

    public abstract boolean outofBounds();
}