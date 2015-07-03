/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Robbie
 */
public class Board {

    String chessboard[][] = new String[8][8];

    private String blackPawn, blackQueen, blackKing, blackKnight, blackRook, blackBishop;
    private String whitePawn, whiteQueen, whiteKing, whiteKnight, whiteRook, whiteBishop;
    private String[] white, black;
    private ArrayList<String> killedPieces = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        boolean winner = false;
        boolean connected = true;
        Board b = new Board();
        b.newGame();
        while (!winner && connected) {
            b.makeMove();
        }
        System.exit(0);
    }

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
        blackPawn = "BP";
        whitePawn = "WP";
        for (int col = 0; col < chessboard.length; col++) {
            addPiece(1, col, blackPawn);
            addPiece(6, col, whitePawn);
        }

        //Add Rook
        blackRook = "BR";
        whiteRook = "WR";
        addPiece(0, 0, blackRook);
        addPiece(0, 7, blackRook);
        addPiece(7, 0, whiteRook);
        addPiece(7, 7, whiteRook);

        //Add Knigt
        blackKnight = "BN";
        whiteKnight = "WN";
        addPiece(0, 1, blackKnight);
        addPiece(0, 6, blackKnight);
        addPiece(7, 1, whiteKnight);
        addPiece(7, 6, whiteKnight);

        //Add Bishop
        blackBishop = "BB";
        whiteBishop = "WB";
        addPiece(0, 2, blackBishop);
        addPiece(0, 5, blackBishop);
        addPiece(7, 2, whiteBishop);
        addPiece(7, 5, whiteBishop);

        //Add Queen
        blackQueen = "BQ";
        whiteQueen = "WQ";

        addPiece(0, 3, blackQueen);
        addPiece(7, 3, whiteQueen);

        //Add King
        blackKing = "BK";
        whiteKing = "WK";
        addPiece(0, 4, blackKing);
        addPiece(7, 4, whiteKing);

        white = new String[]{whitePawn, whiteQueen, whiteKing, whiteKnight, whiteRook, whiteBishop};
        black = new String[]{blackPawn, blackQueen, blackKing, blackKnight, blackRook, blackBishop};

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

        if (s.equals("exit")) {
            System.out.println("Are you sure you wish to exit the game?");
            String response = br.readLine();
            if (response.equalsIgnoreCase("Y")) {
                endGame();
            }
        } else if (s.equals("taken")) {
            taken();
        } else if (s.equals("board")) {
            displayBoard();
        } else {

            char[] moves = s.toCharArray();

            //Example a8,e3
            try {
                moves[0] = Character.toLowerCase(moves[0]);
                moves[4] = Character.toLowerCase(moves[4]);
            } catch (ArrayIndexOutOfBoundsException e) {
                makeMove();
            }
            int currentRow = moves[0] - 97;
            int newRow = moves[3] - 97;

            try {
                currentCol = moves[1] - 49;
                newCol = moves[4] - 49;
            } catch (Exception e) {
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

    }

    public void move(int currentRow, int currentCol, int newRow, int newCol) {

        System.out.println(getPiece(currentRow, currentCol));
        if (checkMove(getPiece(currentRow, currentCol), currentRow, currentCol, newRow, newCol)) {
            chessboard[newRow][newCol] = getPiece(currentRow, currentCol);
            chessboard[currentRow][currentCol] = "  ";
            displayBoard();
        } else {
            System.out.println("Invalid Move");
            //displayBoard();
        }

    }

    public boolean checkMove(String piece, int currentRow, int currentCol, int newRow, int newCol) {

        //Pawn
        if (piece.equalsIgnoreCase(blackPawn)) {
            return bPawn(piece, currentRow, currentCol, newRow, newCol);
        } else if (piece.equalsIgnoreCase(whitePawn)) {
            return wPawn(piece, currentRow, currentCol, newRow, newCol);
        } else if (piece.equalsIgnoreCase(blackKing)) {
            return bKing(piece, currentRow, currentCol, newRow, newCol);
        } else if (piece.equalsIgnoreCase(blackKnight)) {
            return bKnight(piece, currentRow, currentCol, newRow, newCol);
        } else if (piece.equalsIgnoreCase(whiteRook)) {
            return wRook(piece, currentRow, currentCol, newRow, newCol);
        } else if (piece.equalsIgnoreCase(blackBishop)) {
            return bBishop(piece, currentRow, currentCol, newRow, newCol);
        } else if (piece.equalsIgnoreCase(whiteBishop)) {
            return bBishop(piece, currentRow, currentCol, newRow, newCol);
        }
        return false;

    }

    public boolean bPawn(String piece, int currentRow, int currentCol, int newRow, int newCol) {

        //Check if possible position to take an enemy
        if (newRow == currentRow + 1 && newCol == currentCol && getPiece(newRow, newCol) == null) {
            return true;
        }
        for (int i = 0; i < white.length; i++) {
            if (getPiece(newRow, newCol).equals(white[i])) { //If any of the new coordinates is diagonal then it can move
                //check if making that move is possible with pawn
                if (newRow == currentRow + 1 && newCol == currentCol + 1) { //Check diagnonals
                    return true;
                } else if (newRow == currentRow + 1 && newCol == currentCol - 1) { //Check diagonals
                    return true;
                }
            }
        }

        return false;
    }

    public boolean wPawn(String piece, int currentRow, int currentCol, int newRow, int newCol) {

        //Check if possible position to take an enemy
        if (newRow == currentRow - 1 && newCol == currentCol && getPiece(newRow, newCol) == null) {
            return true;
        }
        for (int i = 0; i < black.length; i++) {
            if (getPiece(newRow, newCol).equals(black[i])) { //If any of the new coordinates is diagonal then it can move
                //check if making that move is possible with pawn
                if (newRow == currentRow + 1 && newCol == currentCol + 1) { //Check diagnonals
                    return true;
                } else if (newRow == currentRow + 1 && newCol == currentCol - 1) { //Check diagonals
                    return true;
                }
            }
        }

        return false;
    }

    public boolean bKing(String piece, int currentRow, int currentCol, int newRow, int newCol) {

        //Check if possible position to take an enemy
        if (newRow == currentRow - 1 && newCol == currentCol) {
            return true;
        } else if (newRow == currentRow + 1 && newCol == currentCol) {
            return true;
        } else if (newRow == currentRow && newCol == currentCol - 1) {
            return true;
        } else if (newRow == currentRow && newCol == currentCol + 1) {
            return true;
        } else if (newRow == currentRow + 1 && newCol == currentCol + 1) {
            return true;
        } else if (newRow == currentRow + 1 && newCol == currentCol - 1) {
            return true;
        } else if (newRow == currentRow - 1 && newCol == currentCol - 1) {
            return true;
        } else if (newRow == currentRow - 1 && newCol == currentCol + 1) {
            return true;
        }
        return false;
    }

    public boolean wKing(String piece, int currentRow, int currentCol, int newRow, int newCol) {

        //Check if possible position to take an enemy
        if (newRow == currentRow - 1 && newCol == currentCol) {
            return true;
        } else if (newRow == currentRow + 1 && newCol == currentCol) {
            return true;
        } else if (newRow == currentRow && newCol == currentCol - 1) {
            return true;
        } else if (newRow == currentRow && newCol == currentCol + 1) {
            return true;
        } else if (newRow == currentRow + 1 && newCol == currentCol + 1) {
            return true;
        } else if (newRow == currentRow + 1 && newCol == currentCol - 1) {
            return true;
        } else if (newRow == currentRow - 1 && newCol == currentCol - 1) {
            return true;
        } else if (newRow == currentRow - 1 && newCol == currentCol + 1) {
            return true;
        }
        return false;
    }

    public boolean bKnight(String piece, int currentRow, int currentCol, int newRow, int newCol) {
        if (newRow == currentRow + 2 && newCol == currentCol - 1) {
            return true;
        } else if (newRow == currentRow + 2 && newCol == currentCol + 1) {
            return true;
        } else if (newRow == currentRow - 2 && newCol == currentCol - 1) {
            return true;
        } else if (newRow == currentRow - 2 && newCol == currentCol + 1) {
            return true;
        }
        return false;
    }

    public boolean wKnight(String piece, int currentRow, int currentCol, int newRow, int newCol) {
        if (newRow == currentRow + 2 && newCol == currentCol - 1) {
            return true;
        } else if (newRow == currentRow + 2 && newCol == currentCol + 1) {
            return true;
        } else if (newRow == currentRow - 2 && newCol == currentCol - 1) {
            return true;
        } else if (newRow == currentRow - 2 && newCol == currentCol + 1) {
            return true;
        }
        return false;
    }
    //ROOK DISTANCE CHECK!!! *********************************************
    public boolean wRook(String piece, int currentRow, int currentCol, int newRow, int newCol) {
        if (currentRow == newRow && currentCol != newCol) {
            int distance = currentCol - newCol;
            System.out.println(Math.signum(distance) + " where distance is: " + distance);
            if (Math.signum(distance) == -1) {
              System.out.println("first if statement");
                for (int i = 0; i < newCol; i++) {
                    if (getPiece(currentRow, currentCol - i) != null) {
                        return false;
                    }
                }
            } else {
                
                for (int i = 0; i < newCol; i++) {
                    if (getPiece(currentRow, i + currentCol) != null) {
                        return false;
                    }
                }
                return true;
            }
        } else if (currentRow != newRow && currentCol == newCol) {
            System.out.println("Equal");
            int distance = currentCol - newCol;
            
            System.out.println("distance:" + distance);
            if (Math.signum(distance) == -1) {
                System.out.println("this");
                for (int i = 0; i < newRow; i++) {
                    if (getPiece(currentRow - i, currentCol) != null) {
                        return false;
                    }
                }
            } else {
                int tempRow = 7 - newRow;
                for (int i = 1; i < tempRow; i++) {
                    System.out.println(getPiece(currentRow+i, currentCol) + " Current Piece");
                    if (getPiece(currentRow + i, currentCol) != null) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean bRook(String piece, int currentRow, int currentCol, int newRow, int newCol) {
        if (currentRow == newRow && currentCol != newCol) {
            int distance = currentCol - newCol;
            if (Math.signum(distance) == -1) {
                Math.abs(distance);
                for (int i = 0; i < newCol; i++) {
                    if (getPiece(currentRow, currentCol - i) != null) {
                        return false;
                    }
                }
            } else {
                Math.abs(distance);
                for (int i = 0; i < newCol; i++) {
                    if (getPiece(currentRow, i + currentCol) != null) {
                        return false;
                    }
                }
                return true;
            }
        } else if (currentRow != newRow && currentCol == newCol) {

            int distance = currentCol - newCol;
            if (Math.signum(distance) == -1) {
                Math.abs(distance);
                for (int i = 0; i < newRow; i++) {
                    if (getPiece(currentRow - i, currentCol) != null) {
                        return false;
                    }
                }
            } else {
                Math.abs(distance);
                for (int i = 0; i < newRow; i++) {
                    if (getPiece(currentRow + i, currentCol) != null) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean bBishop(String piece, int currentRow, int currentCol, int newRow, int newCol) {
        int distanceRow = currentRow - newRow;
        System.out.println(distanceRow);
        int distanceCol = currentCol - newCol;
        System.out.println(distanceCol);
        distanceRow = Math.abs(distanceRow);
        distanceCol = Math.abs(distanceCol);
        System.out.println(distanceRow);
        System.out.println(distanceCol);
        if (distanceRow - distanceCol == 0) {
            return true;
        }
        return false;
    }

    public boolean wBishop(String piece, int currentRow, int currentCol, int newRow, int newCol) {
        int distanceRow = currentRow - newRow;
        System.out.println(distanceRow);
        int distanceCol = currentCol - newCol;
        System.out.println(distanceCol);
        distanceRow = Math.abs(distanceRow);
        distanceCol = Math.abs(distanceCol);
        System.out.println(distanceRow);
        System.out.println(distanceCol);
        if (distanceRow - distanceCol == 0) {
            return true;
        }
        return false;
    }
    
    public boolean bQueen(String piece, int currentRow, int currentCol, int newRow, int newCol){
        
        //BishopFeatures
        int distanceRow = currentRow - newRow;
        System.out.println(distanceRow);
        int distanceCol = currentCol - newCol;
        System.out.println(distanceCol);
        distanceRow = Math.abs(distanceRow);
        distanceCol = Math.abs(distanceCol);
        System.out.println(distanceRow);
        System.out.println(distanceCol);
        if (distanceRow - distanceCol == 0) {
            return true;
        }
        
     return false;   
    }

    public void addPiece(int row, int col, String piece) {
        chessboard[row][col] = new String(piece);
    }

    public String[][] getBoard() {
        return chessboard;
    }

    public void displayBoard() {

        String[] alphabet = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        System.out.println("Chess Board");
        for (int row = 0; row < chessboard.length; row++) {
            // System.out.println("");
            if (row == 0) {
                System.out.println((char) 27 + "[31m  |1 |2 |3 |4 |5 |6 |7 |8 |" + (char) 27 + "[0m");
            } else {
                System.out.println("");
            }
            System.out.println("  -------------------------");

            for (int col = 0; col < chessboard[row].length; col++) {

                if (chessboard[row][col] != null) {
                    if (col == 0) {
                        System.out.print((char) 27 + "[31m" + alphabet[row] + (char) 27 + "[0m" + " |" + chessboard[row][col]);
                    } else {
                        System.out.print("|" + chessboard[row][col]);
                    }
                } else {
                    if (col == 0) {
                        System.out.print((char) 27 + "[31m" + alphabet[row] + (char) 27 + "[0m" + " |  ");
                    } else {
                        System.out.print("|  ");
                    }
                }
                if (col + 1 == chessboard[row].length) {
                    System.out.print("|");
                }
            }
        }
        System.out.println("");
        System.out.println("");
    }

    public void killed(String piece) {

        if (piece.equals(blackKing)) {
            System.out.println("Congratulations, White Wins!");
            endGame();
        } else if (piece.equals(whiteKing)) {
            System.out.println("Congratulations, Black Wins!");
            endGame();
        } else {
            System.out.println(piece + " was taken.");
        }
        killedPieces.add(piece);
    }

    public void endGame() {
        System.exit(0);
    }

    public void taken() {
        if (killedPieces.isEmpty()) {
            System.out.println("No pieces have been taken");
        }
        Collections.sort(killedPieces);
        for (String pieces : killedPieces) {
            System.out.println(pieces);
        }
    }

}
