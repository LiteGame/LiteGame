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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.nio.file.Path;

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
    private Boolean LeftPressed = false;
    private  Boolean RightPressed = false;


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
        flipperRight = new Flipper_Right(550,400, -195);
        flipperLeft = new Flipper_Left(200, 400, 15);

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

        Path2D triangle = new Path2D.Float();
        triangle.moveTo(550, 50);
        triangle.lineTo(600, 100);
        triangle.lineTo(600, 50);
        triangle.closePath();
        g.draw(triangle);

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

        Rectangle2D map = new Rectangle(200, 50, 350, 500);
        Rectangle2D launch_area = new Rectangle(550, 500, 50, 50);
        Rectangle2D ball_hack = new Rectangle2D.Float(tempBall.getX(), tempBall.getY(), tempBall.getImage().getHeight(null), tempBall.getImage().getWidth(null));

        Rectangle2D left_wall = new Rectangle2D.Float(200, 50, 2, 450);
        Rectangle2D right_wall = new Rectangle2D.Float(548, 50, 2, 450);
        Rectangle2D bottom_wall = new Rectangle2D.Float(201, 498, 348, 2);
        Rectangle2D top_wall = new Rectangle2D.Float(202, 50, 348, 2);

        Ellipse2D ballBounds = tempBall.getBounds();

        Path2D triangle = new Path2D.Float();
        triangle.moveTo(550, 50);
        triangle.lineTo(600, 100);
        triangle.lineTo(600, 50);
        triangle.closePath();

        /**
         * Left flipper collision magic.
         */
        Path2D FlipperLeft = new Path2D.Float();
        FlipperLeft.moveTo(flipperLeft.getX(), flipperLeft.getY());
        FlipperLeft.lineTo(318, 373);
        FlipperLeft.lineTo(318, 427);
        FlipperLeft.closePath();

        Path2D InnerFlipperLeft = new Path2D.Float();
        InnerFlipperLeft.moveTo(flipperLeft.getX() + 5, flipperLeft.getY());
        InnerFlipperLeft.lineTo(313, 378);
        InnerFlipperLeft.lineTo(313, 422);
        InnerFlipperLeft.closePath();

        /**
         * Right flipper collision magic.
         */

        Path2D FlipperRight = new Path2D.Float();
        FlipperRight.moveTo(flipperRight.getX(), flipperRight.getY());
        FlipperRight.lineTo(435, 373);
        FlipperRight.lineTo(435, 427);
        FlipperRight.closePath();

        Path2D InnerFlipperRight = new Path2D.Float();
        InnerFlipperRight.moveTo(flipperRight.getX() + 5, flipperRight.getY());
        InnerFlipperRight.lineTo(435, 378);
        InnerFlipperRight.lineTo(435, 422);
        InnerFlipperRight.closePath();

        if(FlipperLeft.intersects(ball_hack) && InnerFlipperLeft.intersects(ball_hack) && LeftPressed){
            tempBall.bounce("UP");
        }
        else if(FlipperLeft.intersects(ball_hack) && LeftPressed){
            tempBall.bounce("Bottom wall");
        }

        if(FlipperRight.intersects(ball_hack) && InnerFlipperRight.intersects(ball_hack) && RightPressed){
            tempBall.bounce("UP");
        }
        else if(FlipperRight.intersects(ball_hack) && RightPressed){
            tempBall.bounce("Bottom wall");
        }

        if(ballBounds.intersects(left_wall)){
            tempBall.bounce("Left wall");
        }
        else if(ballBounds.intersects(right_wall)){
            tempBall.bounce("Right wall");
        }
        else if(ballBounds.intersects(bottom_wall)){
            tempBall.bounce("Bottom wall");
        }
        else if(ballBounds.intersects(top_wall)){
            tempBall.bounce("Top wall");
        }


        if(ballBounds.intersects(launch_area)) {
            tempBall.set_in_start_position(true);
            tempBall.set_state("Launch");
        }
        else {
            tempBall.set_in_start_position(false);
        }

        if(triangle.intersects(ball_hack)) {
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
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                LeftPressed = true;
            }
            if (key == KeyEvent.VK_RIGHT) {
                RightPressed = true;
            }
            tempBall.keyPressed(e);
            flipperLeft.keyPressed(e);
            flipperRight.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                LeftPressed = false;
            }
            if (key == KeyEvent.VK_RIGHT) {
                RightPressed = false;
            }
            flipperRight.keyReleased(e);
            flipperLeft.keyReleased(e);
        }
    }
}