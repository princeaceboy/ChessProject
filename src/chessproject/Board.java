/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Robbie
 */
public class Board {

    String chessboard[][] = new String[8][8];

    public Board() {

    }

    public void newGame() {
        clearBoard();
        initalisePieces();
        displayBoard();
    }

    public void clearBoard() {
        chessboard = new String[8][8];
    }

    public void initalisePieces() {

        //Add Pawns
        String blackPawn = "P";
        String whitePawn = "P";
        for (int col = 0; col < chessboard.length; col++) {
            addPiece(1, col, blackPawn);
            addPiece(6, col, whitePawn);
        }

        //Add Rook
        String BlackRook = "R";
        String whiteRook = "R";
        addPiece(0, 0, BlackRook);
        addPiece(0, 7, BlackRook);
        addPiece(7, 0, whiteRook);
        addPiece(7, 7, whiteRook);

        //Add Knigt
        String blackKnight = "N";
        String whiteKnight = "N";
        addPiece(0, 1, blackKnight);
        addPiece(0, 6, blackKnight);
        addPiece(7, 1, whiteKnight);
        addPiece(7, 6, whiteKnight);

        //Add Bishop
        String BlackBishop = "B";
        String whiteBishop = "B";
        addPiece(0, 2, BlackBishop);
        addPiece(0, 5, BlackBishop);
        addPiece(7, 2, whiteBishop);
        addPiece(7, 5, whiteBishop);

        //Add Queen
        String blackQueen = "Q";
        String whiteQueen = "Q";

        addPiece(0, 3, blackQueen);
        addPiece(7, 3, whiteQueen);

        //Add King
        String blackKing = "K";
        String whiteKing = "K";
        addPiece(0, 4, blackKing);
        addPiece(7, 4, whiteKing);

    }

    public String getPiece(int row, int col) {
        return chessboard[row][col];

    }

    public void makeMove() throws IOException {

        int currentCol = 99;
        int newCol = 99;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Move Format(a1,a3) - a1 is where the piece is currently, a3 is where you want to move the piece");
        System.out.print("Enter Move: ");
        String s = br.readLine();
        char[] moves = s.toCharArray();

        //Example a8,e3
        moves[0] = Character.toLowerCase(moves[0]);
        moves[4] = Character.toLowerCase(moves[4]);

        int currentRow = moves[0] - 97;
        int newRow = moves[3] - 97;

        try {
            currentCol = moves[1] - 49;
            newCol = moves[4] - 49;
        } catch (NumberFormatException e) {
            System.out.println("Invalid Move, please try again");
            makeMove();
        }
        try {
            System.out.println(currentRow + "" + currentCol + "" + newRow + "" + newCol);
            move(currentRow, currentCol, newRow, newCol);
        } catch (Exception e) {
            System.out.println("Invalid Move, please try again");
            makeMove();
        }

    }

    public void move(int currentRow, int currentCol, int newRow, int newCol) {

        chessboard[newRow][newCol] = getPiece(currentRow, currentCol);
        chessboard[currentRow][currentCol] = " ";
        displayBoard();

    }

    public boolean checkMove(String piece) {

        return true;
    }

    public void addPiece(int row, int col, String piece) {
        chessboard[row][col] = new String(piece);
    }

    public String[][] getBoard() {
        return chessboard;
    }

    public void displayBoard() {
        System.out.println("Chess Board");
        for (int row = 0; row < chessboard.length; row++) {
            System.out.println("");
            System.out.println("-----------------");

            for (int col = 0; col < chessboard[row].length; col++) {

                if (chessboard[row][col] != null) {
                    System.out.print("|" + chessboard[row][col]);
                } else {
                    System.out.print("| ");
                }
                if (col + 1 == chessboard[row].length) {
                    System.out.print("|");
                }
            }
        }
        System.out.println("");
    }
}
