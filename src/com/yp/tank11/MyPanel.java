package com.yp.tank11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements Runnable,KeyListener {
    public static int PanelWidth = 1100;
    public static int PanelHeight = 610;
    //初始化我方坦克
    int mysize = 1;
    Vector<MyTank> myTanks = new Vector<MyTank>();
    //定义敌军
    Vector<EnemyTank> ets = new Vector<EnemyTank>();
    //定义敌军数量
    public static int ensize = 3;
    //定义炸弹集合
    Vector<Bomb> bombs = new Vector<Bomb>();
    //定义普通墙
    int cwsize = 1;
    Vector<CommonWall> cws = new Vector<CommonWall>();
    //定义金属墙
    int mwsize = 1;
    Vector<MetalWall> mws = new Vector<MetalWall>();
    //定义家
    Home home = new Home();
    //定义血包
    Blood blood = new Blood();

    private Image bufferImage;

    public MyPanel(){
        //设定我方坦克出现的位置
        for(int i=0;i<mysize;i++){
            MyTank myTank = new MyTank(500,400, Direction.U);
            myTanks.add(myTank);
        }
        //初始化敌方坦克
        for(int i=0;i<ensize;i++){
            //创建敌人的坦克对象
            EnemyTank et = new EnemyTank(i*200,50,Direction.D);
            //加入坦克群
            ets.add(et);
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

    @Override
    public void update(Graphics g) {
        super.update(g);
        if(bufferImage == null){
            bufferImage = this.createImage(this.getWidth(),this.getHeight());
            System.out.println(this.getWidth());
        }
        Graphics gBuffer = bufferImage.getGraphics();//获取面板的画笔
        if(gBuffer != null){
            paint(gBuffer);
        }else{
            paint(g);
        }
        g.dispose();
        g.drawImage(bufferImage,0,0,null);//将内容一次性写到屏幕上
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //背景填充
        g.setColor(Color.BLACK);
        g.fillRect(0,0,PanelWidth, PanelHeight);

        //设置字体显示属性
        g.setColor(Color.green);
        g.setFont(new Font("TankGame", Font.BOLD, 20));
        g.drawString("区域内还有敌方坦克: ", 250, 70);
        g.setFont(new Font("Tank", Font.ITALIC, 30));
        g.drawString("" + ets.size(), 460, 70);
        g.setFont(new Font("Tank", Font.BOLD, 20));
        g.drawString("剩余生命值: ", 550, 70);
        g.setFont(new Font("Tank", Font.ITALIC, 30));
        for(int i=0;i<myTanks.size();i++){
            MyTank myTank = myTanks.get(i);
            g.drawString("" + myTank.life, 680, 70);
        }
        //胜负
        g.setColor(Color.red);
        //赢
        if(ets.size() == 0&&home.isLive&&myTanks.size() != 0){
            g.setFont(new Font("TankGame",Font.BOLD,60));
            g.drawString("你赢了！",450,200);
        }
        //输
        if(ets.size()!=0&&(home.isLive == false||myTanks.size() == 0)){
            g.setFont(new Font("TankGame",Font.BOLD,60));
            g.drawString("你输了！",450,200);

        }

        //画出我方坦克
        for(int i=0;i<myTanks.size();i++){
            MyTank myTank = myTanks.get(i);
            if(myTank.isLive){
                myTank.drawLife(g);
                myTank.drawMytank(myTank.getX(),myTank.getY(),g,myTank.direction);
                //从ss中取出每一颗子弹，并画出
                for(int j=0;j<myTank.bullets.size();j++){
                    Bullet myBullet = myTank.bullets.get(j);
                    //画出子弹
                    if(myBullet.isLive==true){
                        myBullet.drawBullet(myBullet.x,myBullet.y,g,myBullet.direction);
                    }else{
                        myTank.bullets.remove(myBullet);
                    }
                }
                //从mm中取出每颗导弹，并画出
                for(int j=0;j<myTank.missiles.size();j++){
                    Missile missile = myTank.missiles.get(j);
                    //画出导弹
                    if(missile.isLive == true){
                        missile.drawMissile(missile.x,missile.y,g,missile.direction);
                    }else{
                        myTank.missiles.remove(missile);
                    }
                }
            }else{
                myTanks.remove(myTank);
                Bomb bomb = new Bomb(myTank.getX(),myTank.getY());
                bombs.add(bomb);
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
                et.drawLife(g);
                et.drawEnemytank(et.getX(), et.getY(), g, et.direction);
                // 画出敌人坦克子弹
                for (int j = 0; j < et.bullets.size(); j++) {
                    Bullet enemyBullet = et.bullets.get(j);
                    if (enemyBullet.isLive) {
                        enemyBullet.drawBullet(enemyBullet.x, enemyBullet.y,g,enemyBullet.direction);
                    } else {
                        // 如果敌人的坦克死亡，就从Vector去掉
                        et.bullets.remove(enemyBullet);
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
            for(int i=0;i<myTanks.size();i++) {
                MyTank myTank = myTanks.get(i);
                myTank.isLive = false;
            }
        }
        //画出血包
        blood.drawBlood(g);
    }
    //--------------------------------------------------------------------------
    //攻击效果
    //敌方攻击
    public void hitMe(){
        //取出每个敌人的坦克
        for(int i=0;i<ets.size();i++){
            //取出坦克
            EnemyTank et = ets.get(i);
            //取出每颗子弹
            for(int j=0;j<et.bullets.size();j++){
                Bullet enemyBullet = et.bullets.get(j);
                //攻击我方坦克
                for(int k=0;k<myTanks.size();k++){
                    MyTank myTank = myTanks.get(k);
                    enemyBullet.bulletTank(myTank);
                    //碰撞检测
                    //1 子弹
                    for(int n=0;n<myTank.bullets.size();n++){
                        Bullet myBullet = myTank.bullets.get(n);
                        enemyBullet.collideBullet(myBullet);
                    }
                    //2 导弹
                    for(int nn = 0;nn<myTank.missiles.size();nn++){
                        Missile missile = myTank.missiles.get(nn);
                        enemyBullet.collideMissile(missile);
                    }

                }
                //攻击普通墙
                for(int m=0;m<cws.size();m++){
                    CommonWall cw = cws.get(m);
                    enemyBullet.hitCommonWall(cw);
                }
                //攻击金属墙
                for(int l=0;l<mws.size();l++){
                    MetalWall mw = mws.get(l);
                    enemyBullet.hitMetalWall(mw);
                }
                //攻击家
                enemyBullet.hitHome(home);
            }
        }
    }

    //我方子弹攻击
    public void bulletEnemy(){
        // 判断是否击中敌人坦克
        for(int m=0;m<myTanks.size();m++){
            MyTank myTank = myTanks.get(m);
            for (int i = 0; i < myTank.bullets.size(); i++) {
                Bullet myBullet = myTank.bullets.get(i);
                // 判断子弹是否有效
                if (myBullet.isLive) {
                    // 取出每个坦克，与他判断
                    for (int j = 0; j < ets.size(); j++) {
                        EnemyTank et = ets.get(j);
                        if (et.isLive) {
                            myBullet.bulletTank(et);
                        }
                    }
                    //取出墙，与之判断
                    //1 普通墙
                    for(int k = 0;k < cws.size();k++){
                        CommonWall cw = cws.get(k);
                        if(cw.isLive){
                            myBullet.hitCommonWall(cw);
                        }
                    }
                    //2 金属墙
                    for(int l = 0;l < mws.size();l++){
                        MetalWall mw = mws.get(l);
                        if(mw.isLive){
                            myBullet.hitMetalWall(mw);
                        }
                    }
                }
            }
        }
    }

    //我方导弹攻击
    public void missileEnemy(){
        // 判断是否击中敌人坦克
        for(int m=0;m<myTanks.size();m++) {
            MyTank myTank = myTanks.get(m);
            for (int i = 0; i < myTank.missiles.size(); i++) {
                Missile missile = myTank.missiles.get(i);
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
                    for (int k = 0; k < cws.size(); k++) {
                        CommonWall cw = cws.get(k);
                        if (cw.isLive) {
                            missile.hitCommonWall(cw);
                        }
                    }
                    //2 金属墙
                    for (int l = 0; l < mws.size(); l++) {
                        MetalWall mw = mws.get(l);
                        if (mw.isLive) {
                            missile.hitMetalWall(mw);
                        }
                    }
                }
            }
        }
    }

    //吃血包
    public void eatBlood(){
        for(int m=0;m<myTanks.size();m++) {
            MyTank myTank = myTanks.get(m);
            myTank.getBlood(blood);
        }
    }

    //碰撞检测
    //1我方坦克
    public void Mycollide(){
        for(int m=0;m<myTanks.size();m++) {
            MyTank myTank = myTanks.get(m);
            //1 普通墙
            for (int i = 0; i < cws.size(); i++) {
                CommonWall cw = cws.get(i);
                myTank.collideCommonWall(cw);
            }
            //2 金属墙
            for (int j = 0; j < mws.size(); j++) {
                MetalWall mw = mws.get(j);
                myTank.collideMetalWall(mw);
            }
            //3 其他坦克
            for (int k = 0; k < ets.size(); k++) {
                EnemyTank et = ets.get(k);
                myTank.collideTank(et);
            }
            //4 家
            myTank.collideHome(home);
        }
    }

    //2敌方坦克
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
        for(int m=0;m<myTanks.size();m++) {
            MyTank myTank = myTanks.get(m);
            myTank.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for(int m=0;m<myTanks.size();m++) {
            MyTank myTank = myTanks.get(m);
            myTank.keyReleased(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            bulletEnemy();
            missileEnemy();
            hitMe();
            eatBlood();
            Mycollide();
            Encollide();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }
}
