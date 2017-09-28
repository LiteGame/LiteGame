package map;

import items.*;
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
    private Boolean LeftPressed = false;
    private Boolean RightPressed = false;
    private boolean ingame;

    private Ball ball;

    private Set<Line2D> lines = new HashSet<>();
    private Set<Ellipse2D> ellipses = new HashSet<>();

    private BallSprite ballSprite;
    private Environment physicsEnvironment;
    private Collisions collisions;

    private int balStartX;
    private int balStartY;
    private int boardWidth;
    private int boardHeight;
    private int balSpeed;



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
            balSpeed = config.getIntegerValueOf("startSpeed");
        } catch (Exception e){
            System.out.println("There was an error loading the config, loading default values:" + e);
            // Default in case config file fails.
            balStartX = 570;
            balStartY = 520;
            boardWidth = 800;
            boardHeight = 600;
            balSpeed = 8;
        }
    }


    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;

        setPreferredSize(new Dimension(boardWidth, boardHeight));

        physicsEnvironment = new Environment();

        ellipses.add(new Ellipse2D.Double(570,300,100,100));
        lines.add(new Line2D.Double(0,580,800,590));
        lines.add(new Line2D.Double(200, 50, 350, 450));
        lines.add(new Line2D.Double(550, 500, 50, 50));
        lines.add(new Line2D.Double(600, 100, 600, 500));

        ball = new Ball(physicsEnvironment, new Vec2d(balStartX,balStartY), 10.0);
        ballSprite = new BallSprite(ball);
        ballSprite.loadImage("resources/dot.png");
        ballSprite.setVisible(true);
        physicsEnvironment.spawnProp(ball);

        flipperRight = new Flipper_Right(new Vec2d(550,400), -195);
        flipperLeft = new Flipper_Left(new Vec2d(200, 400), 15);

        timer = new Timer(balSpeed, this);
        timer.start();
    }

    public Set getLines() {
        return lines;
    }

    public Set getEllipses() {
        return ellipses;
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
            g.drawImage(ballSprite.getImage(), (int) ballSprite.getPosition().x, (int) ballSprite.getPosition().y, this);
        }

        if (flipperRight.isVisible()) {
            identity_right.translate(flipperRight.getPosition().x, flipperRight.getPosition().y);
            trans_right.setTransform(identity_right);
            trans_right.rotate( Math.toRadians(flipperRight.getAngle()) );
            trans_right.getScaleInstance(1, -1);
            trans_right.translate(0, -flipperRight.getImage().getHeight(null));
            g.drawImage(flipperRight.getImage(), trans_right, this);
        }

        if (flipperLeft.isVisible()) {
            identity_left.translate(flipperLeft.getPosition().x, flipperLeft.getPosition().y);
            trans_left.setTransform(identity_left);
            trans_left.rotate( Math.toRadians(flipperLeft.getAngle()) );
            g.drawImage(flipperLeft.getImage(), trans_left, this);
        }

        Path2D triangle = new Path2D.Float();
        triangle.moveTo(550, 50);
        triangle.lineTo(600, 100);
        triangle.lineTo(600, 50);
        triangle.closePath();
        g.draw(triangle);

        Iterator<Line2D> iteratorlines = lines.iterator();
        while(iteratorlines.hasNext()) {
            g.draw(iteratorlines.next());
        }

        Iterator<Ellipse2D> iteratorellipses = ellipses.iterator();
        while(iteratorellipses.hasNext()) {
            g.draw(iteratorellipses.next());
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
        updateFlippers();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
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
                LeftPressed = true;
            }
            if (key == KeyEvent.VK_RIGHT) {
                RightPressed = true;
            }
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

