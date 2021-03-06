package com.yp.tank05;

import java.awt.*;

public class Blood {
    int x;
    int y;
    boolean isLive = true;
    public static final int width = 30;
    public static final int height = 30;

    public Blood(int x,int y){
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

    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }

}
