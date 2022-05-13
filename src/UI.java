

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;


public class UI extends JFrame {

    //按钮
    private final JButton randomGameBtn = new JButton("初始化细胞");
    private final JButton clearGameBtn = new JButton("清空");
    private final JButton nextGameBtn = new JButton("演化一次");
    private final JButton automaticBtn = new JButton("自动演化");
    private final JButton stopBtn = new JButton("结束");

    //文本
    private final JTextField durationText = new JTextField();
    private final JTextField rowsText = new JTextField();
    private final JTextField colsText = new JTextField();

    //结束标志
    private volatile boolean stop = false;

    //线程
    private Thread worker;

    //算法
//    private CellMatrix cellMatrix;
    private LifeGame lifeGame=new LifeGame();

    //UI
    private JPanel gridPanel = new JPanel();

    //显示的矩阵
    private JTextField[][] textMatrix;

    //间隔
    private static final int DEFAULT_DURATION = 300;
    //行数
    private static final int DEFAULT_ROWS = 94;
    //默认列数
    private static final int DEFAULT_COLS = 151;

    public UI() {
        setTitle("孙梦举 梁臣扬");
        // 初始化按钮
        initTextFiled();
        // 绑定按钮和监听器
        bindButtonListener();
        // 设置按钮面板
//        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
        buttonPanel.add(randomGameBtn);
        buttonPanel.add(nextGameBtn);
        buttonPanel.add(automaticBtn);
        buttonPanel.add(stopBtn);
        buttonPanel.add(clearGameBtn);

        JLabel durationLabel = new JLabel("动画间隔(ms)");
        durationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel colsLabel = new JLabel("列数(>8)");
        colsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel rowsLabel = new JLabel("行数(>8)");
        rowsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel textPanel = new JPanel(new GridLayout(3, 2));
        textPanel.add(rowsLabel);
        textPanel.add(rowsText);
        textPanel.add(colsLabel);
        textPanel.add(colsText);
        textPanel.add(durationLabel);
        textPanel.add(durationText);

//        getContentPane().add(BorderLayout.NORTH, buttonPanel);
//        getContentPane().add(BorderLayout.SOUTH, textPanel);
        getContentPane().add(BorderLayout.WEST, buttonPanel);
        getContentPane().add(BorderLayout.SOUTH, textPanel);

        this.setSize(2250, 1350);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


     //初始化文本框
    private void initTextFiled() {
        durationText.setText(String.valueOf(DEFAULT_DURATION));
        rowsText.setText(String.valueOf(DEFAULT_ROWS));
        colsText.setText(String.valueOf(DEFAULT_COLS));
    }

    //绑定按钮和监听事件

    int rows,cols;
    private void bindButtonListener() {
        randomGameBtn.addActionListener(e -> {
            try {
                rows = Integer.parseInt(rowsText.getText().trim());
                cols = Integer.parseInt(colsText.getText().trim());
                int duration = Integer.parseInt(durationText.getText().trim());
                if (rows < 8 || cols < 8 ) {
                    alertMessage("输入参数有误，请检查", "出错啦！");
                    return;
                }
                lifeGame.init(rows, cols,duration);
            } catch (NumberFormatException e1) {
                alertMessage("参数有误", "出错啦！");
                return;
            }
            initGridLayout();
            showMatrix();
            gridPanel.updateUI();
        });
        nextGameBtn.addActionListener(e -> {
            if (isRun()) return;
            if (!gameOfLife()) return;
            lifeGame.circle_one(lifeGame.game_map);
            showMatrix();
            gridPanel.updateUI();
        });
        clearGameBtn.addActionListener(e -> {
            if (isRun()) return;
            lifeGame.init(rows,cols);
            initGridLayout();
            gridPanel.updateUI();
        });
        automaticBtn.addActionListener(e -> {
            if (isRun()) return;
            worker = new Thread(() -> {
                while (!stop) {
                    if (!gameOfLife()) {
                        worker = null;
                        return;
                    }
                    lifeGame.circle_one(lifeGame.game_map);
                    showMatrix();
                    gridPanel.updateUI();
                    try {
                        TimeUnit.MILLISECONDS.sleep(lifeGame.sl);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                stop = false;
            });
            worker.start();
        });
        stopBtn.addActionListener(e -> {
            if (worker != null && worker.isAlive()) {
                stop = true;
                worker = null;
            }
        });
    }

    //判断游戏是否已经运行

    private boolean isRun() {
        if (worker != null && worker.isAlive()) {
            alertMessage("请先停止", "提示");
            return true;
        }
        return false;
    }

    //修改UI

    private void showMatrix() {
        int[][] matrix = lifeGame.game_map;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == 1) {
                    textMatrix[y][x].setBackground(Color.pink);
                } else {
                    textMatrix[y][x].setBackground(Color.white);
                }
                textMatrix[y][x].setEditable(false);
            }
        }
    }

    //创建显示的布局

    private void initGridLayout() {
        int rows = lifeGame.game_map.length;
        int cols = lifeGame.game_map[0].length;
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, cols));
        textMatrix = new JTextField[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                JTextField text = new JTextField();
                textMatrix[y][x] = text;
                gridPanel.add(text);
            }
        }
        add(BorderLayout.CENTER, gridPanel);
    }


    private void alertMessage(String msg, String title) {
        JOptionPane.showMessageDialog(gridPanel, msg, title, JOptionPane.WARNING_MESSAGE);
    }

    //细胞演化

    private boolean gameOfLife() {
        if (lifeGame == null || lifeGame.game_map == null) {
            alertMessage("未初始化", "提示");
            return false;
        }
        if (!lifeGame.gameOfLife()) {
            alertMessage("游戏结束", "提示");
            initGridLayout();
            gridPanel.updateUI();
            return false;
        }
        return true;
    }
}
