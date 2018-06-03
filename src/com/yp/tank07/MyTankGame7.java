package com.yp.tank07;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyTankGame7 extends JFrame implements ActionListener{
    public static void main(String[] args) {
        new MyTankGame7();
    }
    MyPanel myPanel = null;
    public MyTankGame7(){
        myPanel = new MyPanel();
        add(myPanel);
        Thread t = new Thread(myPanel);
        t.start();
        this.setTitle("坦克大战1.0");
        this.setLocation(100,10);
        this.setSize(1315,660);//窗口大小1115
        this.setVisible(true);//是否可视化
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(myPanel);//注册监听
        this.getContentPane().setBackground(Color.GREEN);

        //菜单栏
        JMenuBar jmb = new JMenuBar();
        JMenu jm1 = new JMenu("游戏");
        JMenu jm2 = new JMenu("帮助文档");
        jm1.setFont(new Font("TankGame", Font.BOLD, 15));
        jm2.setFont(new Font("TankGame", Font.BOLD, 15));

        JMenuItem jmi1 = new JMenuItem("开始新的游戏");
        JMenuItem jmi2 = new JMenuItem("退出");
        JMenuItem jmi3 = new JMenuItem("游戏说明");
        jmi1.setFont(new Font("TankGame", Font.BOLD, 15));
        jmi2.setFont(new Font("TankGame", Font.BOLD, 15));
        jmi3.setFont(new Font("TankGame", Font.BOLD, 15));

        jm1.add(jmi1);
        jm1.add(jmi2);
        jm2.add(jmi3);

        jmb.add(jm1);
        jmb.add(jm2);


        jmi1.addActionListener(this);
        jmi1.setActionCommand("NewGame");
        jmi2.addActionListener(this);
        jmi2.setActionCommand("Exit");
        jmi3.addActionListener(this);
        jmi3.setActionCommand("Help");

        //MenuBar放到JFrame上
        this.setJMenuBar(jmb);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("NewGame")){
            new MyTankGame7();
        }else if(e.getActionCommand().equals("Exit")){
            System.exit(0);
        }else if (e.getActionCommand().equals("Help")) {
            JOptionPane.showMessageDialog(null, "用→ ← ↑ ↓控制方向，空格键盘发射！",
                    "提示！", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(true);

        }
    }
}
