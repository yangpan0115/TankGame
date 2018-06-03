package com.yp.tank04;

import java.util.Vector;

public class MyTank extends Tank {
    //子弹集合
    Vector<Shot> ss = new Vector<Shot>();
    Shot shot = null;

    public MyTank(int x,int y){
        super(x,y);
    }
    //开火
    public void shotEnemy(){
        switch(direct){
            case 0:
                shot = new Shot(x+15,y-15,0);
                ss.add(shot);
                break;
            case 1:
                shot = new Shot(x+15,y+15,1);
                ss.add(shot);
                break;
            case 2:
                shot = new Shot(x-15,y+15,2);
                ss.add(shot);
                break;
            case 3:
                shot = new Shot(x+15,y+15,3);
                ss.add(shot);
                break;
            default:
                break;
        }
        Thread t = new Thread(shot);
        t.start();
    }
    public void moveUp(){
        y-=speed;
    }
    public void moveDown(){
        y+=speed;
    }
    public void moveLeft(){
        x-=speed;
    }
    public void moveRight(){
        x+=speed;
    }


}
