package com.yp.tank06;

import java.awt.*;

public class Tank {
    int x = 0;
    int y = 0;
    int speed = 10;
    int direct = 0;
    int  life = 100;
    boolean isLive = true;

    public static final int width = 30;
    public static final int height = 30;

    public Tank(int x,int y){
        this.x = x;
        this.y = y;
    }

    //生命值减少
    public void lifeDown(){
        if(life >= 20){
            life -= 20;
        }else{
            isLive = false;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }
}
