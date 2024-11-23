// written by Zuhur Hashi, Hashi136 && Yusra Hersi, Hersi032

import java.util.Scanner;

public class Piece {
    // Creates Instance variables
    public char character;
    public int row;
    public int col;
    public boolean isBlack;

    public Piece(char character, int row, int col, boolean isBlack) {
        //constructor initializes instance variables
        this.character = character;
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }


    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        // checks if move is legal using cases
        switch (this.character) {
            case '\u2659':
            case '\u265f':
                Pawn pawn = new Pawn(row, col, isBlack);
                return pawn.isMoveLegal(board, endRow, endCol);
            case '\u2656':
            case '\u265c':
                Rook rook = new Rook(row, col, isBlack);
                return rook.isMoveLegal(board, endRow, endCol);
            case '\u265e':
            case '\u2658':
                Knight knight = new Knight(row, col, isBlack);
                return knight.isMoveLegal(board, endRow, endCol);
            case '\u265d':
            case '\u2657':
                Bishop bishop = new Bishop(row, col, isBlack);
                return bishop.isMoveLegal(board, endRow, endCol);
            case '\u265b': //black
            case '\u2655':
                Queen queen = new Queen(row, col, isBlack);
                return queen.isMoveLegal(board, endRow, endCol);
            case '\u265a':
            case '\u2654':
                King king = new King(row, col, isBlack);
                return king.isMoveLegal(board, endRow, endCol);
            default:
                return false;
        }
    }

    public void setPosition(int row, int col) {
        // sets the position of row and col
        this.row = row;
        this.col = col;
    }

    public boolean getIsBlack() {
        // returns color of the piece
        return this.isBlack;
    }


    public void promotePawn(int row, boolean isBlack) {
        if(this.isBlack && row == 7 || !this.isBlack && row == 0){
            //checks that the piece is at the end of the board
            System.out.println("Pick a piece to promote: Enter only the number associated with the piece");
            System.out.println("Queen: 1" + '\n' + "Bishop: 2" + '\n' + "knight: 3" + '\n' + "Rook: 4" + '\n');
            Scanner myscanner = new Scanner(System.in);
            int input = myscanner.nextInt();
            //takes in a piece to promote and changes the starting piece (pawn) to the piece player wants to promote depending on their input
            if (input == 1){
                if(this.isBlack) {
                    this.character = '\u265b';
                }
                if(!this.isBlack){
                    this.character = '\u2655';
                }
            }
            else if (input == 2){
                if(this.isBlack){
                    this.character = '\u265d';
                }
                if(!this.isBlack){
                    this.character = '\u2657';
                }

            }
            else if (input == 3){
                if(this.isBlack){
                    this.character = '\u265e';
                }
                if(!this.isBlack){
                    this.character = '\u2658';
                }

            }
            else if (input == 4){
                if(this.isBlack){
                    this.character = '\u265c';
                }
                if(!this.isBlack){
                    this.character = '\u2656';
                }
            }
        }
    }

    public String toString() {
        //returns a string representation of the piece
            String finalString = "";
            finalString += this.character;
            return finalString;
    }


}
