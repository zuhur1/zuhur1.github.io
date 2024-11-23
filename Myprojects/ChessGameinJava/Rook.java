// written by Zuhur Hashi, Hashi136 && Yusra Hersi, Hersi032
public class Rook {
    private int row;
    private int col;
    private boolean isBlack;
//can only go left, right, up, down, cant move over its own peices

    public Rook(int row, int col, boolean isBlack) {
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        if (board.verifySourceAndDestination(this.row, this.col, endCol, endCol, this.isBlack)) {
            // calls the verifysourceAndDestination method to check the source and destination of the piece
            if (board.verifyHorizontal(this.row, this.col, endRow, endCol)) {
                // calls verifyhorizontal to check if horizontal move can be completed
                if (board.getPiece(endRow, endCol) == null) {  // checks if the endpoint is empty
                    return true;
                }
            } else if (board.getPiece(endRow, endCol).getIsBlack() != this.isBlack) //checks if the opponent's color piece is there if not empty
                return true;
            if (board.verifyVertical(this.row, this.col, endRow, endCol)) {
                // calls verifyvertical to check if vertical move can be completed

                if (board.getPiece(endRow, endCol) == null) { // checks if the endpoint is empty
                    return true;
                }
            } else if (board.getPiece(endRow, endCol).getIsBlack() != this.isBlack) //checks if the opponent's color piece is there if not empty
                return true;
        }
        return false;

    }
}

