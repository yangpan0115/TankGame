package com.yp.tank04;

import java.util.Random;
import java.util.Vector;

//敌方坦克
//需要做成线程类
public class EnemyTank extends Tank implements Runnable{
    int time = 0;
    Random r = new Random();
    //定义一个向量，可以访问到MyPanel上的所有坦克
    Vector<EnemyTank> ets = new Vector<EnemyTank>();
    //定义一个向量存放敌人的子弹
    Vector<Shot> ss = new Vector<Shot>();

    //敌人添加子弹，在刚刚创建坦克的时候
    public EnemyTank(int x,int y){
        super(x,y);
    }

    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(300);
            }catch (Exception e){
                e.printStackTrace();
            }
            switch(direct){
                case 0:
                    if(y>0){
                        y-=speed;
                    }
                    try{
                        Thread.sleep(100);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    if(y<768){
                        y+=speed;
                    }
                    try{
                        Thread.sleep(100);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    if(x>0){
                        x-=speed;
                    }
                    try{
                        Thread.sleep(100);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    if(x<1366){
                        x+=speed;
                    }
                    try{
                        Thread.sleep(100);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }

            if(r.nextInt(10)>5){
                if(isLive){
                    if(ss.size()<100){
                        Shot shot = null;
                        //没有子弹了，添加子弹
                        switch(direct){
                            //创建一颗子弹
                            case 0:
                            shot = new Shot(x+15,y-15,0);
                            //将子弹加入向量
                                ss.add(shot);
                                break;
                            case 1:
                                shot = new Shot(x+15,y+15,1);
                                //将子弹加入向量
                                ss.add(shot);
                                break;
                            case 2:
                                shot = new Shot(x-15,y+15,2);
                                //将子弹加入向量
                                ss.add(shot);
                                break;
                            case 3:
                                shot = new Shot(x+15,y+15,3);
                                //将子弹加入向量
                                ss.add(shot);
                                break;
                            default:
                                break;
                        }
                        //启动单线程
                        Thread t = new Thread(shot);
                        t.start();
                    }
                }
            }
            //让坦克产生新的方向
            if(r.nextInt(10)>5){
                direct = (int) (Math.random()*4);
            }

            //判断敌人是否死亡
            if(isLive == false){
                //让坦克死亡，退出线程
                break;
            }
        }
    }

}
