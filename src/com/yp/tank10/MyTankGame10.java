package com.yp.tank10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyTankGame10 extends JFrame implements ActionListener{
    public static void main(String[] args) {
        new MyTankGame10();
    }
    MyPanel myPanel = null;
    public MyTankGame10(){
        myPanel = new MyPanel();
        add(myPanel);
        Thread t = new Thread(myPanel);
        t.start();
        this.setTitle("坦克大战1.0");
        this.setLocation(100,10);
        this.setSize(1100,650);//窗口大小
        this.setResizable(false);//大小固定
        this.setVisible(true);//是否可视化
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(myPanel);//注册监听

        //菜单栏
        JMenuBar jmb = new JMenuBar();
        JMenu jm1 = new JMenu("游戏");
        JMenu jm2 = new JMenu("帮助文档");
        JMenu jm3 = new JMenu("游戏级别");
        jm1.setFont(new Font("TankGame", Font.BOLD, 15));
        jm2.setFont(new Font("TankGame", Font.BOLD, 15));
        jm3.setFont(new Font("TankGame", Font.BOLD, 15));

        JMenuItem jmi1 = new JMenuItem("开始新的游戏");
        JMenuItem jmi2 = new JMenuItem("退出");
        JMenuItem jmi3 = new JMenuItem("游戏说明");
        JMenuItem jmi4 = new JMenuItem("级别1");
        JMenuItem jmi5 = new JMenuItem("级别2");
        JMenuItem jmi6 = new JMenuItem("级别3");

        jmi1.setFont(new Font("TankGame", Font.BOLD, 15));
        jmi2.setFont(new Font("TankGame", Font.BOLD, 15));
        jmi3.setFont(new Font("TankGame", Font.BOLD, 15));
        jmi4.setFont(new Font("TankGame", Font.BOLD, 15));
        jmi5.setFont(new Font("TankGame", Font.BOLD, 15));
        jmi6.setFont(new Font("TankGame", Font.BOLD, 15));

        jm1.add(jmi1);
        jm1.add(jmi2);
        jm2.add(jmi3);
        jm3.add(jmi4);
        jm3.add(jmi5);
        jm3.add(jmi6);

        jmb.add(jm1);
        jmb.add(jm2);
        jmb.add(jm3);


        jmi1.addActionListener(this);
        jmi1.setActionCommand("NewGame");
        jmi2.addActionListener(this);
        jmi2.setActionCommand("Exit");

        jmi3.addActionListener(this);
        jmi3.setActionCommand("Help");

        jmi4.addActionListener(this);
        jmi4.setActionCommand("Level1");
        jmi5.addActionListener(this);
        jmi5.setActionCommand("Level2");
        jmi6.addActionListener(this);
        jmi6.setActionCommand("Level3");

        //MenuBar放到JFrame上
        setJMenuBar(jmb);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("NewGame")){
            this.dispose();
            new MyTankGame10();
        }else if(e.getActionCommand().equals("Exit")){
            System.exit(0);
        }else if (e.getActionCommand().equals("Help")) {
            JOptionPane.showMessageDialog(null,
                    "用→ ← ↑ ↓控制方向，空格键发射！" +
                            "在任意level输了将继续本level！",
                    "提示！", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(true);
        }else if(e.getActionCommand().equals("Level1")){
            this.dispose();
            myPanel.ensize = 3;
            new MyTankGame10();
        }else if(e.getActionCommand().equals("Level2")){
            this.dispose();
            myPanel.ensize = 4;
            new MyTankGame10();
        }else if(e.getActionCommand().equals("Level3")){
            this.dispose();
            myPanel.ensize = 5;
            new MyTankGame10();
        }
    }
}
