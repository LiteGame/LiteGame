package map;

import items.BallSprite;
import items.Flipper_Left;
import items.Flipper_Right;
import nl.tu.delft.defpro.api.IDefProAPI;
import physics.Ball;
import physics.Environment;
import physics.Vec2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Flipper_Right flipperRight;
    private Flipper_Left flipperLeft;
    private boolean ingame;

    private BallSprite ballSprite;
    private Environment physicsEnvironment;

    private int balStartX;
    private int balStartY;
    private int boardWidth;
    private int boardHeight;



    public Board(IDefProAPI config) {

        initConfig(config);
        initBoard();

    }


    private void initConfig(IDefProAPI config) {
        try{
            balStartX = config.getIntegerValueOf("balStartX");
            balStartY = config.getIntegerValueOf("balStartY");
            boardWidth = config.getIntegerValueOf("boardWidth");
            boardHeight = config.getIntegerValueOf("boardHeight");
        } catch (Exception e){
            System.out.println("There was an error loading the config, loading default values:" + e);
            // Default in case config file fails.
            balStartX = 570;
            balStartY = 520;
            boardWidth = 800;
            boardHeight = 600;
        }
    }


    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;

        setPreferredSize(new Dimension(boardWidth, boardHeight));

        physicsEnvironment = new Environment();

        ballSprite = new BallSprite(new Ball(physicsEnvironment, new Vec2d(balStartX,balStartY), 10.0));
        ballSprite.loadImage("resources/dot.png");

        physicsEnvironment.spawnProp(ballSprite.getBall());
        physicsEnvironment.setGravity(0.0);
        //flipperRight = new Flipper_Right(550,400, -195);
        //flipperLeft = new Flipper_Left(200, 400, 15);

        timer = new Timer(10, this);
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


        if (ballSprite.isVisible()) {
            g.drawImage(ballSprite.getImage(), (int) ballSprite.getX(), (int) ballSprite.getY(), this);
        }

//        if (flipperRight.isVisible()) {
//            identity_right.translate(flipperRight.getX(), flipperRight.getY());
//            trans_right.setTransform(identity_right);
//            trans_right.rotate( Math.toRadians(flipperRight.getAngle()) );
//            trans_right.getScaleInstance(1, -1);
//            trans_right.translate(0, -flipperRight.getImage().getHeight(null));
//            g.drawImage(flipperRight.getImage(), trans_right, this);
//        }
//
//        if (flipperLeft.isVisible()) {
//            identity_left.translate(flipperLeft.getX(), flipperLeft.getY());
//            trans_left.setTransform(identity_left);
//            trans_left.rotate( Math.toRadians(flipperLeft.getAngle()) );
//            g.drawImage(flipperLeft.getImage(), trans_left, this);
//        }

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
        g.drawString(msg, (boardWidth - fm.stringWidth(msg)) / 2,
                boardHeight / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        physicsEnvironment.tick();
        //updateFlippers();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updateBall() {

        if (ballSprite.isVisible()) {
            //System.out.println(ballSprite.getBounds());
            //System.out.println(ballSprite.getMoveAngle());
            //System.out.println(ballSprite.getSpeed());
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

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                //LeftPressed = true;
                physicsEnvironment.applyForceTime(ballSprite.getBall(),new Vec2d(-100.0,0.0),100);
            }
            if (key == KeyEvent.VK_RIGHT) {
                physicsEnvironment.applyForceTime(ballSprite.getBall(),new Vec2d(100.0,0.0),100);
            }
            if (key == KeyEvent.VK_UP) {
                physicsEnvironment.applyForceTime(ballSprite.getBall(),new Vec2d(0.0,-100.0),100);
            }
            if (key == KeyEvent.VK_DOWN) {
                physicsEnvironment.applyForceTime(ballSprite.getBall(),new Vec2d(0.0,100.0),100);
            }
            //flipperLeft.keyPressed(e);
            //flipperRight.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                //LeftPressed = false;
            }
            if (key == KeyEvent.VK_RIGHT) {
                //RightPressed = false;
            }
            //flipperRight.keyReleased(e);
            //flipperLeft.keyReleased(e);
        }
    }
}