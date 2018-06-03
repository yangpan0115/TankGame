package com.yp.tank07;

import java.awt.*;

public class CommonWall extends Wall{
    public CommonWall(int x,int y){
        super(x,y);
    }

    public CommonWall(){}

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] commonWallImage = null;

    //存储图片
    static{
        commonWallImage = new Image[]{
                tk.getImage(Blood.class.getResource("/images/commonWall.jpg")),
        };
    }

    //画出墙
    public void drawCommonWall(Graphics g){
        g.drawImage(commonWallImage[0],x,y,null);
    }
}
