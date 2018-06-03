package com.yp.tank04;

public class Bomb {
    int x;
    int y;
    //炸弹的生命
    int life = 6;
    boolean isLive = true;
    public Bomb(int x,int y){
        this.x = x;
        this.y = y;

    }
    //减少生命值
    public void lifeDown(){
        if(life>0){
            life--;
        }else{
            isLive = false;
        }
    }
}
