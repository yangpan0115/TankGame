package com.yp.tank04;

import javax.swing.*;

public class MyTankGame4 extends JFrame {
    MyPanel p1 = null;
    public MyTankGame4(){
        p1 = new MyPanel();
        Thread t = new Thread(p1);
        t.start();
        add(p1);
        setSize(1366,768);//窗口大小
        setVisible(true);//是否可视化
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(p1);//注册监听
    }
    public static void main(String[] args) {
        new MyTankGame4();
    }
}
