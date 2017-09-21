package game;

import map.Board;
import nl.tu.delft.defpro.api.APIProvider;
import nl.tu.delft.defpro.api.IDefProAPI;

import java.awt.EventQueue;
import javax.swing.*;


public class Launcher extends JFrame {

    public Launcher() {

        initUI();

    }

    private IDefProAPI getConfig() {
        String path = "config.txt";
        return APIProvider.getAPI(path);
    }

    private void initUI() {

        add(new Board(getConfig()));

        setResizable(false);
        pack();

        setTitle("Collision");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Launcher ex = new Launcher();
            ex.setVisible(true);
        });
    }
}