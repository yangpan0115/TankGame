package com.yp.tank13;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Bullet{
    public int x;
    public int y;
    public Direction direction;
    public int speed = 10;
    public boolean isLive = true;

    public static final int width = 5;
    public static final int height = 5;

    public Bullet(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Bullet(){}

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] bulletImage1 = null;
    private static Map<Integer, Image> bulletimgs1 = new HashMap<Integer, Image>(); // 定义Map键值对，是不同方向对应不同的弹头

    static {
        bulletImage1 = new Image[]{
            tk.getImage(Bullet.class.getResource("/images/bullet_U.gif")),
            tk.getImage(Bullet.class.getResource("/images/bullet_D.gif")),
            tk.getImage(Bullet.class.getResource("/images/bullet_L.gif")),
            tk.getImage(Bullet.class.getResource("/images/bullet_R.gif")),
        };
        // 加入Map容器
        bulletimgs1.put(0,bulletImage1[0]);
        bulletimgs1.put(1,bulletImage1[1]);
        bulletimgs1.put(2,bulletImage1[2]);
        bulletimgs1.put(3,bulletImage1[3]);
    }

    private static Image[] bulletImage2 = null;
    private static Map<Integer, Image> bulletimgs2 = new HashMap<Integer, Image>(); // 定义Map键值对，是不同方向对应不同的弹头

    static {
        bulletImage2 = new Image[]{
                tk.getImage(Bullet.class.getResource("/images/bullet.png")),
                tk.getImage(Bullet.class.getResource("/images/bullet.png")),
                tk.getImage(Bullet.class.getResource("/images/bullet.png")),
                tk.getImage(Bullet.class.getResource("/images/bullet.png")),
        };
        // 加入Map容器
        bulletimgs2.put(0,bulletImage2[0]);
        bulletimgs2.put(1,bulletImage2[1]);
        bulletimgs2.put(2,bulletImage2[2]);
        bulletimgs2.put(3,bulletImage2[3]);
    }



    /**
     * drawBullet (子弹坐标x,y,画笔g,方向，子弹类型) 方法介绍：
     */
    public void drawMyBullet(int x, int y, Graphics g, Direction direction) {
        switch (direction) {
            case U:
                // 向上
                g.drawImage(bulletimgs1.get(0), x, y, null);
                break;
            case D:
                // 向下w
                g.drawImage(bulletimgs1.get(1), x, y, null);
                break;
            case L:
                // 向左
                g.drawImage(bulletimgs1.get(2), x, y, null);
                break;
            case R:
                // 向右
                g.drawImage(bulletimgs1.get(3), x, y, null);
                break;
            default:
                break;
        }
        move();
    }

    public void drawEnBullet(int x, int y, Graphics g, Direction direction) {
        switch (direction) {
            case U:
                // 向上
                g.drawImage(bulletimgs2.get(0), x, y,10,10, null);
                break;
            case D:
                // 向下w
                g.drawImage(bulletimgs2.get(1), x, y,10,10, null);
                break;
            case L:
                // 向左
                g.drawImage(bulletimgs2.get(2), x, y,10,10, null);
                break;
            case R:
                // 向右
                g.drawImage(bulletimgs2.get(3), x, y,10,10, null);
                break;
            default:
                break;
        }
        move();
    }

    public void move(){
        switch(direction){
            case U:
                y -= speed;
                break;
            case D:
                y += speed;
                break;
            case L:
                x -= speed;
                break;
            case R:
                x += speed;
                break;
            default:
                break;
        }
        //子弹何时死亡
        if(x<0||x> MyPanel.PanelWidth- Bullet.width||y<0||y> MyPanel.PanelHeight- Bullet.height){
            this.isLive = false;
        }
    }
    //攻击
    //1 攻击坦克
    public void bulletTank(Tank tank){
        if (this.getRect().intersects(tank.getRect())) {
            if(tank.life >= 20) {
                // 坦克死亡
                tank.lifeDown();
                // 击中，子弹死亡
                this.isLive = false;
            }else{
                // 坦克死亡
                tank.isLive = false;
                // 击中，子弹死亡
                this.isLive = false;
            }
        }
    }

    //2 攻击金属墙
    public void hitMetalWall(MetalWall mw){
        if (this.getRect().intersects(mw.getRect())) {
            mw.isLive = true;
            this.isLive = false;
        }
    }

    //3 攻击普通墙
    public void hitCommonWall(CommonWall cw){
        if(this.getRect().intersects(cw.getRect())){
            cw.isLive = false;
            this.isLive = false;
        }
    }

    //4 攻击家
    public void hitHome(Home home){
        if(this.getRect().intersects(home.getRect())){
            this.isLive = false;
            home.isLive = false;
        }
    }

    //碰撞检测
    //1子弹与子弹
    public boolean collideBullet(Bullet bullet){
        if(this != bullet){
            if(this.isLive&&this.getRect().intersects(bullet.getRect())){
                bullet.isLive = false;
                this.isLive = false;
                return true;
            }
        }
        return false;
    }
    //2子弹与导弹
    public boolean collideMissile(Missile missile){
        if(this.isLive&&this.getRect().intersects(missile.getRect())){
            missile.isLive = false;
            this.isLive = false;
            return true;
        }
        return false;
    }

    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }
}
