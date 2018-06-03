package com.yp.tank01;

import javax.swing.*;

/**
 * @author LBJ
 * 功能：坦克游戏1.0
 * 1.画出坦克
 */
@SuppressWarnings("serial")//对被批注的代码元素内部的某些警告保持静默,即忽略这些警告信息
public class MyTankGame1 extends JFrame {

    MyPanel p1 = new MyPanel();

    public MyTankGame1(){
        add(p1);
        setSize(400,300);//JFrame大小：宽400，高300
        setVisible(true);//可视化
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new MyTankGame1();
    }
}

