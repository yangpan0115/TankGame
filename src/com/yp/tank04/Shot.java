package com.yp.tank04;

import java.awt.*;

public class Shot implements Runnable{
    int x;
    int y;
    int direct;
    int speed = 10;
    boolean isLive = true;
    public static final int width = 5;
    public static final int height = 5;

    public Shot(int x,int y,int direct){
        this.x = x;
        this.y = y;
        this.direct = direct;
    }
    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(50);
            }catch (Exception e){
                e.printStackTrace();
            }
            switch(direct){
                case 0:
                    y-=speed;
                    break;
                case 1:
                    y+=speed;
                    break;
                case 2:
                    x-=speed;
                    break;
                case 3:
                    x+=speed;
                    break;
                default:
                    break;
            }
            //子弹何时死亡
            if(x<0||x>1366||y<0||y>768){
                this.isLive = false;
                break;
            }
        }
    }
    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }
}
