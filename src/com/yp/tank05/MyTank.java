package com.yp.tank05;

import java.util.ArrayList;


public class MyTank extends Tank{
    //子弹集合
    ArrayList<Shot> ss = new ArrayList<Shot>();
    Shot shot = null;

    public MyTank(int x,int y){
        super(x,y);
    }
    //开火
    public void shotEnemy(){
        switch(direct){
            case 0:
                shot = new Shot(x+20,y-15,0);
                ss.add(shot);
                break;
            case 1:
                shot = new Shot(x+20,y+50,1);
                ss.add(shot);
                break;
            case 2:
                shot = new Shot(x-15,y+23,2);
                ss.add(shot);
                break;
            case 3:
                shot = new Shot(x+50,y+23,3);
                ss.add(shot);
                break;
            default:
                break;
        }
        Thread t = new Thread(shot);
        t.start();
    }
    public void moveUp(){
        if(y>0){
            y-=speed;
        }
    }
    public void moveDown(){
        if(y<650){
            y+=speed;
        }
    }
    public void moveLeft(){
        if(x>0)
        x-=speed;
    }
    public void moveRight(){
        if(x<1250)
        x+=speed;
    }

}
