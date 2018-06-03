package com.yp.tank05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;


public class MyPanel extends JPanel implements Runnable,KeyListener {
    //初始化我方坦克
    MyTank myTank = null;
    //定义敌军
    ArrayList<EnemyTank> ets = new ArrayList<EnemyTank>();
    //定义敌军数量
    int ensize = 5;
    //定义炸弹集合
    ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    //定义普通墙
    int cwsize = 5;
    ArrayList<CommonWall> cws = new ArrayList<CommonWall>();
    //定义金属墙
    int mwsize = 5;
    ArrayList<MetalWall> mws = new ArrayList<MetalWall>();
    //定义家
    Home home = null;

    //定义血包
    Blood blood = null;

    Random r = new Random();
    //定义4张图片，表示坦克的上下左右
    //1 我方坦克
    private Image mytank_U = null;
    private Image mytank_D = null;
    private Image mytank_L = null;
    private Image mytank_R = null;
    //2 敌方坦克
    private Image enemytank_U = null;
    private Image enemytank_D = null;
    private Image enemytank_L = null;
    private Image enemytank_R = null;

    //定义4张图片，表示子弹的上下左右
    private Image bullet_U = null;
    private Image bullet_D = null;
    private Image bullet_L = null;
    private Image bullet_R = null;

    //定义5张图片，表示一个炸弹的爆炸过程
    private Image bomb1 = null;
    private Image bomb2 = null;
    private Image bomb3 = null;
    private Image bomb4 = null;
    private Image bomb5 = null;

    //定义墙的图片
    private Image commonWall = null;
    private Image metalWall = null;

    //定义家的图片
    private Image homeImage = null;

    //定义血包图片
    private Image bloodImage = null;

    public MyPanel(){
        //设定我方坦克出现的位置
        myTank = new MyTank(550,400);
        //初始化敌方坦克
        for(int i=0;i<ensize;i++){
            //创建敌人的坦克对象
            EnemyTank et = new EnemyTank((i+1)*150,50);
            et.setDirect(1);
            //启动敌方坦克
            Thread t1 = new Thread(et);
            t1.start();
            //给敌人添加子弹
            Shot shot = new Shot(et.x,et.y,1);
            //加入给敌方坦克
            et.ss.add(shot);
            //发射子弹
            Thread t2 = new Thread(shot);
            t2.start();
            //加入坦克群
            ets.add(et);
        }

        //初始化墙
        for(int i=0;i<cwsize;i++){
            CommonWall cw1 = new CommonWall(i*20,200);
            cws.add(cw1);

            CommonWall cw2 = new CommonWall(i*20,220);
            cws.add(cw2);

            CommonWall cw3 = new CommonWall(i*20+300,200);
            cws.add(cw3);

            CommonWall cw4 = new CommonWall(i*20+300,220);
            cws.add(cw4);

            CommonWall cw5 = new CommonWall(i*20+600,200);
            cws.add(cw5);

            CommonWall cw6 = new CommonWall(i*20+600,220);
            cws.add(cw6);

            CommonWall home1 = new CommonWall(450,600-i*20);
            cws.add(home1);


            CommonWall home3 = new CommonWall(545,520+i*20);
            cws.add(home3);



        }
        for(int i=0;i<mwsize;i++){
            MetalWall mw1 = new MetalWall((i+1)*20+200,300);
            mws.add(mw1);

            MetalWall mw2 = new MetalWall((i+1)*20+500,300);
            mws.add(mw2);

            MetalWall mw3 = new MetalWall((i+1)*20+800,300);
            mws.add(mw3);

            MetalWall home2 = new MetalWall(i*20+450,480);
            mws.add(home2);
        }
        //初始化家
        home = new Home(480,560);

        //初始化血包
        blood = new Blood(700,560);

        // 初始化图片
        //1 我方坦克
        mytank_U = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/mytank_U.png"));
        mytank_D = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/mytank_D.png"));
        mytank_L = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/mytank_L.png"));
        mytank_R = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/mytank_R.png"));
        //2 敌方坦克
        enemytank_U = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/enemytank_U.png"));
        enemytank_D = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/enemytank_D.png"));
        enemytank_L = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/enemytank_L.png"));
        enemytank_R = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/enemytank_R.png"));

        //子弹
        bullet_U = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/bullet_U.gif"));
        bullet_D = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/bullet_D.gif"));
        bullet_L = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/bullet_L.gif"));
        bullet_R = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/bullet_R.gif"));


        // 炸弹
        bomb1 = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/bomb_1.png"));
        bomb2 = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/bomb_2.png"));
        bomb3 = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/bomb_3.png"));
        bomb4 = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/bomb_4.png"));
        bomb5 = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/bomb_5.png"));

        //墙
        commonWall = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/commonWall.gif"));
        metalWall = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/metalWall.gif"));

        //家
        homeImage = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/home.png"));
        //血包
        bloodImage = Toolkit.getDefaultToolkit().getImage(
                MyPanel.class.getResource("/images/blood.png"));
    }


