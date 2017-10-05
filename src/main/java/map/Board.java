package map;

import items.BallSprite;
import nl.tu.delft.defpro.api.IDefProAPI;
import physics.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.awt.geom.Ellipse2D;
import java.util.HashSet;
import java.util.Set;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Boolean inGame;

    private int flipperRightTick = 0;
    private int flipperLeftTick = 0;
    private Boolean leftPressed = false;
    private Boolean rightPressed = false;

    private Set<Line2D> lines = new HashSet<>();
    private Set<Ellipse2D> ellipses = new HashSet<>();
    private Set<Arc2D> arcs = new HashSet<>();
    private Set<Line2D> flippers = new HashSet<>();

    private BallSprite ballSprite;
    private Environment physicsEnvironment;
    private Collisions collisions;

    private int balStartX;
    private int balStartY;
    private int boardWidth;
    private int boardHeight;
    private int flipperSpeed;

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
            flipperSpeed = config.getIntegerValueOf("flipperSpeed");
        } catch (Exception e){
            System.out.println("There was an error loading the config, loading default values:" + e);
            // Default in case config file fails.
            balStartX = 570;
            balStartY = 520;
            boardWidth = 800;
            boardHeight = 600;
            flipperSpeed = 3;
        }
    }


    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        inGame = true;

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

        // Outside
        ellipses.add(new Ellipse2D.Double(550,200,0.1,0.1));
        lines.add(new Line2D.Double(250,750,625,750));
        lines.add(new Line2D.Double(250, 750, 250, 200));
        lines.add(new Line2D.Double(600, 750, 600, 200));
        lines.add(new Line2D.Double(625, 200, 625, 750));
        //bumpers middle
        ellipses.add(new Ellipse2D.Double(420,220,50,50));
        ellipses.add(new Ellipse2D.Double(370,170,40,40));
        ellipses.add(new Ellipse2D.Double(340,260,60,60));
        ellipses.add(new Ellipse2D.Double(475,150,80,80));

        arcs.add(new Arc2D.Double(250, 50, 375, 300, 0, 180, Arc2D.OPEN));

        ballSprite = new BallSprite(new Ball(physicsEnvironment, new Vec2d(balStartX,balStartY), 10.0, 5.0));
        ballSprite.loadImage("resources/dot.png");
        ballSprite.setVisible(true);
        physicsEnvironment.spawnObject(ballSprite.getBall());
        physicsEnvironment.setGravity(2.0);
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

    public Set getFlippers() {
        return flippers;
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintComponent((Graphics2D) g);
    }

    private void paintComponent(Graphics2D g) {
        super.paintComponent(g);

        if (inGame) {

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

        for (Line2D line : lines) {
            physicsEnvironment.spawnObject(new PhysicsShape(line));
            g.draw(line);
        }

        for (Ellipse2D ellipse : ellipses) {
            physicsEnvironment.spawnObject(new PhysicsShape(ellipse));
            g.draw(ellipse);
        }

        for (Arc2D arc : arcs) {
            physicsEnvironment.spawnObject(new PhysicsShape(arc));
            g.draw(arc);
        }

        flippers = updateFlippers();
        for (Line2D flipper : flippers) {
            g.draw(flipper);
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
        collisions.tick(ballSprite.getBall(), lines, ellipses, arcs, flippers);
        //updateFlippers();

        repaint();
    }

    private void inGame() {
        if (!inGame) {
            timer.stop();
        }
    }

    private Set<Line2D> updateFlippers() {
        Set<Line2D> flippers = new HashSet<>();
        double x = 0;
        double y = 0;
        double a = 0;

        //left
        if (leftPressed) {
            flipperLeftTick += flipperSpeed;
            if (flipperLeftTick >= 30) {
                flipperLeftTick = 30;
            }
        } else {
            flipperLeftTick -= 2 * flipperSpeed;
            if (-20 >= flipperLeftTick) {
                flipperLeftTick = -20;
            }
        }

        x = 60;
        y = 0;
        a =  Math.toRadians(-flipperLeftTick);
        x = (x * Math.cos(a) - y * Math.sin(a));
        y = (x * Math.sin(a) - y * Math.cos(a));

        flippers.add(new Line2D.Double(340, 720, x + 340, y + 720));

        //Right
        if (rightPressed) {
            flipperRightTick += flipperSpeed;
            if (flipperRightTick >= 30) {
                flipperRightTick = 30;
            }
        } else {
            flipperRightTick -= 2 * flipperSpeed;
            if (-20 >= flipperRightTick) {
                flipperRightTick = -20;
            }
        }

        x = -60;
        y = 0;
        a = Math.toRadians(flipperRightTick);

        x = (x * Math.cos(a) - y * Math.sin(a));
        y = (x * Math.sin(a) - y * Math.cos(a));
        flippers.add(new Line2D.Double(510, 720, x + 510, y + 720));

        return flippers;
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                leftPressed = true;
                ballSprite.getBall().applyForce(new Vec2d(-500.0,0.0));
            }
            if (key == KeyEvent.VK_RIGHT) {
                rightPressed = true;
                ballSprite.getBall().applyForce(new Vec2d(500.0,0.0));
            }
            if (key == KeyEvent.VK_UP) {
                ballSprite.getBall().applyForce(new Vec2d(0.0,-500.0));
            }
            if (key == KeyEvent.VK_DOWN) {
                ballSprite.getBall().applyForce(new Vec2d(0.0,500.0));
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (key == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            //flipperRight.keyReleased(e);
            //flipperLeft.keyReleased(e);
        }
    }
}