package map;

import items.Flipper_Left;
import items.TempBall;
import items.Flipper_Right;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private TempBall tempBall;
    private Flipper_Right flipperRight;
    private Flipper_Left flipperLeft;
    private boolean ingame;
    private final int bal_start_x = 570;
    private final int bal_start_y = 520;
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int speed = 8;

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
        flipperRight = new Flipper_Right(550,500, -195);
        flipperLeft = new Flipper_Left(200, 500, 15);

        timer = new Timer(speed, this);
        timer.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        paintComponent((Graphics2D) g);
    }

    public void paintComponent(Graphics2D g) {
        super.paintComponent(g);

        if (ingame) {

            drawObjects(g);

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g) {
        AffineTransform identity_left = new AffineTransform();
        AffineTransform trans_left = new AffineTransform();

        AffineTransform identity_right = new AffineTransform();
        AffineTransform trans_right = new AffineTransform();


        if (tempBall.isVisible()) {
            g.drawImage(tempBall.getImage(), (int)tempBall.getX(), (int)tempBall.getY(), this);
        }

        if (flipperRight.isVisible()) {
            identity_right.translate(flipperRight.getX(), flipperRight.getY());
            trans_right.setTransform(identity_right);
            trans_right.rotate( Math.toRadians(flipperRight.getAngle()) );
            trans_right.getScaleInstance(1, -1);
            trans_right.translate(0, -flipperRight.getImage().getHeight(null));
            g.drawImage(flipperRight.getImage(), trans_right, this);
        }

        if (flipperLeft.isVisible()) {
            identity_left.translate(flipperLeft.getX(), flipperLeft.getY());
            trans_left.setTransform(identity_left);
            trans_left.rotate( Math.toRadians(flipperLeft.getAngle()) );
            g.drawImage(flipperLeft.getImage(), trans_left, this);
        }

        g.drawRect(200, 50, 350, 450);
        g.drawRect(550, 500, 50, 50);
        g.drawLine(600, 100, 600, 500);
        g.drawLine(550, 50, 600, 100);

        g.setColor(Color.WHITE);
    }

    private void drawGameOver(Graphics2D g) {

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
            //System.out.println(tempBall.getBounds());
            //System.out.println(tempBall.getMoveAngle());
            //System.out.println(tempBall.getSpeed());
        }

    }

    private void updateFlippers() {

        if (flipperRight.isVisible()) {
            flipperRight.move();
        }
        if (flipperLeft.isVisible()) {
            flipperLeft.move();
        }

    }


    public void checkCollisions() {

        Rectangle2D ballBounds = tempBall.getBounds();
        Rectangle2D map = new Rectangle(200, 50, 350, 500);
        Rectangle2D launch_area = new Rectangle(550, 500, 50, 50);

        Line2D left_wall = new Line2D.Float(201, 50, 201, 500);
        Line2D right_wall = new Line2D.Float(549, 50, 549, 500);
        Line2D bottom_wall = new Line2D.Float(201, 499, 549, 499);
        Line2D top_wall = new Line2D.Float(201, 51, 549, 51);


        Rectangle2D ballBounds2D = tempBall.getBounds();
        Line2D start_line = new Line2D.Float(550, 50, 600, 100);

        if(left_wall.intersects(ballBounds2D)){
            tempBall.bounce("Left wall");
        }
        else if(right_wall.intersects(ballBounds2D)){
            tempBall.bounce("Right wall");
        }
        else if(bottom_wall.intersects(ballBounds2D)){
            tempBall.bounce("Bottom wall");
        }
        else if(top_wall.intersects(ballBounds2D)){
            tempBall.bounce("Top wall");
        }


        if(ballBounds.intersects(launch_area)) {
            tempBall.set_in_start_position(true);
            tempBall.set_state("Launch");
        }
        else {
            tempBall.set_in_start_position(false);
        }

        if(start_line.intersects(ballBounds2D)) {
            tempBall.bounce("90");
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
            flipperLeft.keyPressed(e);
            flipperRight.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            flipperRight.keyReleased(e);
            flipperLeft.keyReleased(e);
        }
    }
}