    //作图
    protected void paintComponent(Graphics g){

        super.paintComponent(g);
        // 背景填充
        g.fillRect(0, 0, 1300, 700);
        //画出我方坦克
        if(myTank.isLive){
            drawTank(myTank.getX(),myTank.getY(),g,myTank.direct,0);
        }else{
            g.setColor(Color.red);
            g.dispose();
        }
        //从ss中取出每一颗子弹，并画出
        for(int i=0;i<myTank.ss.size();i++){
            Shot myShot = myTank.ss.get(i);
            //画出子弹
            if(myTank.isLive==true&&myShot.isLive==true){
                drawBullet(myShot.x,myShot.y,g,myShot.direct,0);
            }
            if(myShot.isLive == false){
                myTank.ss.remove(myShot);
            }
        }

        // 画出敌方坦克
        for (int i = 0; i < ets.size(); i++) {
            EnemyTank et = ets.get(i);
            if (et.isLive) {
                drawTank(et.getX(), et.getY(), g, et.getDirect(),1);
                // 画出敌人坦克子弹
                for (int j = 0; j < et.ss.size(); j++) {
                    Shot enemyShot = et.ss.get(j);
                    if (enemyShot.isLive) {
                        drawBullet(enemyShot.x, enemyShot.y,g,enemyShot.direct,0);
                    } else {
                        // 如果敌人的坦克死亡
                        et.ss.remove(enemyShot);
                    }
                }
            }
        }

        //画出炸弹
        for (int i = 0; i < bombs.size(); i++) {
            // 取出炸弹
            Bomb b = bombs.get(i);
            if (b.life > 5) {
                g.drawImage(bomb1, b.x, b.y, 30, 30, this);
            } else if (b.life > 4) {
                g.drawImage(bomb2, b.x, b.y, 30, 30, this);
            } else if (b.life > 3) {
                g.drawImage(bomb3, b.x, b.y, 30, 30, this);
            } else if (b.life > 2) {
                g.drawImage(bomb4, b.x, b.y, 30, 30, this);
            } else if (b.life > 1) {
                g.drawImage(bomb5, b.x, b.y, 30, 30, this);
            }


            b.lifeDown();
            // 如果life为 0 酒吧炸弹从bombs向量去掉
            if (b.life == 0) {
                bombs.remove(b);
            }
        }

        //画出墙
        //1 普通墙
        for(int i=0;i<cws.size();i++){
            CommonWall cw = cws.get(i);
            if(cw.isLive){
                drawWall(cw.getX(),cw.getY(),g,0);
            }
            /*else{
                synchronized (cws){
                    cws.remove(cw);
                }
            }*/

        }
        //2 金属墙
        for(int i=0;i<mws.size();i++){
            MetalWall mw = mws.get(i);
            if(mw.isLive){
                drawWall(mw.getX(),mw.getY(),g,1);
            }
        }

        //画出家
        if(home.isLive){
            drawHome(home.getX(),home.getY(),g);
        }else{
            g.dispose();
        }

        //画出血包
        if(blood.isLive){
            g.drawImage(bloodImage,blood.x,blood.y,this);
        }
    }
    //敌方攻击
    public void hitMe(){
        //取出每个敌人的坦克
        for(int i=0;i<ets.size();i++){
            //取出坦克
            EnemyTank et = ets.get(i);
            //取出每颗子弹
            for(int j=0;j<et.ss.size();j++){
                Shot enemyShot = et.ss.get(j);
                //攻击我方坦克
                hitTank(enemyShot,myTank,1);
            }
        }
        System.out.println(myTank.life);
    }
    //敌方攻击
    public void hitWall(){
        //取出每个敌人的坦克
        for(int i=0;i<ets.size();i++){
            //取出坦克
            EnemyTank et = ets.get(i);
            //取出每颗子弹
            for(int j=0;j<et.ss.size();j++){
                Shot enemyShot = et.ss.get(j);
                //攻击普通墙
                for(int k=0;k<cws.size();k++){
                    synchronized (cws){
                        CommonWall cw = cws.get(k);
                        hitCommonWall(enemyShot,cw);
                    }
                }
                //攻击金属墙
                for(int l=0;l<mws.size();l++){
                    MetalWall mw = mws.get(l);
                    hitMetalWall(enemyShot,mw);
                }
            }
        }
    }
    //敌方攻击
    public void hitHome(){
        //取出每个敌人的坦克
        for(int i=0;i<ets.size();i++){
            //取出坦克
            EnemyTank et = ets.get(i);
            //取出每颗子弹
            for(int j=0;j<et.ss.size();j++){
                Shot enemyShot = et.ss.get(j);
                //攻击家
                hitHome(enemyShot);
            }
        }
    }
    //我方攻击
    public void hitEnemy(){
        // 判断是否击中敌人坦克
        for (int i = 0; i < myTank.ss.size(); i++) {
            Shot myShot = myTank.ss.get(i);
            // 判断子弹是否有效
            if (myShot.isLive) {
                // 取出每个坦克，与他判断
                for (int j = 0; j < ets.size(); j++) {
                    EnemyTank et = ets.get(j);
                    if (et.isLive) {
                        hitTank(myShot, et,0);
                    }
                }
                //取出墙，与之判断
                //1 普通墙
                for(int k = 0;k < cws.size();k++){
                    synchronized (cws){
                        CommonWall cw = cws.get(k);
                            hitCommonWall(myShot,cw);
                    }
                }
                //2 金属墙
                for(int l = 0;l < mws.size();l++){
                    MetalWall mw = mws.get(l);
                    if(mw.isLive){
                        hitMetalWall(myShot,mw);
                    }
                }
            }
        }
    }


