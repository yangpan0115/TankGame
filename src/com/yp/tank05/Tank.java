package com.yp.tank05;

import java.awt.*;

public class Tank {
    int x = 0;
    int y = 0;
    int speed = 10;
    int direct = 0;
    boolean isLive = true;
    int  life = 200;

    public static final int width = 40;
    public static final int height = 40;

    public Tank(int x,int y){
        this.x = x;
        this.y = y;
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
