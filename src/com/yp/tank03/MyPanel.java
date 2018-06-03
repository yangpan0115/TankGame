package com.yp.tank03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    int enSize = 40;
    Hero hero = null;

    // 定义敌军
    Vector<EnemyTank> ets = new Vector<EnemyTank>();

    // 定义炸弹集合
    Vector<Bomb> bombs = new Vector<Bomb>();

    // 定义8张图片,三张图片组成一颗炸弹
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    Image image4 = null;
    Image image5 = null;
    Image image6 = null;
    Image image7 = null;
    Image image8 = null;
    Image image9 = null;
    Image image10 = null;

    public MyPanel() {
        hero = new Hero(100, 100);// 设置坦克出现的位置（10，10）
        // 初始化敌人的坦克
        for (int i = 0; i < enSize; i++) {
            // 创建对人的坦克对象
            EnemyTank et = new EnemyTank((i + 1) * 30, 0);
            et.setColor(1);
            et.setDirect(1);
            ets.add(et);
        }
        // 初始化图片
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/0.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/1.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/2.gif"));
        image4 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/3.gif"));
        image5 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/4.gif"));
        image6 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/5.gif"));
        image7 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/6.gif"));
        image8 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/7.gif"));
        image9 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/8.gif"));
        image10 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/images/9.gif"));

    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.fillRect(0, 0, 1366, 768);// 背景填充

        // 画出我方坦克
        drawTank(hero.getX(), hero.getY(), g, hero.Direct, 0);// 一定要传入画笔g

        // 从ss中取出每一颗子弹，并画出
        for (int i = 0; i < hero.ss.size(); i++) {
            Shot myShot = hero.ss.get(i);
            // 画出子弹
            if (myShot.isLive == true) {
                // System.out.format("%d %d  ", hero.s.x,hero.s.y);
                g.setColor(Color.green);
                g.draw3DRect(myShot.x, myShot.y, 3, 3, false);
            }
            if (myShot.isLive == false) {
                hero.ss.remove(myShot);// 记住是myShot
            }
        }

        //画出炸弹

        for(int i=0;i<bombs.size();i++){
            //取出炸弹
            Bomb b = bombs.get(i);

            if(b.life>10){
                g.drawImage(image1, b.x, b.y, 30, 30, this);
            }else if(b.life>9){
                g.drawImage(image2, b.x, b.y, 30, 30, this);
            }else if(b.life>8){
                g.drawImage(image3, b.x, b.y, 30, 30, this);
            }else if(b.life>7){
                g.drawImage(image4, b.x, b.y, 30, 30, this);
            }else if(b.life>6){
                g.drawImage(image5, b.x, b.y, 30, 30, this);
            }else if(b.life>5){
                g.drawImage(image6, b.x, b.y, 30, 30, this);
            }else if(b.life>4){
                g.drawImage(image7, b.x, b.y, 30, 30, this);
            }else if(b.life>3){
                g.drawImage(image8, b.x, b.y, 30, 30, this);
            }else if(b.life>2){
                g.drawImage(image9, b.x, b.y, 30, 30, this);
            }else if(b.life>1){
                g.drawImage(image10, b.x, b.y, 30, 30, this);
            }

            b.lifeDown();
            //System.out.println("图片");
            //System.out.format("1+%d\n",i);
            //如果life为 0  就把炸弹从bombs向量去掉
            if(b.life==0){
                bombs.remove(b);
            }
        }


        // 画出敌方坦克
        for (int i = 0; i < ets.size(); i++) {
            EnemyTank et = ets.get(i);
            if (et.isLive) {
                drawTank(et.getX(), et.getY(), g, et.getDirect(), ets.get(i)
                        .getColor());
            }
        }
    }

    // 写一个函数专门判断: 子弹是否击中敌人坦克
    public void hitTank(Shot s, EnemyTank et) {
        // 判断该坦克的方向
        switch (et.Direct) {
            // 方向上或者下，是相同的
            case 0:
            case 1:
                if (s.x > et.x && s.x < et.x + 20 && s.y > et.y && s.y < et.y + 30) {
                    // 击中，子弹死亡
                    s.isLive = false;
                    // 坦克死亡
                    et.isLive = false;
                    // 触发爆炸，放入vector
                    Bomb b = new Bomb(et.x, et.y);
                    bombs.add(b);
                }
                break;
            // 方向左右，相同
            case 2:
            case 3:
                if (s.x > et.x && s.x < et.x + 30 && s.y > et.y && s.y <= et.y + 20) {
                    s.isLive = false;
                    et.isLive = false;
                    Bomb b = new Bomb(et.x, et.y);
                    bombs.add(b);
                }
                break;

            default:
                break;
        }
    }

    /*
     * drawTank (坦克坐标x,y,画笔g,方向，坦克类型) 方法介绍： 可以设置-->坦克的颜色（类型：敌方坦克，我方坦克），方向，出现的坐标
     *
     * 如果type是default 则默认颜色为画出黑色坦克
     *
     * 封装性：将坦克封装到方法中。
     */

    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0:
                g.setColor(Color.green);
                break;
            case 1:
                g.setColor(Color.red);
            default:
                break;
        }
        switch (direct) {
            case 0:
                // 向上
                g.fill3DRect(x, y, 5, 30, false);
                g.fill3DRect(x + 15, y, 5, 30, false);
                g.fill3DRect(x + 5, y + 5, 10, 20, false);
                g.fillOval(x + 4, y + 10, 10, 10);
                g.drawLine(x + 9, y + 15, x + 9, y);
                break;
            case 1:
                // 向下
                g.fill3DRect(x, y, 5, 30, false);
                g.fill3DRect(x + 15, y, 5, 30, false);
                g.fill3DRect(x + 5, y + 5, 10, 20, false);
                g.fillOval(x + 4, y + 10, 10, 10);
                g.drawLine(x + 9, y + 15, x + 9, y + 30);
                break;
            case 2:
                // 向左
                g.fill3DRect(x, y, 30, 5, false);
                g.fill3DRect(x, y + 15, 30, 5, false);
                g.fill3DRect(x + 5, y + 5, 20, 10, false);
                g.fillOval(x + 9, y + 4, 10, 10);
                g.drawLine(x + 5, y + 9, x - 4, y + 9);
                break;
            case 3:
                // 向右
                g.fill3DRect(x, y, 30, 5, false);
                g.fill3DRect(x, y + 15, 30, 5, false);
                g.fill3DRect(x + 5, y + 5, 20, 10, false);
                g.fillOval(x + 9, y + 4, 10, 10);
                g.drawLine(x + 15, y + 9, x + 30, y + 9);
                break;
            default:
                break;
        }
        // repaint(); 因为监听器里面有repaint() 所以不用在加repaint()了
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {// 实现接口 根据按键上下左右移动 可以控制速度和移动
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            hero.setDirect(0);
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            hero.setDirect(1);
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            hero.setDirect(2);
            hero.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            hero.setDirect(3);
            hero.moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // 开火
            if (hero.ss.size() <= 100) {
                hero.shotEnemy();
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
        // 每隔100毫秒 重新画图
        while (true) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 判断是否击中
            for (int i = 0; i < hero.ss.size(); i++) {
                Shot myShot = hero.ss.get(i);
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
            repaint();
        }
    }
}
