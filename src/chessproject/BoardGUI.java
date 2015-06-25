/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class BoardGUI {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    private final JLabel message = new JLabel(
            "Chess Champ is ready to play!");
    private static final String COLS = "ABCDEFGH";
    private ImageIcon piece;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Chess Board");
        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel serverStatus = new JLabel("Sever Connected.");

        JPanel panel = new JPanel(new GridLayout(9, 9));
        JMenuBar menu = new JMenuBar();
        menu.add(new JButton("New Game"));
        menu.add(new JButton("Help"));
        panel.add(menu);
        mainPanel.add(menu, BorderLayout.NORTH);
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(serverStatus, BorderLayout.PAGE_END);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                System.out.println(actionEvent.getActionCommand());

            }
        };

        JButton[][] squares = new JButton[8][8];
        Border emptyBorder = BorderFactory.createEmptyBorder();

        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {

                JButton tempButton = new JButton();

                tempButton.setBorder(emptyBorder);
                tempButton.setSize(64, 64);

                if ((row % 2 == 1 && col % 2 == 1) || (row % 2 == 0 && col % 2 == 0)) {
                    tempButton.setText(row + "" + col);
                    tempButton.setFont(new Font("Arial", 0, 0));
                    tempButton.setForeground(Color.WHITE);
                    tempButton.setBackground(Color.WHITE);
                } else {
                    tempButton.setForeground(Color.BLACK);
                    tempButton.setText(row + "" + col);
                    tempButton.setFont(new Font("Arial", 0, 0));
                    tempButton.setBackground(Color.BLACK);
                }
                squares[row][col] = tempButton;
                squares[row][col].addActionListener(actionListener);
                panel.add(squares[row][col]);

                squares[0][0].setIcon(new ImageIcon(BoardGUI.class.getResource("knight4.png"), "knight"));


          
            }
        }

        frame.pack();
        frame.add(mainPanel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void setCurrentPiece(ImageIcon piece) {
        this.piece = piece;
    }

    public ImageIcon getCurrentPiece() {
        return piece;
    }

}
