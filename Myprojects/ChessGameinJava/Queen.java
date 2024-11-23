// written by Zuhur Hashi, Hashi136 && Yusra Hersi, Hersi032

public class Queen {
    private int row;
    private int col;
    private boolean isBlack;

    public Queen(int row, int col, boolean isBlack) {
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        if (board.verifySourceAndDestination(this.row, this.col, endCol, endCol, this.isBlack)){
            // calls the verifysourceAndDestination method to check the source and destination of the piece
            if (board.verifyHorizontal(this.row, this.col, endRow, endCol)
                    || (board.verifyVertical(this.row, this.col, endRow, endCol))
                    || (board.verifyDiagonal(this.row, this.col, endRow, endCol))
                    || (board.verifyAdjacent(this.row, this.col, endRow, endCol))) {
                // calls the verify diagonal, vertical, adjacent, or horizontal methods to see if the move can be completed depending on user input

                return true;
            }
        }
        return false;
    }
}
