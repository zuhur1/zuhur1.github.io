// written by Zuhur Hashi, Hashi136 && Yusra Hersi, Hersi032
public class Bishop {
    //if it starts on white, it can only move diagonaly across light squares, and vice versa
    private int row;
    private int col;
    private boolean isBlack;

    public Bishop(int row, int col, boolean isBlack) {
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        if (board.verifySourceAndDestination(this.row, this.col, endCol, endCol, this.isBlack)) {
            // calls the verifysourceAndDestination method to check the source and destination of the piece
            if (board.verifyDiagonal(this.row, this.col, endRow, endCol)) {
                // calls the verify diagonal method to see if the diagonal move can be completed
                if (board.getPiece(endRow, endCol) == null) { // checks if the endpoint is empty
                    return true;
                } else if (board.getPiece(endRow, endCol).getIsBlack() != this.isBlack) { //checks if the opponent's color piece is there if not empty
                    return true;
                }
            }
        }
        return false;
    }
}

