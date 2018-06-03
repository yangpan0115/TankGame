package com.yp.tank05;

import javax.swing.*;

public class MyTankGame5 extends JFrame {
    MyPanel p1 = null;
    public MyTankGame5(){
        p1 = new MyPanel();
        Thread t = new Thread(p1);
        t.start();
        add(p1);
        setTitle("坦克大战");
        setLocation(25,25);
        setSize(1300,700);//窗口大小
        setVisible(true);//是否可视化
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(p1);//注册监听
    }
    public static void main(String[] args) {
        new MyTankGame5();
    }
}
