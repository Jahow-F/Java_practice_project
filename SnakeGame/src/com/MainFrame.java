package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import static java.awt.event.KeyEvent.VK_UP;

public class MainFrame extends JFrame {

    private Snake snake;//蛇
    private JPanel jPanel;//游戏棋盘
    private Timer timer;//计时器，在规定的时间内调用蛇移动的方法
    private Node food;//食物

    public MainFrame() throws HeadlessException {
        //初始化窗体参数
        initFrame();
        //初始化游戏棋盘
        initGamepanel();
        //初始化蛇
        initSnake();
        //初始化计时器
        initTimer();
        //设置键盘监听,让蛇上下左右方向移动
        setKeylistener();
        //初始化食物
        initFood();
    }

    //初始化食物
    private void initFood() {
        food = new Node();
        food.random();
    }

    private void setKeylistener() {
        addKeyListener(new KeyAdapter() {
            // 当键盘按下时，会自动调用此方法
            @Override
            public void keyPressed(KeyEvent e) {
                //键盘中每一个键都有一个编号
                switch (e.getKeyCode()){
                    //修改蛇的运动方向
                    case KeyEvent.VK_UP://上键
                        if(snake.getDirection() != Direction.DOWN){
                            snake.setDirection(Direction.UP);
                        }
                        break ;
                    case KeyEvent.VK_DOWN://下键
                        if(snake.getDirection() != Direction.UP){
                            snake.setDirection(Direction.DOWN);
                        }
                        break ;
                    case KeyEvent.VK_LEFT://左键
                        if(snake.getDirection() != Direction.RIGHT){
                            snake.setDirection(Direction.LEFT);
                        }
                        break ;
                    case KeyEvent.VK_RIGHT://右键
                        if(snake.getDirection() != Direction.LEFT){
                            snake.setDirection(Direction.RIGHT);
                        }
                        break ;
                }
            }
        });
    }

    //初始化计时器
    private void initTimer() {
        //创建计时器
        timer = new Timer();
        //初始化计时任务
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                snake.move();
                //判断蛇头是否与食物重合
                Node head = snake.getBody().getFirst();
                if(head.getX()==food.getX() && head.getY()==food.getY()){
                    snake.eat(food);
                    food.random();
                }
                //重绘游戏棋盘
                jPanel.repaint();
            }
        };

        //每一百毫秒，执行 一次定时任务
        timer.scheduleAtFixedRate(timerTask,0,100);
    }

    private void initSnake() {
        snake = new Snake();
    }

    private void initGamepanel() {
        jPanel = new JPanel(){

            //绘制游戏棋盘中的内容
            @Override
            public void paint(Graphics g) {
                //清空棋盘
                g.clearRect(0,0,600,600);

                //Graphics g 可以看作是一个画笔，它提供了很多方法可以绘制一些基本的图形（直线、矩形）
                //绘制48条横线
                for (int i = 0; i <= 48; i++) {
                    g.drawLine(0,i*15,600,i*15);
                }

                //绘制48条竖线
                for (int i = 0; i <= 48; i++) {
                    g.drawLine(i*15,0,i*15,600);
                }

                //绘制蛇
                LinkedList<Node> body = snake.getBody();
                for (Node node : body) {
                    g.fillRect(node.getX()*15,node.getY()*15,15,15);
                    //前面两个 node.getX()*15,node.getY()*15 是绘制在棋盘的某个位置的横纵坐标，后面两个15表示体型的长和宽
                }

                //绘制食物
                g.fillRect(food.getX()*15,food.getY()*15,15,15);
            }
        };

        //把棋盘添加到窗体中
        add(jPanel);
    }

    //初始化窗体参数
    private void initFrame() {
        //设置窗体的宽和高
        setSize(610,640);
        //设置窗体的位置
        setLocationRelativeTo(null);
        //设置关闭按钮
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗体大小不可变
        setResizable(false);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("贪吃蛇小游戏");
        //创建窗体对象，并显示
        new MainFrame().setVisible(true);

    }
}
