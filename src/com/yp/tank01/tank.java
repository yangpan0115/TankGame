package com.yp.tank01;

public class tank {
    int x=0;
    int y=0;

    public tank(int x,int y){
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
}

//我的坦克
class MyTank extends tank{
    public MyTank(int x,int y){
        super(x,y);
    }
}
