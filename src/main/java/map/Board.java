package map;

import items.BallSprite;
import items.Flipper_Left;
import items.Flipper_Right;
import nl.tu.delft.defpro.api.IDefProAPI;
import physics.Ball;
import physics.Collisions;
import physics.Environment;
import physics.Vec2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Flipper_Right flipperRight;
    private Flipper_Left flipperLeft;
    private boolean ingame;

    private Set<Line2D> lines = new HashSet<>();
    private Set<Ellipse2D> ellipses = new HashSet<>();
    private Set<Arc2D> arcs = new HashSet<>();

    private BallSprite ballSprite;
    private Environment physicsEnvironment;
    private Collisions collisions;

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

        //side shutters
        lines.add(new Line2D.Double(295,600,295,670));
        lines.add(new Line2D.Double(295,670,340,720));
        lines.add(new Line2D.Double(555,600,555,670));
        lines.add(new Line2D.Double(555,670,510,720));

        //side shapes
        lines.add(new Line2D.Double(310.5,527,250,200));
        arcs.add(new Arc2D.Double(250, 560, 60, 100, 90, 90, Arc2D.OPEN));
        arcs.add(new Arc2D.Double(250, 500, 60, 60, -90, 100, Arc2D.OPEN));
        lines.add(new Line2D.Double(539.5,527,600,200));
        arcs.add(new Arc2D.Double(540, 560, 60, 100, 90, -90, Arc2D.OPEN));
        arcs.add(new Arc2D.Double(540, 500, 60, 60, -90, -100, Arc2D.OPEN));

        //top middle arcs
        arcs.add(new Arc2D.Double(325, 61.5, 100, 112, 105, -105, Arc2D.OPEN));
        arcs.add(new Arc2D.Double(425, 53.5, 100, 120, 75, 105, Arc2D.OPEN));

        //left shutter top
        arcs.add(new Arc2D.Double(275, 120, 160, 150, 150, 40, Arc2D.OPEN));
        arcs.add(new Arc2D.Double(280, 110, 160, 150, 90, 70, Arc2D.OPEN));
        arcs.add(new Arc2D.Double(320, 110, 80, 80, 90, -70, Arc2D.OPEN));

        //right shutter top
        arcs.add(new Arc2D.Double(400, 100, 200, 200, 0, 90, Arc2D.OPEN));
        arcs.add(new Arc2D.Double(460, 100, 80, 80, 90, 85, Arc2D.OPEN));

        //Bumper left side
        lines.add(new Line2D.Double(325,625,325,665));
        lines.add(new Line2D.Double(344.3969,622,360,680));
        lines.add(new Line2D.Double(325,665,347.5,687.5));
        arcs.add(new Arc2D.Double(325, 615, 20, 20, 20, 160, Arc2D.OPEN));
        arcs.add(new Arc2D.Double(345, 675, 15, 15, 35, -180, Arc2D.OPEN));

        //Bumper right side
        lines.add(new Line2D.Double(525,625,525,665));
        lines.add(new Line2D.Double(505.6031,622,490,680));
        lines.add(new Line2D.Double(525,665,502.5,687.5));
        arcs.add(new Arc2D.Double(505, 615, 20, 20, 160, -160, Arc2D.OPEN));
        arcs.add(new Arc2D.Double(490, 675, 15, 15, 145, 180, Arc2D.OPEN));

        //bumpers middle
        ellipses.add(new Ellipse2D.Double(420,220,50,50));
        ellipses.add(new Ellipse2D.Double(370,170,40,40));
        ellipses.add(new Ellipse2D.Double(340,260,60,60));
        ellipses.add(new Ellipse2D.Double(475,150,80,80));


        ellipses.add(new Ellipse2D.Double(600,200,0.1,0.1));
        lines.add(new Line2D.Double(250,775,625,775));
        lines.add(new Line2D.Double(250, 775, 250, 200));
        lines.add(new Line2D.Double(600, 775, 600, 200));
        lines.add(new Line2D.Double(625, 200, 625, 775));
        arcs.add(new Arc2D.Double(250, 50, 375, 300, 0, 180, Arc2D.OPEN));

        //arcs.add(new Arc2D.Double(250, 50, 300, 300, 180, 90, Arc2D.OPEN));
        //arcs.add(new Arc2D.Double(225, 50, 350, 300, 0, 180, Arc2D.OPEN));

        flipperRight = new Flipper_Right(new Vec2d(550,400), -195);
        flipperLeft = new Flipper_Left(new Vec2d(200, 400), 15);
        ballSprite = new BallSprite(new Ball(physicsEnvironment, new Vec2d(balStartX,balStartY), 10.0,5.0));
        ballSprite.loadImage("resources/dot.png");

        ballSprite.setVisible(true);
        physicsEnvironment.spawnObject(ballSprite.getBall());
        physicsEnvironment.setGravity(0.0);
        //flipperRight = new Flipper_Right(550,400, -195);
        //flipperLeft = new Flipper_Left(200, 400, 15);

        timer = new Timer(10, this);
        timer.start();
    }

    public Set getLines() {
        return lines;
    }

    public Set getEllipses() {
        return ellipses;
    }

    public Set getArcs() {
        return arcs;
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
        g.setColor(Color.WHITE);
        AffineTransform identity_left = new AffineTransform();
        AffineTransform trans_left = new AffineTransform();

        AffineTransform identity_right = new AffineTransform();
        AffineTransform trans_right = new AffineTransform();


        if (ballSprite.isVisible()) {
            g.drawImage(ballSprite.getImage(), (int) ballSprite.getPosition().x, (int) ballSprite.getPosition().y, this);
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

        Iterator<Line2D> iteratorlines = lines.iterator();
        while(iteratorlines.hasNext()) {
            g.draw(iteratorlines.next());
        }

        Iterator<Ellipse2D> iteratorellipses = ellipses.iterator();
        while(iteratorellipses.hasNext()) {
            g.draw(iteratorellipses.next());
        }

        Iterator<Arc2D> iteratorarcs = arcs.iterator();
        while(iteratorarcs.hasNext()) {
            g.draw(iteratorarcs.next());
        }

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
        collisions.tick(ballSprite.getBall(), lines, ellipses, arcs);
        //updateFlippers();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                //LeftPressed = true;
                ballSprite.getBall().applyForce(new Vec2d(-500.0,0.0));
            }
            if (key == KeyEvent.VK_RIGHT) {
                ballSprite.getBall().applyForce(new Vec2d(500.0,0.0));
            }
            if (key == KeyEvent.VK_UP) {
                ballSprite.getBall().applyForce(new Vec2d(0.0,-500.0));
            }
            if (key == KeyEvent.VK_DOWN) {
                ballSprite.getBall().applyForce(new Vec2d(0.0,500.0));
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