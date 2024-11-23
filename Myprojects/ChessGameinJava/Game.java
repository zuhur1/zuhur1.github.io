// written by Zuhur Hashi, Hashi136 && Yusra Hersi, Hersi032

import java.util.Scanner;
public class Game {
    //instance variables
    private int row;
    private int col;
    private Board board;

    public Game(int row, int col, Board board) {
        //constructor initializes instance variables
        this.row = row;
        this.col = col;
        this.board = board;
    }

    public static void main(String[] args) {
        boolean isBlack = false; // has a boolean isBlack setting the color to white first
        Board myBoard = new Board();
        Fen.load("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPQP/RNBQKBNR", myBoard); //loads board
        Scanner scanner = new Scanner(System.in);
        while (!myBoard.isGameOver()) { //while loop going while game is not over
            System.out.println(myBoard);
            if (isBlack == true) { //sets it to blacks turn to play
                System.out.println("it is currently Black's turn to play"); //black
            }else{ //whites turn to play
                System.out.println("it is currently White's turn to play"); //white
            }
            System.out.println("What is your move? (format: [start row] [start col] [end row] [end col]"); //user input
            String finalMove = scanner.nextLine(); //scanner scans user input line
            // assume the user correctly inputs
            String[] array = finalMove.split(" "); //splits string by white space and uses indexing to load onto array
            int srow = Integer.parseInt(array[0]); //use parse int to change into ints and uses indexing to set each part of user input equal to starting and ending positions
            int scol = Integer.parseInt(array[1]);
            int erow = Integer.parseInt(array[2]);
            int ecol = Integer.parseInt(array[3]);
            while (srow < 0 || srow > 7 || scol < 0 || scol > 7 || erow < 0 || erow > 7 || ecol < 0 || ecol > 7) { //checks if it's out of bounds
                System.out.println("Invalid move. Please try again."); //prompts user to reinput if out of bounds
                finalMove = scanner.nextLine();
                array = finalMove.split(" ");
                srow = Integer.parseInt(array[0]); //reassigns user correct user input to starting and ending positions
                scol = Integer.parseInt(array[1]);
                erow = Integer.parseInt(array[2]);
                ecol = Integer.parseInt(array[3]);
            }
            Piece piece = myBoard.getPiece(srow, scol); //creates new piece object
            if (piece.isMoveLegal(myBoard, erow , ecol)) { //checks if move is legal
                myBoard.movePiece(srow, scol, erow, ecol); //moves piece if it's legal from starting to ending positions
                piece.promotePawn(erow, isBlack); //if piece makes it to end of the row, it prompts user to promote the pawn to any of the given options
                if (piece.getIsBlack() == true) {
                    isBlack = false; // switches player
                } else {
                    isBlack = true;
                }
            }
            else {
                System.out.println("Invalid move. Please try again.");
            }

        }
    }
}

