package com.yp.tank06;

import javax.swing.*;

public class MyTankGame6 extends JFrame {
    MyPanel p1 = null;
    public MyTankGame6(){
        p1 = new MyPanel();
        Thread t = new Thread(p1);
        t.start();
        add(p1);
        setSize(1000,600);//窗口大小
        setVisible(true);//是否可视化
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(p1);//注册监听
    }
    public static void main(String[] args) {
        new MyTankGame6();
    }
}
