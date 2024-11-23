import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;


public class main {
    public static void main(String[] args) {
        boolean debug = false;
        Scanner scanner = new Scanner(System.in);
        // the following print statements describe the different level and ask the user to select a level
        System.out.println("Pick a level:" + "\n" + "Easy (5 x 5) with 5 flags and Mines" + "\n" + "Medium (9 x 9) with 9 mines and Flags" + "\n" + "Hard (20 x 20) with 40 flags and mines");
        System.out.println("For Easy: Enter 1" +"\n"+ "For Medium: Enter 2" +"\n"+ "For Hard: Enter 3");
        int input = Integer.parseInt(scanner.nextLine());
        System.out.println("would you like to play is debug mode? Please enter Yes or No");
        String debugUser = scanner.nextLine();
        Minefield minefield;

        if (debugUser.equalsIgnoreCase("Yes")){
            debug = true;
        }
        int mineNum;
        if(input == 1) {
            // creates a minefield with easy level conditions if the user selects easy level
            minefield = new Minefield(5, 5, 5);
            mineNum = 5;

        }
        else if (input == 2){
            // creates a minefield with medium level conditions if the user selects medium level
            minefield = new Minefield(9, 9, 12);
            mineNum = 12;

        }
        else{
            // if the user don't select either 1 or 2 or user selects hard level, minefield with hard level conditions is created
            minefield = new Minefield(20, 20, 40);
            mineNum = 40;

        }
        System.out.println("Choose a position and input it's position number(x) SPACE number(y)");
        String userGuess = scanner.nextLine();
        // takes in coordinates of user starting position
        String [] userGuess2 = userGuess.split(" ");
        int x = Integer.parseInt(userGuess2[0]);
        int y = Integer.parseInt(userGuess2[1]);
        minefield.createMines(x,y ,mineNum);
        // creates mines avoiding the starting position
        minefield.evaluateField();
        minefield.revealStartingArea(x, y);
        // reveals starting area given starting coordinates
        boolean guess = false;
        while(! minefield.gameOver()  ) {
            System.out.println(minefield);
            if (debug == true) {
                minefield.debug();
                // if debug mode was selected by user, prints debug mode everytime user makes a guess
            }
            System.out.println("Choose a position and input it's position number(x) SPACE number(y)");
            userGuess = scanner.nextLine();
            userGuess2 = userGuess.split(" ");
            x = Integer.parseInt(userGuess2[0]);
            y = Integer.parseInt(userGuess2[1]);
            System.out.println("Do you think there is a mine located there, YES or NO");
            String mineorNot = scanner.nextLine();
            // Asks user for coordinate of cell they want to guess and if they guess there is a mine there
            if (mineorNot.equalsIgnoreCase("no")) {
                guess = minefield.guess(x, y, false);
                // if user guesses there is no mine there, cell will not be flagged and guess is placed
            } else {
                guess = minefield.guess(x, y, true);
                // if user guesses there is a mine there, cell is flagged and guess is place
            }
            if (guess == true) {
                System.out.println("Game over! You lost!!!");
                // if the user guesses a mine AND didn't flag the cell, loses game and game is over
                // breaks the while loop
                break;
            }
        }
    }
}




