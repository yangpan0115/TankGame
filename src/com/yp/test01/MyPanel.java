package com.yp.test01;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel{
    Image ImageBuffer = null;
    Graphics GraImage = null;

    public void update(Graphics g){     //覆盖update方法，截取默认的调用过程
        ImageBuffer = createImage(100, 60);   //创建图形缓冲区
        GraImage = ImageBuffer.getGraphics();       //获取图形缓冲区的图形上下文
        paint(GraImage);        //用paint方法中编写的绘图过程对图形缓冲区绘图
        GraImage.dispose();     //释放图形上下文资源
        g.drawImage(ImageBuffer, 0, 0, this);   //将图形缓冲区绘制到屏幕上
    }

    public void paint(Graphics g){      //在paint方法中实现绘图过程
        g.drawLine(200, 200, 200, 300);
        repaint();
    }
}
