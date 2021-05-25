package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    private final int board_width = 300;
    private final int board_height = 300;
    private final int dot_size = 10;
    private final int all_dots = 900;
    private final int random_position = 29;
    private final int delay = 140;

    private final int x[] = new int [all_dots];
    private final int y[] = new int [all_dots];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean left_direction = false;
    private boolean right_direction = true;
    private boolean upper_direction = false;
    private boolean down_direction = false;
    private boolean in_game = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public Board(){ initBoard(); }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(board_width, board_height));
        loadImages();
        initGame();
    }

    private void loadImages() {
        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        ball = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        ball = iih.getImage();
    }

    private void initGame() {
        dots = 3;

        for(int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        locateApple();

        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if(in_game) {
            g.drawImage(apple, apple_x, apple_y, this);

            for(int i = 0; i < dots; i++) {
                if(i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(ball, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over!";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics mtr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (board_width - mtr.stringWidth(msg)) / 2, board_height / 2);
    }

    private void checkApple() {
        if((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
        }
    }

    private void move() {
        for (int z = dots; z > 0; z++) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        if(left_direction) {
            x[0] -= dot_size;
        }
        if(right_direction) {
            x[0] += dot_size;
        }
        if(upper_direction) {
            y[0] -= dot_size;
        }
        if(down_direction) {
            y[0] += dot_size;
        }
    }

    private void checkCollision() {
        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                in_game = false;
            }
        }
        if (y[0] >= board_height) {
            in_game = false;
        }
        if (y[0] < 0) {
            in_game = false;
        }
        if (x[0] >= board_width) {
            in_game = false;
        }
        if (x[0] < 0) {
            in_game = false;
        }
        if (!in_game) {
            timer.stop();
        }
    }

    private void locateApple() {
        int r = (int) (Math.random() * random_position);
        apple_x = ((r * dot_size));

        r = (int) (Math.random() * random_position);
        apple_y = ((r * dot_size));
    }

    public class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if((key == KeyEvent.VK_LEFT) && (!right_direction)) {
                left_direction = true;
                upper_direction = false;
                down_direction = false;
            }
            if((key == KeyEvent.VK_RIGHT) && (!left_direction)) {
                right_direction = true;
                upper_direction = false;
                down_direction = false;
            }
            if((key == KeyEvent.VK_UP) && (!down_direction)) {
                upper_direction = true;
                right_direction = false;
                down_direction = false;
            }
            if((key == KeyEvent.VK_DOWN) && (!upper_direction)) {
                down_direction = true;
                right_direction = false;
                left_direction = false;
            }
        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
