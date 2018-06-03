package com.yp.tank10;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MyTank extends Tank {
    public MyTank(int x,int y,Direction direction){
        super(x,y,direction);
    }
    public MyTank(){}
    private static Toolkit tk = Toolkit.getDefaultToolkit();//读取图片
    private static Image[] myTankImage = null;
    private static Map<Integer, Image> myTankimgs = new HashMap<Integer, Image>();

    static{
        myTankImage = new Image[]{
                tk.getImage(MyTank.class.getResource("/images/mytank_U.png")),
                tk.getImage(MyTank.class.getResource("/images/mytank_D.png")),
                tk.getImage(MyTank.class.getResource("/images/mytank_L.png")),
                tk.getImage(MyTank.class.getResource("/images/mytank_R.png")),
        };
        myTankimgs.put(0,myTankImage[0]);
        myTankimgs.put(1,myTankImage[1]);
        myTankimgs.put(2,myTankImage[2]);
        myTankimgs.put(3,myTankImage[3]);
    }

    //画坦克
    public void drawMytank(int x, int y, Graphics g, Direction direction){
        switch (direction) {
            case U:
                // 向上
                g.drawImage(myTankimgs.get(0),x,y,40,40,null);
                break;
            case D:
                // 向下w
                g.drawImage(myTankimgs.get(1),x,y,40,40,null);
                break;
            case L:
                // 向左
                g.drawImage(myTankimgs.get(2),x,y,40,40,null);
                break;
            case R:
                // 向右
                g.drawImage(myTankimgs.get(3),x,y,40,40,null);
                break;
            default:
                break;
        }
    }
    //画生命值
    public void drawLife(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.green);
        g.drawRect(x,y-20,width,10);
        int w = MyTank.width * life/100;
        g.fillRect(x,y-20,w,10);
        g.setColor(c);
    }

    //子弹集合
    Vector<Bullet> bullets = new Vector<Bullet>();
    Bullet bullet = null;

    //开火
    public void shotEnemy(){
        switch(direction){
            case U:
                bullet = new Bullet(x+15,y-15, Direction.U);
                bullets.add(bullet);
                break;
            case D:
                bullet = new Bullet(x+13,y+40, Direction.D);
                bullets.add(bullet);
                break;
            case L:
                bullet = new Bullet(x-15,y+17, Direction.L);
                bullets.add(bullet);
                break;
            case R:
                bullet = new Bullet(x+40,y+17, Direction.R);
                bullets.add(bullet);
                break;
            default:
                break;
        }
        Thread t = new Thread(bullet);
        t.start();
    }

    //导弹集合
    Vector<Missile> missiles = new Vector<Missile>();
    Missile missile = null;
    //发射导弹
    public void missileEnemy(){
        switch(direction){
            case U:
                missile = new Missile(x+6,y-20, Direction.U);
                missiles.add(missile);
                break;
            case D:
                missile = new Missile(x+8,y+40, Direction.D);
                missiles.add(missile);
                break;
            case L:
                missile = new Missile(x-15,y+10, Direction.L);
                missiles.add(missile);
                break;
            case R:
                missile = new Missile(x+40,y+8, Direction.R);
                missiles.add(missile);
                break;
            default:
                break;
        }
        Thread t = new Thread(missile);
        t.start();
    }


    //吃血包
    public boolean getBlood(Blood blood){
        if(this.getRect().intersects(blood.getRect())){
            blood.isLive = false;
            if(this.life == 100){
                this.life += 0;
            }else{
                this.life = 100;
            }
            return true;
        }
        return false;
    }
}
