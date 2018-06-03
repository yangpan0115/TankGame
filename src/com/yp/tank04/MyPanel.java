package com.yp.tank04;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements Runnable,KeyListener {
    //初始化我方坦克
    MyTank myTank = null;
    //定义敌军
    Vector<EnemyTank> ets = new Vector<EnemyTank>();
    //定义敌军数量
    int ensize = 1;
    //定义炸弹集合
    Vector<Bomb> bombs = new Vector<Bomb>();

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

    public MyPanel(){
        //设定我方坦克出现的位置
        myTank = new MyTank(500,300);
        //初始化敌方坦克
        for(int i=0;i<ensize;i++){
            //创建敌人的坦克对象
            EnemyTank et = new EnemyTank((i+1)*150,100);
            et.setColor(1);
            //et.setDirect(1);
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

    }

    protected void paintComponent(Graphics g){

        super.paintComponent(g);
        // 背景填充
        g.fillRect(0, 0, 1366, 700);
        //画出我方坦克
        if(myTank.isLive){
            drawTank(myTank.getX(),myTank.getY(),g,myTank.direct,0);
        }
        //从ss中取出每一颗子弹，并画出
        for(int i=0;i<myTank.ss.size();i++){
            Shot myShot = myTank.ss.get(i);
            //画出子弹
            if(myShot!=null&&myShot.isLive==true){
                drawBullet(myShot.x,myShot.y,g,myShot.direct,0);
                //g.draw3DRect(myShot.x,myShot.y,3,3,false);
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
                        //g.draw3DRect(enemyShot.x, enemyShot.y, 3, 3, false);
                    } else {
                        // 如果敌人的坦克死亡，就从Vector去掉
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
                hitTank(enemyShot,myTank);
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
                        hitTank(myShot, et);
                    }
                }
            }
        }
    }

    // 写一个函数专门判断: 子弹是否击中敌人坦克
    public void hitTank(Shot s, Tank et) {
        // 判断该坦克的方向
        switch (et.direct) {
            // 方向上或者下，是相同的
            case 0:
            case 1:
                if (s.getRect().intersects(et.getRect())) {
                    // 坦克死亡
                    et.isLive = false;
                    // 击中，子弹死亡
                    s.isLive = false;
                    // 触发爆炸，放入vector
                    Bomb b = new Bomb(et.x, et.y);
                    bombs.add(b);
                }
                break;
            // 方向左右，相同
            case 2:
            case 3:
                if (s.getRect().intersects(et.getRect())) {
                    et.isLive = false;
                    s.isLive = false;

                    Bomb b = new Bomb(et.x, et.y);
                    bombs.add(b);
                }
                break;

            default:
                break;

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
                        g.drawImage(mytank_U,x,y,30,30,this);
                        break;
                    case 1:
                        // 向下w
                        g.drawImage(mytank_D,x,y,30,30,this);
                        break;
                    case 2:
                        // 向左
                        g.drawImage(mytank_L,x,y,30,30,this);
                        break;
                    case 3:
                        // 向右
                        g.drawImage(mytank_R,x,y,30,30,this);
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
                        g.drawImage(enemytank_U,x,y,30,30,this);
                        break;
                    case 1:
                        // 向下w
                        g.drawImage(enemytank_D,x,y,30,30,this);
                        break;
                    case 2:
                        // 向左
                        g.drawImage(enemytank_L,x,y,30,30,this);
                        break;
                    case 3:
                        // 向右
                        g.drawImage(enemytank_R,x,y,30,30,this);
                        break;
                    default:
                        break;
                }
            }

            default:
                break;
        }
        // repaint(); 因为监听器里面有repaint() 所以不用在加repaint()了
    }

    /**
     * drawbullet (子弹坐标x,y,画笔g,方向，子弹类型) 方法介绍：
     */
    public void drawBullet(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0: {
                switch (direct) {
                    case 0:
                        // 向上
                        g.drawImage(bullet_U, x, y, 5, 5, this);
                        break;
                    case 1:
                        // 向下w
                        g.drawImage(bullet_D, x, y, 5, 5, this);
                        break;
                    case 2:
                        // 向左
                        g.drawImage(bullet_L, x, y, 5, 5, this);
                        break;
                    case 3:
                        // 向右
                        g.drawImage(bullet_R, x, y, 5, 5, this);
                        break;
                    default:
                        break;
                }
            }
            default:
                break;
        }
    }



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
        while (true) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            hitEnemy();
            hitMe();
            repaint();
        }
    }
}
