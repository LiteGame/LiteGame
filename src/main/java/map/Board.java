package map;

import items.TempBall;
import items.Flippers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private TempBall tempBall;
    private Flippers flippers;
    private boolean ingame;
    private final int bal_start_x = 570;
    private final int bal_start_y = 520;
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int speed = 16;

    public Board() {

        initBoard();

    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        tempBall = new TempBall(bal_start_x, bal_start_y);
        flippers = new Flippers(0,0);

        timer = new Timer(speed, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {

            drawObjects(g);

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {

        if (tempBall.isVisible()) {
            g.drawImage(tempBall.getImage(), tempBall.getX(), tempBall.getY(),
                    this);
        }

        g.drawRect(200, 50, 350, 450);
        g.drawRect(550, 500, 50, 50);
        g.drawLine(600, 100, 600, 500);
        g.drawLine(550, 50, 600, 100);
        g.setColor(Color.WHITE);
    }

    private void drawGameOver(Graphics g) {

        String msg = "Try again";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateBall();
        updateFlippers();

        checkCollisions();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updateBall() {

        if (tempBall.isVisible()) {
            tempBall.move();
            System.out.println(tempBall.getBounds());
        }

    }

    private void updateFlippers() {

        if (flippers.isVisible()) {
            flippers.move();
        }

    }


    public void checkCollisions() {

        Rectangle ballBounds = tempBall.getBounds();
        Rectangle map = new Rectangle(200, 50, 350, 500);
        Rectangle launch_area = new Rectangle(550, 500, 50, 50);

        Rectangle2D ballBounds2D = tempBall.getBounds();
        Line2D start_line = new Line2D.Float(550, 50, 600, 100);


        if(ballBounds.intersects(launch_area)) {
            tempBall.set_in_start_position(true);
            tempBall.set_state("Launch");
        }
        else {
            tempBall.set_in_start_position(false);
        }

        if(start_line.intersects(ballBounds2D)) {
            tempBall.bounce(90);
        }

        if(ballBounds.intersects(map)) {
            tempBall.gravity(true);
            tempBall.set_state("Game");

        }

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            tempBall.keyPressed(e);
        }
    }
}