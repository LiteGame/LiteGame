package map;

import items.BallSprite;
import items.Score;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Boolean inGame;

    private int flipperRightTick = 0;
    private int flipperLeftTick = 0;
    private Boolean leftPressed = false;
    private Boolean rightPressed = false;

    private Map<Integer, Line2D> lines = new HashMap<>();
    private Map<Integer, Ellipse2D> ellipses = new HashMap<>();
    private Map<Integer, Arc2D> arcs = new HashMap<>();
    private Map<Integer, Line2D> flippers = new HashMap<>();

    private BallSprite ballSprite;
    private BallSprite ballSprite2;
    private Environment physicsEnvironment;
    private Score score = Score.getInstance();

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
            System.out.println("There was an error loading the config, loading default values: " + e.getMessage());
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
        lines.put(1000, new Line2D.Double(295,600,295,670));
        lines.put(1001, new Line2D.Double(295,670,340,720));
        lines.put(1002, new Line2D.Double(555,600,555,670));
        lines.put(1003, new Line2D.Double(555,670,510,720));

        //side shapes
        lines.put(1004, new Line2D.Double(310.5,527,250,200));
        arcs.put(2000, new Arc2D.Double(250, 560, 60, 100, 90, 90, Arc2D.OPEN));
        arcs.put(2001, new Arc2D.Double(250, 500, 60, 60, -90, 100, Arc2D.OPEN));
        lines.put(1005, new Line2D.Double(539.5,527,600,200));
        arcs.put(2002, new Arc2D.Double(540, 560, 60, 100, 90, -90, Arc2D.OPEN));
        arcs.put(2003, new Arc2D.Double(540, 500, 60, 60, -90, -100, Arc2D.OPEN));

        //top middle arcs
        arcs.put(2004, new Arc2D.Double(325, 61.5, 100, 112, 105, -105, Arc2D.OPEN));
        arcs.put(2005, new Arc2D.Double(425, 53.5, 100, 120, 75, 105, Arc2D.OPEN));

        //left shutter top
        arcs.put(2006, new Arc2D.Double(275, 120, 160, 150, 150, 40, Arc2D.OPEN));
        arcs.put(2007, new Arc2D.Double(280, 110, 160, 150, 90, 70, Arc2D.OPEN));
        arcs.put(2008, new Arc2D.Double(320, 110, 80, 80, 90, -70, Arc2D.OPEN));

        //right shutter top
        arcs.put(2009, new Arc2D.Double(400, 100, 200, 200, 0, 90, Arc2D.OPEN));
        arcs.put(2010, new Arc2D.Double(460, 100, 80, 80, 90, 85, Arc2D.OPEN));

        //Bumper left side
        lines.put(1006, new Line2D.Double(325,625,325,665));
        lines.put(1007, new Line2D.Double(344.3969,622,360,680));
        lines.put(1008, new Line2D.Double(325,665,347.5,687.5));
        arcs.put(2011, new Arc2D.Double(325, 615, 20, 20, 20, 160, Arc2D.OPEN));
        arcs.put(2012, new Arc2D.Double(345, 675, 15, 15, 35, -180, Arc2D.OPEN));

        //Bumper right side
        lines.put(1009, new Line2D.Double(525,625,525,665));
        lines.put(1010, new Line2D.Double(505.6031,622,490,680));
        lines.put(1011, new Line2D.Double(525,665,502.5,687.5));
        arcs.put(2013, new Arc2D.Double(505, 615, 20, 20, 160, -160, Arc2D.OPEN));
        arcs.put(2014, new Arc2D.Double(490, 675, 15, 15, 145, 180, Arc2D.OPEN));

        // Outside
        ellipses.put(3000, new Ellipse2D.Double(550,200,0.1,0.1));
        lines.put(1012, new Line2D.Double(250,750,625,750));
        lines.put(1013, new Line2D.Double(250, 750, 250, 200));
        lines.put(1014, new Line2D.Double(600, 750, 600, 200));
        lines.put(1015, new Line2D.Double(625, 200, 625, 750));
        //bumpers middle
        ellipses.put(3001, new Ellipse2D.Double(420,220,50,50));
        ellipses.put(3002, new Ellipse2D.Double(370,170,40,40));
        ellipses.put(3003, new Ellipse2D.Double(340,260,60,60));
        ellipses.put(3004, new Ellipse2D.Double(475,150,80,80));

        arcs.put(2015, new Arc2D.Double(250, 50, 375, 300, 0, 180, Arc2D.OPEN));

        //ballSprite = new BallSprite(new Ball(physicsEnvironment, new Vec2d(balStartX,balStartY), 10.0, 5.0));
        //ballSprite.loadImage("resources/dot.png");
        //ballSprite.setVisible(true);
        BallFactory fact = new BallFactory(physicsEnvironment, new Vec2d(balStartX,balStartY));
        ballSprite = fact.create().getSprite();
        ballSprite2 = fact.create(new Vec2d(balStartX-150, balStartY-50), 10,5).getSprite();
        //physicsEnvironment.spawnObject(ballSprite.getBall());
        physicsEnvironment.setGravity(9.81);
        //flipperRight = new Flipper_Right(550,400, -195);
        //flipperLeft = new Flipper_Left(200, 400, 15);

        for (Map.Entry<Integer, Line2D> line: lines.entrySet()) {
            physicsEnvironment.spawnObject(new PhysicsShape(line.getKey(), line.getValue()));
        }

        for (Map.Entry<Integer, Ellipse2D> ellipse: ellipses.entrySet()) {
            physicsEnvironment.spawnObject(new PhysicsShape(ellipse.getKey(), ellipse.getValue()));
        }

        for (Map.Entry<Integer, Arc2D> arc: arcs.entrySet()) {
            physicsEnvironment.spawnObject(new PhysicsShape(arc.getKey(), arc.getValue()));
        }

        timer = new Timer(10, this);
        timer.start();
    }

    public Map getLines() {
        return lines;
    }

    public Map getEllipses() {
        return ellipses;
    }

    public Map getArcs() {
        return arcs;
    }

    public Map getFlippers() {
        return flippers;
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintComponent((Graphics2D) g);
    }

    private void paintComponent(Graphics2D g) {
        super.paintComponent(g);

        if (score.getScore() >= 10000){
            inGame = false;
        }

        if (inGame) {
            drawObjects(g);
        } else {
            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g) {
        g.setColor(Color.WHITE);

        if (ballSprite.isVisible()) {
            g.drawImage(ballSprite.getImage(), (int) ballSprite.getPosition().x, (int) ballSprite.getPosition().y, this);
        }

        if (ballSprite2.isVisible()) {
            g.drawImage(ballSprite2.getImage(), (int) ballSprite2.getPosition().x, (int) ballSprite2.getPosition().y, this);
        }

        for (Map.Entry<Integer, Line2D> line: lines.entrySet()) {
            g.draw(line.getValue());
        }

        for (Map.Entry<Integer, Ellipse2D> ellipse: ellipses.entrySet()) {
            g.draw(ellipse.getValue());
        }

        for (Map.Entry<Integer, Arc2D> arc: arcs.entrySet()) {
            g.draw(arc.getValue());
        }


        /**
        flippers = updateFlippers();
        for (Line2D flipper : flippers) {
            physicsEnvironment.spawnStaticObject(new PhysicsShape(flipper));
            g.draw(flipper);
        }
         **/

        drawScore(g);
    }

    private void drawGameOver(Graphics2D g) {
        String msg = "Game Over, your score: " + score.getScore();
        Font big = new Font("Verdana", Font.BOLD, 30);
        FontMetrics fm = getFontMetrics(big);

        g.setColor(Color.white);
        g.setFont(big);
        g.drawString(msg, (boardWidth - fm.stringWidth(msg)) / 2,boardHeight / 2);
    }

    private void drawScore(Graphics2D g) {
        String msg = score.getScore() + "";
        Font small = new Font("Verdana", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString("Score:", 20, 20);
        g.drawString(msg, 150 - fm.stringWidth(msg),20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();
        physicsEnvironment.tick();
        repaint();
    }

    private void inGame() {
        if (!inGame) {
            timer.stop();
        }
        else {
            score.addToScore(10);
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
            }
            if (key == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (key == KeyEvent.VK_A) {
                ballSprite.getBall().applyForce(new Vec2d(-3000.0,0.0));
            }
            if (key == KeyEvent.VK_D) {
                ballSprite.getBall().applyForce(new Vec2d(3000.0,0.0));
            }
            if (key == KeyEvent.VK_W) {
                ballSprite.getBall().applyForce(new Vec2d(0.0,-3000.0));
            }
            if (key == KeyEvent.VK_S) {
                ballSprite.getBall().applyForce(new Vec2d(0.0,3000.0));
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