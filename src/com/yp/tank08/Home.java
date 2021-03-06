package com.yp.tank08;

import java.awt.*;

public class Home {
    int x;
    int y;
    boolean isLive = true;
    public static final int width = 50;
    public static final int height = 50;

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] homeImage = null;

    //存储图片
    static{
        homeImage = new Image[]{
                tk.getImage(Blood.class.getResource("/images/home.png")),
        };
    }
    public Home(int x,int y){
        this.x = x;
        this.y = y;
    }

    public Home(){}

    //画出家
    public void drawHome(Graphics g){
        g.drawImage(homeImage[0],x,y,50,50,null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }
}
