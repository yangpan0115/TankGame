package com.yp.tank10;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

//敌方坦克
//需要做成线程类
public class EnemyTank extends Tank implements Runnable{
    Random r = new Random();
    public EnemyTank(int x,int y,Direction direction){
        super(x,y,direction);
    }
    public EnemyTank(){}
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] enemyTankImage = null;
    private static Map<Integer, Image> enemyTankimgs = new HashMap<Integer, Image>();

    static{
        enemyTankImage = new Image[]{
                tk.getImage(EnemyTank.class.getResource("/images/enemytank_U.png")),
                tk.getImage(EnemyTank.class.getResource("/images/enemytank_D.png")),
                tk.getImage(EnemyTank.class.getResource("/images/enemytank_L.png")),
                tk.getImage(EnemyTank.class.getResource("/images/enemytank_R.png")),
        };
        enemyTankimgs.put(0,enemyTankImage[0]);
        enemyTankimgs.put(1,enemyTankImage[1]);
        enemyTankimgs.put(2,enemyTankImage[2]);
        enemyTankimgs.put(3,enemyTankImage[3]);
    }

    //定义一个向量存放敌人的子弹
    Vector<Bullet> bullets = new Vector<Bullet>();

    //画坦克
    public void drawEnemytank(int x, int y, Graphics g, Direction direction){
        switch (direction) {
            case U:
                // 向上
                g.drawImage(enemyTankimgs.get(0),x,y,40,40,null);
                break;
            case D:
                // 向下
                g.drawImage(enemyTankimgs.get(1),x,y,40,40,null);
                break;
            case L:
                // 向左
                g.drawImage(enemyTankimgs.get(2),x,y,40,40,null);
                break;
            case R:
                // 向右
                g.drawImage(enemyTankimgs.get(3),x,y,40,40,null);
                break;
            default:
                break;
        }
    }

    //画生命值
    public void drawLife(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.red);
        g.drawRect(x,y-20,width,10);
        int w = MyTank.width * life/100;
        g.fillRect(x,y-20,w,10);
        g.setColor(c);
    }
    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
            //移动
            move();
            //if(r.nextInt(10)>5){
                if(isLive){
                    if(bullets.size()<1){
                        Bullet bullet = null;
                        //没有子弹了，添加子弹
                        switch(direction){
                            //创建一颗子弹
                            case U:
                                bullet = new Bullet(x+15,y-15, Direction.U);
                                //将子弹加入向量
                                bullets.add(bullet);
                                break;
                            case D:
                                bullet = new Bullet(x+13,y+40, Direction.D);
                                //将子弹加入向量
                                bullets.add(bullet);
                                break;
                            case L:
                                bullet = new Bullet(x-15,y+17, Direction.L);
                                //将子弹加入向量
                                bullets.add(bullet);
                                break;
                            case R:
                                bullet = new Bullet(x+40,y+17, Direction.R);
                                //将子弹加入向量
                                bullets.add(bullet);
                                break;
                            default:
                                break;
                        }
                        //启动单线程
                        Thread t = new Thread(bullet);
                        t.start();
                    }
                }
            //}
            Direction[] directions = Direction.values();
            int dir = r.nextInt(directions.length);
                if(r.nextInt(100)>85)
                direction = directions[dir];

            //判断敌人是否死亡
            if(isLive == false){
                //让坦克死亡，退出线程
                break;
            }
        }
    }
}
