// written by Zuhur Hashi, Hashi136 && Yusra Hersi, Hersi032

public class Board {
    private int row;
    private int col;
    // Instance variables
    private Piece[][] board;

    //TODO:
    // Construct an object of type Board using given arguments.
    public Board() {
        this.board = new Piece[8][8];
    }

    // Accessor Methods

    // Return the Piece object stored at a given row and column
    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    // Update a single cell of the board to the new piece.
    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }


    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
        if (board[startRow][startCol] != null) {
            //checks that the starting position is not null
            if (board[startRow][startCol].isMoveLegal(this, endRow, endCol)) {
                //checks if the move from the starting position to ending position is legal
                board[endRow][endCol] = board[startRow][startCol];
                // sets the location of the piece at the starting position to ending position
                board[startRow][startCol].setPosition(endRow, endCol);
                // sets the position
                board[startRow][startCol] = null;
                // sets the starting position to null after the move is complete
            }
        }
        return true;
    }
    //return false

    public boolean isGameOver() {
        // loops through the whole board to check the count of kings
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != null){
                    if (this.board[i][j].toString().equals("\u265a")) {
                        // if the black king piece is on the board, count increases
                        count++;
                    }
                    if (this.board[i][j].toString().equals("\u2654")) {
                        // if the white king piece is on the board, count increases
                        count++;
                    }
                }
            }
        }
        String winner;
        if (count == 1) {
            // if the count is 1 and the game is over, loops through the board again to determine which player won
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] != null){
                        if (this.board[i][j].toString().equals("\u265a")) {
                            winner = "Game Over! Black Player won";
                            System.out.println(winner);
                        }
                        if (this.board[i][j].toString().equals("\u2654")) {
                            winner = "Game Over! White Player won";
                            System.out.println(winner);
                        }
                    }
                }
            }
            return true;
        }
     return false;
    }


    public String toString() { // prints a string version of the board since the toString method prints a string version of object
        StringBuilder out = new StringBuilder();
        out.append(" ");
        for (int i = 0; i < 8; i++) {
            out.append(" ");
            out.append(i);
        }
        out.append('\n');
        for (int i = 0; i < board.length; i++) { // iterates through the board and appends to new string
            out.append(i);
            out.append("|");
            for (int j = 0; j < board[0].length; j++) {
                out.append(board[i][j] == null ? "\u2001|" : board[i][j] + "|");
            }
            out.append("\n");
        }
        return out.toString();
    }


    // Sets every cell of the array to null. For debugging and grading purposes.
    public void clear() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = null;
            }
        }

    }

    public boolean verifySourceAndDestination(int startRow, int startCol, int endRow, int endCol, boolean isBlack) {
        // first if statement checks that the piece is within bounds
        if (startRow >= 0 && startRow <= 7
                && startCol >= 0 && startCol <= 7 &&
                endRow >= 0 && endRow <= 7 &&
                endCol >= 0 && endCol <= 7) {
            // seconf if checks that if the piece is in bounds that the end position is null or occupied by the opposite player
            if (board[startRow][startCol] != null &&
                    (board[endRow][endCol] == null || board[endRow][endCol].getIsBlack() != isBlack) &&
                    board[startRow][startCol].getIsBlack() == isBlack) {
                return true;
            }
        }
        return false;
    }




    public boolean verifyAdjacent(int startRow, int startCol, int endRow, int endCol) {
        //checks the pieces are adjacent to each other by checking the absolute valye of the differences of end and starting rows and end and starting col
        if (Math.abs(endRow-startRow) <= 1 && Math.abs(endCol - startCol) <= 1){
                return true;
        }
        return false;
    }


    public boolean verifyHorizontal(int startRow, int startCol, int endRow, int endCol) {
        int Minn = Math.min(startCol, endCol) + 1;
        int Max = Math.max(startCol, endCol);
        // first checks that the rows are the same
        if (startRow == endRow){
            if (startCol == endCol){
                return true;
            }
            for (int i = Minn; i < Max; i++){
                // if the rows are the same, loops through the cols in between the starting col and ending col to check that they are empty
                if (board[startRow][i] != null){
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    public boolean verifyVertical(int startRow, int startCol, int endRow, int endCol) {
        int Minn = Math.min(startRow, endRow) + 1;
        int Max = Math.max(startRow, endRow);
        // checks that the starting and ending col are the same
        if(startCol == endCol){
            // loops through the rows to check if the pieces between the starting row and ending row are the same
            for (int i = Minn; i < Max ; i++){
                if (board[i][startCol] != null){
                    return false;
                }
            } return true;
        }
        return false;
    }

    //TODO:
    // Checks whether a given 'start' and 'end' position are a valid diagonal move.
    // Returns a boolean to signify whether:
    // - The path from 'start' to 'end' is diagonal... change in row and col.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyDiagonal(int startRow, int startCol, int endRow, int endCol) {
        int Minn = Math.min(startRow, endRow) + 1;
        int Max = Math.max(startRow, endRow);
        // checks that they are diagonal by check the sum of the start row and col and end row and col
        if(startRow + startCol == endRow + endCol){
            for (int i= Minn; i < Max; i++){
                if (board[i][startCol + startRow - i] != null){
                    return false;
                }
            }
            return true;
        }
        else if (startRow - startCol == endRow - endCol){
            for (int i= Minn; i < Max; i++){
                if (board[i][startCol - startRow + i] != null){
                    return false;
                }
            }
            return true;

        } else {
            return false;
        }

    }
}
