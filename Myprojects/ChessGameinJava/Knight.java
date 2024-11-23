public class Knight {
    // Instance variables
    private int row;
    private int col;
    private boolean isBlack;
    public Knight(int row, int col, boolean isBlack) {
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        if (board.verifySourceAndDestination(this.row, this.col, endCol, endCol, this.isBlack)) {
            if ((Math.abs(this.row - endRow) == 1) && Math.abs(this.col - endCol) ==2
                    || (Math.abs(this.row - endRow) == 2) && Math.abs(this.col - endCol) ==1) {
                        return true;
            }
        }
        return false;
    }

}
