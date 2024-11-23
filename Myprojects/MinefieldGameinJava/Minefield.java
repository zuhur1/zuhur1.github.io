import java.util.Queue;
import java.util.Random;

public class Minefield {
    /**
     * Global Section
     */
    public static final String ANSI_YELLOW_BRIGHT = "\u001B[33;1m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_PURPLE = "\u001b[35m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001b[47m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001b[45m";
    public static final String ANSI_GREY_BACKGROUND = "\u001b[0m";

    /*
     * Class Variable Section
     *
     */

    /*Things to Note:
     * Please review ALL files given before attempting to write these functions.
     * Understand the Cell.java class to know what object our array contains and what methods you can utilize
     * Understand the StackGen.java class to know what type of stack you will be working with and methods you can utilize
     * Understand the QGen.java class to know what type of queue you will be working with and methods you can utilize
     */

    /**
     * minefield
     * <p>
     * Build a 2-d Cell array representing your minefield.
     * Constructor
     *
     * @param rows       Number of rows.
     * @param columns    Number of columns.
     * @param flags      Number of flags, should be equal to mines
     */
    private int rows;
    private int cols;
    private int flags;
    Cell[][] minefield;

    public Minefield(int rows, int columns, int flags) {
        this.rows = rows;
        this.cols = columns;
        this.flags = flags;
        minefield = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                minefield[i][j] = new Cell(false, "-"); //following the cell contructer and initializing each row and column in the minefield as blank/false
            }
        }

    }


    public boolean inBounds(int x, int y) {
        return (x >= 0 && x < rows && y >= 0 && y < cols);
    }// helper function to check if it is within bounds



    /**
     * evaluateField
     *
     * @function: Evaluate entire array.
     * When a mine is found check the surrounding adjacent tiles. If another mine is found during this check, increment adjacent cells status by 1.
     */
    public void evaluateField() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!minefield[i][j].getStatus().equals("M")) {
                    int count = 0;
                    for (int x = i -1; x <= i + 1; x++) {
                        for (int y = j -1; y <= j+1; y++) {
                            if ((inBounds( x,  y)) && minefield[x][y].getStatus().equals("M")) {
                                count++;
//                                String status = minefield[i + x][j + y].getStatus();
//                                int stat = Integer.parseInt(status) + 1;
//                                String fin = Integer.toString(stat);
//                                minefield[i + x][j + y].setStatus(fin);
                            }
                            //get status of cell minefield[j][i]
                            //turn that string into an int
                            // increment it by 1
                            //turn it back into a string
                            // finally set status back to the string
                        }
                    }
                    minefield[i][j].setStatus(Integer.toString(count));

                }

            }
        }
    }


    /**
     * createMines
     * <p>
     * Randomly generate coordinates for possible mine locations.
     * If the coordinate has not already been generated and is not equal to the starting cell set the cell to be a mine.
     * utilize rand.nextInt()
     *
     * @param x     Start x, avoid placing on this square.
     * @param y     Start y, avoid placing on this square.
     * @param mines Number of mines to place.
     */
    public void createMines(int x, int y, int mines) {
        int count = 0;
        for (int i = 0; count < mines; i++) {
            Random rand = new Random();
            int minex = rand.nextInt(rows);
            int miney = rand.nextInt(cols);
            if (miney != y && minex != x && (!minefield[minex][miney].getStatus().equals("M"))) {
                minefield[minex][miney].setStatus("M");
                count++;
            }
        }

    }


    /**
     * guess
     * <p>
     * Check if the guessed cell is inbounds (if not done in the Main class).
     * Either place a flag on the designated cell if the flag boolean is true or clear it.
     * If the cell has a 0 call the revealZeroes() method or if the cell has a mine end the game.
     * At the end reveal the cell to the user.
     *
     * @param x    The x value the user entered.
     * @param y    The y value the user entered.
     * @param flag A boolean value that allows the user to place a flag on the corresponding square.
     * @return boolean Return false if guess did not hit mine or if flag was placed, true if mine found.
     */
    public boolean guess(int x, int y, boolean flag) {
        if(x < cols && y < rows){
            if(flag == true){
                if(this.flags > 0){
                    minefield[x][y].setStatus("F");
                    minefield[x][y].setRevealed(true);
                    this.flags--;
                }else{
                    System.out.println("No more flags to use! Try again!");
                }
            }else{
                if(minefield[x][y].getStatus().equals("0")){
                    revealZeroes(x, y);
                }else if (minefield[x][y].getStatus().equals("M")){
                    minefield[x][y].setRevealed(true);
                    return true;
                }else{
                    minefield[x][y].setRevealed(true);
                }
            }
            return false;
        }
        return false;
    }

    /**
     * gameOver
     * <p>
     * Ways a game of Minesweeper ends:
     * 1. player guesses a cell with a mine: game over -> player loses
     * 2. player has revealed the last cell without revealing any mines -> player wins
     *
     * @return boolean Return false if game is not over and squares have yet to be revealed, otheriwse return true.
     */
    public boolean gameOver() {
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(minefield[i][j].getRevealed()){                 //if(minefield[i][j].getRevealed() == false){
                    return false;
                }
            }
        }return true;
    }

    /**
     * Reveal the cells that contain zeroes that surround the inputted cell.
     * Continue revealing 0-cells in every direction until no more 0-cells are found in any direction.
     * Utilize a STACK to accomplish this.
     * <p>
     * This method should follow the psuedocode given in the lab writeup.
     * Why might a stack be useful here rather than a queue?
     *
     * @param x The x value the user entered.
     * @param y The y value the user entered.
     */
    public void revealZeroes(int x, int y) {
        minefield[x][y].setRevealed(true);
        Stack1Gen<int[]> zeros = new Stack1Gen<>();
        zeros.push(new int[]{x, y});
        while (!zeros.isEmpty()) {
            int[] coords = zeros.pop();
            if ((minefield[coords[0]][coords[1]].getStatus().equals("0"))) {
                minefield[coords[0]][coords[1]].setRevealed(true);
                if ( inBounds(coords[0]+1, coords[1])&& !minefield[coords[0] + 1][coords[1]].getRevealed() && minefield[coords[0] + 1][coords[1]].getStatus().equals("0")) {
                    zeros.push(new int[]{coords[0] + 1, coords[1]});
                }
                if (inBounds(coords[0]-1, coords[1])&& !minefield[coords[0] - 1][coords[1]].getRevealed() && minefield[coords[0] - 1][coords[1]].getStatus().equals("0")) {
                    zeros.push(new int[]{coords[0] - 1, coords[1]});
                }
                if (inBounds(coords[0], coords[1]+1)&& !minefield[coords[0]][coords[1] + 1].getRevealed() && minefield[coords[0]][coords[1]+ 1].getStatus().equals("0")) {
                    zeros.push(new int[]{coords[0], coords[1] + 1});
                }
                if (inBounds(coords[0], coords[1]-1)&& !minefield[coords[0]][coords[1] - 1].getRevealed() && minefield[coords[0]][coords[1]-1].getStatus().equals("0")) {
                    zeros.push(new int[]{coords[0], coords[1] - 1});
                }

                }
            }

        }

    /**
     * revealStartingArea
     * <p>
     * On the starting move only reveal the neighboring cells of the inital cell and continue revealing the surrounding concealed cells until a mine is found.
     * Utilize a QUEUE to accomplish this.
     * <p>
     * This method should follow the psuedocode given in the lab writeup.
     * Why might a queue be useful for this function?
     *
     * @param x The x value the user entered.
     * @param y The y value the user entered.
     */
    public void revealStartingArea(int x, int y) {
        Q1Gen<int[]> que = new Q1Gen<>();
        int[] start = {x, y};
        que.add(start);
        while (que.length() != 0) {
            int[] removed = que.remove();//dequeue
            int currentx = removed[0]; //x index
            int currenty = removed[1]; // y index
            minefield[currentx][currenty].setRevealed(true); //sets corresponding cell's revealed attribute to true
            if (minefield[currentx][currenty].getStatus().equals("M")){
                break;
            }
            if (inBounds(currentx-1, currenty)&&!minefield[currentx - 1][currenty].getRevealed()) { // checks each index to see if it's in bounds
                int arrayadd[] = {currentx - 1, currenty}; // adds to array that index
                que.add(arrayadd); //adds array to queue
            }
            if (inBounds(currentx+1, currenty) && !minefield[currentx + 1][currenty].getRevealed()) {
                int arrayadd[] = {currentx + 1, currenty};
                que.add(arrayadd);
            }
            if (inBounds(currentx, currenty+1) && !minefield[currentx][currenty + 1].getRevealed()) { //minefield[0] will check cols
                int arrayadd[] = {currentx, currenty + 1};
                que.add(arrayadd);
            }
            if (inBounds(currentx, currenty-1) && !minefield[currentx][currenty - 1].getRevealed()) {
                int arrayadd[] = {currentx, currenty - 1};
                que.add(arrayadd);
            }

        }
    }

        /**
         * For both printing methods utilize the ANSI colour codes provided!
         *
         *
         *
         *
         *
         * debug
         *
         * @function This method should print the entire minefield, regardless if the user has guessed a square.
         * *This method should print out when debug mode has been selected.
         */
        public void debug() {
            StringBuilder out = new StringBuilder();
            out.append("  ");
            for (int j = 0; j < cols; j++) {
                out.append(j + " ");
            }
            out.append("\n");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (j == 0) {
                        out.append(ANSI_GREY_BACKGROUND+ i + " ");
                    }
                        if (minefield[i][j].getStatus().equals("M")) {
                            out.append(ANSI_RED + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND); //012345
                        }
                        else if (minefield[i][j].getStatus().equals("0")) {
                            out.append(ANSI_PURPLE + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                        else if (minefield[i][j].getStatus().equals("1")) {
                            out.append(ANSI_GREEN + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                        else if (minefield[i][j].getStatus().equals("2")) {
                            out.append(ANSI_BLUE + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                        else if (minefield[i][j].getStatus().equals("3")) {
                            out.append(ANSI_YELLOW + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                        else if (minefield[i][j].getStatus().equals("4")) {
                            out.append(ANSI_BLUE_BRIGHT + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                        else if (minefield[i][j].getStatus().equals("5")) {
                            out.append(ANSI_CYAN + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                        else if(minefield[i][j].getStatus().equals("F")){
                            out.append(ANSI_RED + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                }
                out.append("\n");
            }
            System.out.println(out);
        }


        /**
         * toString
         *
         * @return String The string that is returned only has the squares that has been revealed to the user or that the user has guessed.
         */
        public String toString () {
            StringBuilder out = new StringBuilder();
            out.append("  ");
            for (int j = 0; j < cols; j++) {
                out.append(j + " ");
            }
            out.append("\n");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (j == 0) {

                        out.append(ANSI_GREY_BACKGROUND+ i + " ");
                    }
                    if (minefield[i][j].getRevealed() == true) {
                        if (minefield[i][j].getStatus().equals("M")) {
                            out.append(ANSI_RED + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND); //012345
                        }
                        else if (minefield[i][j].getStatus().equals("0")) {
                            out.append(ANSI_PURPLE + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                        else if (minefield[i][j].getStatus().equals("1")) {
                            out.append(ANSI_GREEN + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                        else if (minefield[i][j].getStatus().equals("2")) {
                            out.append(ANSI_BLUE + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                        else if (minefield[i][j].getStatus().equals("3")) {
                            out.append(ANSI_YELLOW + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                        else if (minefield[i][j].getStatus().equals("4")) {
                            out.append(ANSI_BLUE_BRIGHT + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                        else if (minefield[i][j].getStatus().equals("5")) {
                            out.append(ANSI_CYAN + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                        else if (minefield[i][j].getStatus().equals("F")) {
                            out.append(ANSI_RED_BRIGHT + minefield[i][j].getStatus() + " " + ANSI_GREY_BACKGROUND);
                        }
                    } else {
                        out.append(ANSI_GREY_BACKGROUND + "-");
                        out.append(" ");

                    }
                }
                out.append("\n");
            }

            return out.toString();

        }
}