    //-------------------------------------------------------------------------//
    // 写一个函数专门判断: 子弹是否击中坦克
    public void hitTank(Shot shot, Tank tank,int type) {
        switch (type){
            case 0:{
                // 判断该坦克的方向
                switch (tank.direct) {
                    // 方向上或者下，是相同的
                    case 0:
                    case 1:
                        if (shot.getRect().intersects(tank.getRect())) {
                            // 坦克死亡
                            tank.isLive = false;
                            // 击中，子弹死亡
                            shot.isLive = false;
                            // 触发爆炸，放入vector
                            Bomb bomb = new Bomb(tank.x, tank.y);
                            bombs.add(bomb);
                        }
                        break;
                    // 方向左右，相同
                    case 2:
                    case 3:
                        if (shot.getRect().intersects(tank.getRect())) {
                            tank.isLive = false;
                            shot.isLive = false;
                            Bomb bomb = new Bomb(tank.x, tank.y);
                            bombs.add(bomb);
                        }
                        break;
                    default:
                        break;
                }
            }
            case 1:{
                // 判断该坦克的方向
                switch (tank.direct) {
                    // 方向上或者下，是相同的
                    case 0:
                    case 1:
                        if (shot.getRect().intersects(tank.getRect())) {
                            if(tank.life >= 20) {
                                // 坦克死亡
                                tank.life -= 20;
                                // 击中，子弹死亡
                                shot.isLive = false;
                            }else{
                                // 坦克死亡
                                tank.isLive = false;
                                // 击中，子弹死亡
                                shot.isLive = false;
                                // 触发爆炸，放入vector
                                Bomb bomb = new Bomb(tank.x, tank.y);
                                bombs.add(bomb);
                            }
                        }
                        break;
                    // 方向左右，相同
                    case 2:
                    case 3:
                        if (shot.getRect().intersects(tank.getRect())) {
                            if(tank.life >= 20) {
                                // 坦克死亡
                                tank.life -= 20;
                                // 击中，子弹死亡
                                shot.isLive = false;
                            }else{
                                // 坦克死亡
                                tank.isLive = false;
                                // 击中，子弹死亡
                                shot.isLive = false;
                                // 触发爆炸，放入vector
                                Bomb bomb = new Bomb(tank.x, tank.y);
                                bombs.add(bomb);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    //写一个函数专门判断: 子弹是否击中墙
    //1 攻击普通墙
    public void hitCommonWall(Shot shot,CommonWall cw){
        if(shot.getRect().intersects(cw.getRect())){
            cw.isLive = false;
            shot.isLive = false;
            Bomb bomb = new Bomb(cw.x, cw.y);
            bombs.add(bomb);
        }
    }
    //2 攻击金属墙
    public void hitMetalWall(Shot shot,MetalWall mw){
        if(shot.getRect().intersects(mw.getRect())){
            mw.isLive = true;
            shot.isLive = false;
        }
    }
    //攻击家
    public void hitHome(Shot shot){
        if(shot.getRect().intersects(home.getRect())){
            home.isLive = false;
            shot.isLive = false;
        }
    }


    //写一个函数专门进行碰撞检测
    public void tankcollidetank(){
        for(int i=0;i<ets.size();i++){
            EnemyTank et1 = ets.get(i);
            for(int j=0;j<ets.size()-1;j++){
                EnemyTank et2 = ets.get(j);
                if(et1.getRect().intersects(et2.getRect())){
                    et1.setSpeed(0);
                    et2.setSpeed(0);
                    int dir1 = et1.getDirect();
                    int dir2 = et2.getDirect();
                    if(dir1 != r.nextInt(3))
                        et1.setSpeed(10);
                    if(dir2 != r.nextInt(3))
                        et2.setSpeed(10);
                }
            }
        }
    }




    /*
     * drawTank (坦克坐标x,y,画笔g,方向，坦克类型) 方法介绍：
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0:{
                switch (direct) {
                    case 0:
                        // 向上
                        g.drawImage(mytank_U,x,y,40,40,this);
                        break;
                    case 1:
                        // 向下w
                        g.drawImage(mytank_D,x,y,40,40,this);
                        break;
                    case 2:
                        // 向左
                        g.drawImage(mytank_L,x,y,40,40,this);
                        break;
                    case 3:
                        // 向右
                        g.drawImage(mytank_R,x,y,40,40,this);
                        break;
                    default:
                        break;
                }
            }
                break;
            case 1:{
                switch (direct) {
                    case 0:
                        // 向上
                        g.drawImage(enemytank_U,x,y,40,40,this);
                        break;
                    case 1:
                        // 向下w
                        g.drawImage(enemytank_D,x,y,40,40,this);
                        break;
                    case 2:
                        // 向左
                        g.drawImage(enemytank_L,x,y,40,40,this);
                        break;
                    case 3:
                        // 向右
                        g.drawImage(enemytank_R,x,y,40,40,this);
                        break;
                    default:
                        break;
                }
            }

            default:
                break;
        }
    }

    /**
     * drawBullet (子弹坐标x,y,画笔g,方向，子弹类型) 方法介绍：
     */
    public void drawBullet(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0: {
                switch (direct) {
                    case 0:
                        // 向上
                        g.drawImage(bullet_U, x, y,5,5, this);
                        break;
                    case 1:
                        // 向下w
                        g.drawImage(bullet_D, x, y, 5,5,this);
                        break;
                    case 2:
                        // 向左
                        g.drawImage(bullet_L, x, y, 5,5,this);
                        break;
                    case 3:
                        // 向右
                        g.drawImage(bullet_R, x, y, 5,5,this);
                        break;
                    default:
                        break;
                }
            }
            default:
                break;
        }
    }

    //drawWall (墙坐标x,y,画笔g,墙类型) 方法介绍：
    public void drawWall(int x,int y,Graphics g,int type){
        switch (type){
            //commonWall
            case 0:{
                g.drawImage(commonWall,x,y,20,20,this);
                break;
            }
            case 1:{
                g.drawImage(metalWall,x,y,20,20,this);
                break;
            }
            default:
                break;
        }
    }

    //drawHome(家坐标x,y,画笔g)
    public void drawHome(int x,int y,Graphics g){
        g.drawImage(homeImage,x,y,50,50,this);
    }
    //drawBlood


    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 实现接口 根据按键上下左右移动 可以控制速度和移动
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            myTank.setDirect(0);
            myTank.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            myTank.setDirect(1);
            myTank.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            myTank.setDirect(2);
            myTank.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            myTank.setDirect(3);
            myTank.moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // 开火
            if (myTank.ss.size() <= 100) {
                myTank.shotEnemy();
            }
        }
        // 判断玩家是否按下空格攻击键
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            hitEnemy();
            hitMe();
            hitWall();
            hitHome();
            repaint();
        }
    }
}
