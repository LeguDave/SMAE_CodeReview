import java.util.Random;
import static java.lang.Math.*;


// This Class represents the gameboard for a game of Snakes & Ladders
// Holds the board as an array of Squares with distributed Snakes, Ladders, Normal Squares, Start/End Squares
public class Gameboard {
    // Holds the field of Squares
    Square[] field;
    //Constructor
    public Gameboard(int numberOfSquares) {
        // Initialize field of squares
        this.field = new Square[numberOfSquares];
        // Populate Gameboard
        initGameboard(calculateNumberOfSnakesAndLadders(numberOfSquares));
        // Print Gameboard
        System.out.println(this);
    }

    // Initialize Gameboard with given ammount of Snake & Ladder Squares
    private void initGameboard(int snakesAndLadders) {
        //Shuffle squares (without start and end, which should be fixated)
        int[] shuffledSquares = shuffleArray(2, field.length - 1);
        // Assign fields that are going to be snakes or ladders
        int assignedFields = 0;
        while (snakesAndLadders > 0) {
            // Get start and end square of Snake/Ladder
            int start;
            int end;
            start = Math.min(shuffledSquares[assignedFields], shuffledSquares[assignedFields + 1])
            end = Math.max(shuffledSquares[assignedFields], shuffledSquares[assignedFields + 1])
            //alternate snakes and ladder creation
            //create snake square + normal square
            if (snakesAndLadders % 2 == 1) {
                //Snakes have inverted start/end
                field[end - 1] = new SnakeSquare(end, (end - start));
                field[start - 1] = new NormalSquare(start);
            }
            //create ladder square + normal square
            else {
                field[start - 1] = new LadderSquare(start, (end - start));
                field[end - 1] = new NormalSquare(end);
            }
            // Increase indices
            assignedFields = assignedFields + 2;
            snakesAndLadders = snakesAndLadders - 1;
        }
        // Assign the rest as normal squares
        while (assignedFields < field.length - 2) {
            field[shuffledSquares[assignedFields] - 1] = new NormalSquare(shuffledSquares[assignedFields]);
            assignedFields = assignedFields + 1;
        }
        //create start square
        field[0] = new StartSquare();
        //create end square
        field[field.length - 1] = new EndSquare(field.length);
    }

    //
    // Helpers
    //

    // Calculate a good amount of Snake and Ladder fields given the board size
    private int calculateNumberOfSnakesAndLadders(int numberOfSquares) {
        return (numberOfSquares - 2) / 3;
    }

    // Shuffle and return an array such that each Gameboard is random
    private int[] shuffleArray(int a, int b) {
        Random rgen = new Random();
        int size = b - a + 1;
        int[] array = new int[size];
        // Create an array counting from a to b in 1 step intervals
        for (int i = 0; i < size; i++) {
            array[i] = a + i;
        }
        //Shuffle this array
        for (int i = 0; i < array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
        return array;
    }

    // Print the Gameboard
    @Override
    public String toString() {
        String output;
        // Greeting
        output += "Let the game begin! Your board looks like this: \n";
        for (Square s: field) {
            // Every square type overrides print function differently
            System.out.println(s)
        }
        return output
    }
}