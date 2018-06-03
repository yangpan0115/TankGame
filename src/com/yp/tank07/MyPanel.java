package com.yp.tank07;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements Runnable,KeyListener {
    boolean printable = true;
    //幕布的大小
    Image screenImage = null;
    public static int PanelWidth = 1100;
    public static int PanelHeight = 600;
    //初始化我方坦克
    MyTank myTank = new MyTank();
    //定义敌军
    Vector<EnemyTank> ets = new Vector<EnemyTank>();
    //定义敌军数量
    int ensize = 3;
    //定义炸弹集合
    Vector<Bomb> bombs = new Vector<Bomb>();
    //定义普通墙
    int cwsize = 3;
    Vector<CommonWall> cws = new Vector<CommonWall>();
    //定义金属墙
    int mwsize = 3;
    Vector<MetalWall> mws = new Vector<MetalWall>();
    //定义家
    Home home = new Home();
    //定义血包
    Blood blood = new Blood();

    public MyPanel(){
        //设定我方坦克出现的位置
        myTank = new MyTank(500,400,Direction.U);
        //初始化敌方坦克
        for(int i=0;i<ensize;i++){
            //创建敌人的坦克对象
            EnemyTank et1 = new EnemyTank((i+1)*250,50,Direction.D);
            //启动敌方坦克
            Thread t1 = new Thread(et1);
            t1.start();
            //给敌人添加子弹
            Shot shot1 = new Shot(et1.x,et1.y,Direction.D);

            //加入给敌方坦克
            et1.ss.add(shot1);
            //发射子弹
            Thread tt1 = new Thread(shot1);
            tt1.start();

            //加入坦克群
            ets.add(et1);

        }

        //初始化墙
        for(int i=0;i<cwsize;i++){
            CommonWall cw1 = new CommonWall(i*40,200);
            cws.add(cw1);

            CommonWall cw3 = new CommonWall(i*40+300,200);
            cws.add(cw3);

            CommonWall home1 = new CommonWall(430,520-i*40);
            cws.add(home1);

            CommonWall home3 = new CommonWall(550,480+i*40);
            cws.add(home3);

            CommonWall home4 = new CommonWall(i*40+430,560);
            cws.add(home4);

        }
        for(int i=0;i<mwsize;i++){
            MetalWall mw1 = new MetalWall((i+1)*40+200,300);
            mws.add(mw1);

            MetalWall mw2 = new MetalWall((i+1)*40+500,300);
            mws.add(mw2);

            MetalWall mw3 = new MetalWall((i+1)*40+800,300);
            mws.add(mw3);

            MetalWall home2 = new MetalWall(i*40+470,440);
            mws.add(home2);

        }
        //初始化家
        home = new Home(490,500);
    }

    //作图
    protected void paintComponent(Graphics g){
        //将画板上原有的东西清除
        super.paintComponent(g);
        //背景填充

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, PanelWidth, PanelHeight);

        //g.fillRect(0, 0, PanelWidth, PanelHeight);
        //画出我方坦克
        if(myTank.isLive){
            myTank.drawMytank(myTank.getX(),myTank.getY(),g,myTank.direction);
        }
        //从ss中取出每一颗子弹，并画出
        for(int i=0;i<myTank.ss.size();i++){
            Shot myShot = myTank.ss.get(i);
            //画出子弹
            if(myShot.isLive==true){
                myShot.drawBullet(myShot.x,myShot.y,g,myShot.direction);
            }else{
                myTank.ss.remove(myShot);
            }
        }

        //从mm中取出每颗导弹，并画出
        for(int i=0;i<myTank.mm.size();i++){
            Missile missile = myTank.mm.get(i);
            //画出导弹
            if(missile.isLive == true){
                missile.drawMissle(missile.x,missile.y,g,missile.direction);
            }else{
                myTank.mm.remove(missile);
            }
        }

        //画出炸弹
        for (int i = 0; i < bombs.size(); i++) {
            // 取出炸弹
            Bomb b = bombs.get(i);
            b.drawBomb(g);
            // 如果life为 0 就把炸弹从bombs向量去掉
            if (b.life == 0) {
                bombs.remove(b);
            }
        }
        // 画出敌方坦克
        for (int i = 0; i < ets.size(); i++) {
            EnemyTank et = ets.get(i);
            if (et.isLive) {
                et.drawEnemytank(et.getX(), et.getY(), g, et.direction);
                // 画出敌人坦克子弹
                for (int j = 0; j < et.ss.size(); j++) {
                    Shot enemyShot = et.ss.get(j);
                    if (enemyShot.isLive&&et.time>=5) {
                        enemyShot.drawBullet(enemyShot.x, enemyShot.y,g,enemyShot.direction);
                    } else {
                        // 如果敌人的坦克死亡，就从Vector去掉
                        et.ss.remove(enemyShot);
                    }
                }
            }else{
                ets.remove(et);
                Bomb bomb = new Bomb(et.getX(),et.getY());
                bombs.add(bomb);
            }
        }

        //画出墙
        //1 普通墙
        for(int i=0;i<cws.size();i++){
            CommonWall cw = cws.get(i);
            if(cw.isLive){
                cw.drawCommonWall(g);
            }else{
                cws.remove(cw);
                Bomb bomb = new Bomb(cw.getX(), cw.getY());
                bombs.add(bomb);
            }

        }
        //2 金属墙
        for(int i=0;i<mws.size();i++){
            MetalWall mw = mws.get(i);
            if(mw.isLive){
                mw.drawMetalWall(g);
            }else{
                mws.remove(mw);
                Bomb bomb = new Bomb(mw.getX(), mw.getY());
                bombs.add(bomb);
            }
        }

        //画出家
        if(home.isLive){
            home.drawHome(g);
        }else{
            myTank.isLive = false;
        }
        //画出血包
        blood.drawBlood(g);

        //画出生命值
        //1 我方坦克
        myTank.drawlife(g);
        //2 敌方坦克
        for(int i=0;i<ets.size();i++){
            EnemyTank et = ets.get(i);
            et.drawlife(g);
        }

        //显示
        //设置字体显示属性
        Color c = g.getColor();
        g.setColor(Color.green);

        Font f1 = g.getFont();
        g.setFont(new Font("TankGame", Font.BOLD, 20));
        g.drawString("区域内还有敌方坦克: ", 250, 70);
        g.setFont(new Font("Tank", Font.ITALIC, 30));
        g.drawString("" + ets.size(), 460, 70);
        g.setFont(new Font("Tank", Font.BOLD, 20));
        g.drawString("剩余生命值: ", 550, 70);
        g.setFont(new Font("Tank", Font.ITALIC, 30));
        g.drawString("" + myTank.life, 680, 70);
        g.setFont(f1);
        g.setColor(c);

        Color c2 = g.getColor();
        g.setColor(Color.red);
        //赢
        if(ets.size() == 0&&home.isLive&&myTank.isLive){
            //g.clearRect(0,0,PanelWidth,PanelHeight);
            Font f = g.getFont();
            g.setFont(new Font("TankGame",Font.BOLD,60));
            g.drawString("你赢了！",500,300);
            g.setFont(f);
        }
        //输
        if(ets.size()!=0&&(home.isLive == false||myTank.isLive == false)){
            //g.clearRect(0,0,PanelWidth,PanelHeight);
            Font f = g.getFont();
            g.setFont(new Font("TankGame",Font.BOLD,60));
            g.drawString("你输了！",500,300);
            g.setFont(f);
        }
        g.setColor(c2);

    }
    //--------------------------------------------------------------------------
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
                enemyShot.shotTank(myTank);
                //攻击普通墙
                for(int k=0;k<cws.size();k++){
                    CommonWall cw = cws.get(k);
                    enemyShot.hitCommonWall(cw);
                }
                //攻击金属墙
                for(int l=0;l<mws.size();l++){
                    MetalWall mw = mws.get(l);
                    enemyShot.hitMetalWall(mw);
                }
                //攻击家
                enemyShot.hitHome(home);
            }
        }
    }
    //我方攻击
    public void shotEnemy(){
        // 判断是否击中敌人坦克
        for (int i = 0; i < myTank.ss.size(); i++) {
            Shot myShot = myTank.ss.get(i);
            // 判断子弹是否有效
            if (myShot.isLive) {
                // 取出每个坦克，与他判断
                for (int j = 0; j < ets.size(); j++) {
                    EnemyTank et = ets.get(j);
                    if (et.isLive) {
                        myShot.shotTank(et);
                    }
                }
                //取出墙，与之判断
                //1 普通墙
                for(int k = 0;k < cws.size();k++){
                    CommonWall cw = cws.get(k);
                    if(cw.isLive){
                        myShot.hitCommonWall(cw);
                    }
                }
                //2 金属墙
                for(int l = 0;l < mws.size();l++){
                    MetalWall mw = mws.get(l);
                    if(mw.isLive){
                        myShot.hitMetalWall(mw);
                    }
                }
            }
        }
    }

    //我方攻击
    public void missileEnemy(){
        // 判断是否击中敌人坦克
        for (int i = 0; i < myTank.mm.size(); i++) {
            Missile missile = myTank.mm.get(i);
            // 判断子弹是否有效
            if (missile.isLive) {
                // 取出每个坦克，与他判断
                for (int j = 0; j < ets.size(); j++) {
                    EnemyTank et = ets.get(j);
                    if (et.isLive) {
                        missile.missileTank(et);
                    }
                }
                //取出墙，与之判断
                //1 普通墙
                for(int k = 0;k < cws.size();k++){
                    CommonWall cw = cws.get(k);
                    if(cw.isLive){
                        missile.hitCommonWall(cw);
                    }
                }
                //2 金属墙
                for(int l = 0;l < mws.size();l++){
                    MetalWall mw = mws.get(l);
                    if(mw.isLive){
                        missile.hitMetalWall(mw);
                    }
                }
            }
        }
    }

    //吃血包
    public void eatBlood(){
        myTank.getBlood(blood);
    }

    //碰撞检测
    // 我方坦克
    public void Mycollide(){
        //1 普通墙
        for(int i=0;i<cws.size();i++){
            CommonWall cw = cws.get(i);
            myTank.collideCommonWall(cw);
        }
        //2 金属墙
        for(int j=0;j<mws.size();j++){
            MetalWall mw = mws.get(j);
            myTank.collideMetalWall(mw);
        }
        //3 其他坦克
        for(int k=0;k<ets.size();k++){
            EnemyTank et = ets.get(k);
            myTank.collideTank(et);
        }
        //4 家
        myTank.collideHome(home);
    }

    //敌方坦克
    public void Encollide(){
        for(int i=0;i<ets.size();i++){
            EnemyTank et = ets.get(i);
            //1 普通墙
            for(int l=0;l<cws.size();l++){
                CommonWall cw = cws.get(l);
                et.collideCommonWall(cw);
            }
            //2 金属墙
            for(int j=0;j<mws.size();j++){
                MetalWall mw = mws.get(j);
                et.collideMetalWall(mw);
            }
            //3 其他坦克
            for(int k=0;k<ets.size();k++){
                EnemyTank et1 = ets.get(k);
                et.collideTank(et1);
            }
            //4 家
            et.collideHome(home);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 实现接口 根据按键上下左右移动 可以控制速度和移动
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            myTank.direction = Direction.U;
            myTank.move();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            myTank.direction = Direction.D;
            myTank.move();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            myTank.direction = Direction.L;
            myTank.move();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            myTank.direction = Direction.R;
            myTank.move();
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // 开火
            if (myTank.ss.size() <= 5) {
                myTank.shotEnemy();
            }
        }else if(e.getKeyCode() == KeyEvent.VK_F1){
            //导弹
            if(myTank.mm.size() <= 1){
                myTank.missileEnemy();
                myTank.lifeDown();
            }
        }
        //repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (printable) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            shotEnemy();
            missileEnemy();
            hitMe();
            eatBlood();
            Mycollide();
            Encollide();

            repaint();
        }
    }
}
