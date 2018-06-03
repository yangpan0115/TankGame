package com.yp.tank08;

import java.awt.*;

public class Tank {
    int x;
    int y;
    int speed = 10;
    Direction direction = Direction.U;//初始化方向向上
    boolean isLive = true;
    int  life = 100;
    private int oldX;
    private int oldY;
    int time = 0;

    public static final int width = 40;
    public static final int height = 40;

    public Tank(int x,int y) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
    }

    public Tank(int x,int y,Direction direction) {// Tank的构造函数2
        this(x,y);
        this.direction = direction;
    }

    public Tank(){}

    //生命值减少
    public void lifeDown(){
        if(life > 0){
            life -= 20;
            isLive = true;
        }
        if(life == 0)
            isLive = false;

    }

    public void move(){
        time++;

        this.oldX = x;
        this.oldY = y;

        switch (direction){
            case U:
                y -= speed;
                break;
            case D:
                y += speed;
                break;
            case L:
                x -= speed;
                break;
            case R:
                x += speed;
                break;
        }
        if(x<0)
            x = 0;
        if(y<20)
            y = 20;
        if(x + Tank.width > MyPanel.PanelWidth)
            x = MyPanel.PanelWidth - Tank.width;
        if(y + Tank.height > MyPanel.PanelHeight)
            y = MyPanel.PanelHeight - Tank.height;
    }

    public void stop(){
        x = oldX;
        y = oldY;
    }

    //碰撞检测
    //1 普通墙
    public boolean collideCommonWall(CommonWall cw){
        if(this.isLive&&this.getRect().intersects(cw.getRect())){
            this.stop();
            return true;
        }
        return false;
    }
    //2 金属墙
    public boolean collideMetalWall(MetalWall mw){
        if(this.isLive&&this.getRect().intersects(mw.getRect())){
            this.stop();
            return true;
        }
        return false;
    }
    //3 家
    public boolean collideHome(Home home){
        if(this.isLive&&this.getRect().intersects(home.getRect())){
            return true;
        }
        return false;
    }
    //4 其他坦克
    public boolean collideTank(Tank tank){
        if(this != tank){
            if(this.isLive&&this.getRect().intersects(tank.getRect())){
                this.stop();
                tank.stop();
                return true;
            }
        }
        return false;
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

    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }
}
