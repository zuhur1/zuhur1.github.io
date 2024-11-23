// written by Zuhur Hashi, Hashi136 && Yusra Hersi, Hersi032

public class King {
    // king can only move one space in each direction
    private int row;
    private int col;
    private boolean isBlack;

    public King(int row, int col, boolean isBlack) {
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        if (board.verifySourceAndDestination(this.row, this.col, endRow, endCol, this.isBlack)) {
            //checks if the source and destination of move is valid
            if (board.verifyAdjacent(this.row, this.col, endRow, endCol)) {
                //calls verify adjacent to check if the king to move adjacently depending on starting and ending position
                if (board.getPiece(endRow, endCol) == null) { // checks if the endpoint is empty
                    return true;
                } else if (board.getPiece(endRow, endCol).getIsBlack() != this.isBlack) //checks if the opponent's color piece is there if not empty
                    return true;
            }
        }
        return false;

    }
}
