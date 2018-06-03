package com.yp.test01;

//建立主窗口
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.*;

public class test extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new test();
    }
    JLabel mainLabel;
    JButton min, close;// 最小化，关闭按钮

    public test() {
        // 定义构造方法来初始化窗口
        ImageIcon bg=new ImageIcon("images/bg.png");
        setUndecorated(true);
        mainLabel = new JLabel(bg);
        setBounds(100, 100, bg.getIconWidth(), bg.getIconHeight());


        // 设置窗体可以跟随鼠标移动和透明
        //LocationUtil util = new LocationUtil(this);
        //util.setOp();

        min = new JButton(new ImageIcon("images/min.png"));
        min.setBounds(740, 13, 32, 32);
        min.setBorderPainted(false);// 去掉按钮的边框
        min.setContentAreaFilled(false); // 去掉按钮的填充区域
        mainLabel.add(min);
        close = new JButton(new ImageIcon("images/close.png"));
        close.setBounds(780, 13, 32, 32);
        close.setBorderPainted(false);
        close.setContentAreaFilled(false);
        mainLabel.add(close);

        add(mainLabel);
        setVisible(true);
        min.addActionListener(this);
        close.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

