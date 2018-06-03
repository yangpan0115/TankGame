package com.yp.tank06;

import java.util.Vector;

public class MyTank extends Tank {
    //子弹集合
    Vector<Shot> ss = new Vector<Shot>();
    Shot shot = null;
    int  life = 100;

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
        if(y>0){
            y-=speed;
        }
    }
    public void moveDown(){
        if(y<600){
            y+=speed;
        }
    }
    public void moveLeft(){
        if(x>0){
            x-=speed;
        }
    }
    public void moveRight(){
        if(x<1000){
            x+=speed;
        }
    }
    public void stop(){
        x += 0;
        y += 0;
    }

    //生命值减少
    public void lifeDown(){
        if(life >= 20){
           life -= 20;
        }else{
            isLive = false;
        }
    }
}
