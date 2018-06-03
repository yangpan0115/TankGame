package com.yp.tank03;

import javax.swing.*;

public class MyTankGame3 extends JFrame {
    MyPanel p1 = null;

    public MyTankGame3() {
        p1 = new MyPanel();
        Thread t = new Thread(p1);
        t.start();
        add(p1);
        setSize(1366, 768);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addKeyListener(p1);// 监听

    }

    public static void main(String[] args) {
        new MyTankGame3();
    }
}
