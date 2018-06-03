package com.yp.tank01;

import java.awt.*;
import javax.swing.JPanel;

//画图
@SuppressWarnings("serial")//对被批注的代码元素内部的某些警告保持静默,即忽略这些警告信息
public class MyPanel extends JPanel{
    MyTank mytank = null;

    public MyPanel(){
        mytank = new MyTank(10,10);//设置坦克出现的位置(10,10)
    }

    //窗口
    public void paintComponent(Graphics g){

        super.paintComponent(g);//调用父类（套路）函数 完成初始化任务

        g.fillRect(0, 0, 400, 300);//背景填充

        drawTank(mytank.getX(),mytank.getY(),g,0,1);//一定要传入画笔g
        g.setColor(Color.yellow);
    }


	/*
	* 面板：
	* drawTank (坦克坐标x,y,画笔g,方向，坦克类型)
	* 方法介绍：
	* 可以设置-->坦克的颜色（类型：敌方坦克，我方坦克），方向，出现的坐标
	*
	* 如果type是default 则默认颜色为画出黑色坦克
	*
	* 封装性：将坦克封装到方法中。
	*
	*/

    public void drawTank(int x,int y,Graphics g,int direct,int type){
        switch(type){
            case 0:
                g.setColor(Color.red);
                break;
            case 1:
                g.setColor(Color.green);
                break;
        }
        switch(direct){
            case 0:
                //向上
                g.fill3DRect(x   , y    , 5 , 30, false);//使坦克具有立体感
                g.fill3DRect(x+15, y    , 5 , 30, false);
                g.fill3DRect(x+5 , y+5  , 10, 20, false);
                g.fillOval(x+4, y+10, 10 , 10);
                g.drawLine(x+9, y+15, x+9, y );
                break;

            default:
                break;
        }
    }
}
