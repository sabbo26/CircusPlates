package eg.edu.alexu.csd.oop.game67;

import eg.edu.alexu.csd.oop.game.GameEngine;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Launcher {
    private JFrame f;
    private JButton exit;

    Launcher() {
        f = new JFrame();
        f.setLayout(null);
        f.getContentPane().setBackground(Color.BLUE);
        f.setTitle("DISNEY");
        f.setSize(600, 850);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);
        ImageIcon icon = new ImageIcon(getimage("res\\d2.jpg"));
        JLabel label = new JLabel("");
        label.setIcon(icon);
        f.add(label);
        label.setBounds(0, 0, 600, 900);
        JButton start = new JButton();
        ImageIcon starticon2 = new ImageIcon(getimage("res\\Save.png"));
        start.setIcon(starticon2);
        f.add(start);
        start.setBounds(200, 100, 170, 70);
        exit = new JButton();
        ImageIcon exiticon2 = new ImageIcon(getimage("res\\exit.png"));
        exit.setIcon(exiticon2);
        f.add(exit);
        exit.setBounds(200, 200, 170, 70);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
                System.exit(0);
            }
        });
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setVisible(false);
                exit.setVisible(false);
                JButton L1 = new JButton();
                L1.setBounds(0, 0, 600, 250);
                f.add(L1);
                label.add(L1);
                ImageIcon icon2 = new ImageIcon(getimage("res\\b.png"));
                L1.setIcon(icon2);
                L1.setBackground(Color.white);
                JButton L2 = new JButton();
                f.getContentPane().add(L2);
                ImageIcon icon3 = new ImageIcon(getimage("res\\l2.jpg"));
                L2.setIcon(icon3);
                L2.setBackground(Color.white);
                L2.setBounds(0, 250, 600, 300);
                label.add(L2);
                JButton L3 = new JButton();
                f.add(L3);
                L3.setBounds(0, 550, 600, 300);
                ImageIcon icon4 = new ImageIcon(getimage("res\\l3.jpg"));
                L3.setIcon(icon4);
                L3.setBackground(Color.white);
                label.add(L3);
                f.add(label);
                L1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Button_Performance(1,"res\\mickey2.png",10);
                    }
                });
                L2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Button_Performance(2,"res\\l2b.jpg",8);
                    }
                });
                L3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Button_Performance(3,"res\\l3b.jpg",7);
                    }
                });
            }
        });
    }



    public void Button_Performance(int level, String path,int speed) {
        f.setVisible(false);
        exit.setVisible(false);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        menu.add(newMenuItem);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menuBar.add(menu);
        JButton undo = new JButton("undo");
        undo.setBounds(30, 40, 50, 50);
        undo.setBackground(menuBar.getBackground().darker());
        undo.setVisible(true);
        menuBar.add(undo);
        JButton redo = new JButton("redo");
        redo.setBounds(30, 40, 50, 50);
        redo.setBackground(menuBar.getBackground().darker());
        redo.setVisible(true);
        menuBar.add(redo);
        final MyGameWorld[] GW = {new MyGameWorld(550, 700, level, path,speed)};
        final GameEngine.GameController gameController = GameEngine.start("Disney Plates❤", GW[0], menuBar, Color.BLACK);
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GW[0].undo();
            }
        });
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GW[0].redo();
            }
        });
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GW[0] = new MyGameWorld(800, 700, level, path,speed);
                gameController.changeWorld(GW[0]);
            }
        });
        pauseMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.pause();
            }
        });
        resumeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.resume();
            }
        });
    }

    private static Image getimage(String path){
        BufferedImage im = null;
        File f = new File(path);
        try {
            im = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return im;
    }

}
