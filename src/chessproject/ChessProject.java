/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessProject {

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Chess Board");
        JPanel panel = new JPanel(new BorderLayout(8,8));
        JButton b =  new JButton(new ImageIcon("src/knight4.png"));
        panel.add(b);
        frame.add(panel);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(600,600);
                
        
        
//        Board b = new Board();
//        b.displayBoard();
//        b.addPiece(1, 1, "K");
//        b.displayBoard();
//        b.newGame();
//        b.displayBoard();
//        b.makeMove();
    }
}



