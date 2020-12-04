import java.util.Random;
import static java.lang.Math.*;

/**
 * This Class represents the gameboard for a game of Snakes & Ladders
 * @author David Steiger
 */
public class Gameboard {
    /**
     * Holds the board as an array of Squares
     */
    protected Square[] field;

    /**
     * Create a Gameboard of desired size and print to the layout to console
     *
     * @param amount The number of desired squares
     * @void
     */
    public Gameboard(final int numberOfSquares) {
        // Initialize field of squares
        this.field = new Square[numberOfSquares];
        // Populate Gameboard
        initGameboard(calculateNumberOfSnakesAndLadders(numberOfSquares));
        // Print Gameboard
        System.out.println(this);
    }

    /**
     * Initialize Gameboard with given ammount of Snake & Ladder Squares
     *
     * @param amount The number of desired Snake & Ladder Squares
     * @void
     */
    private void initGameboard(final int amount) {
        int snakesAndLadders = in ;
        //Shuffle squares (without start and end, which should be fixated)
        final int[] shuffledSquares = shuffleArray(2, field.length - 1);
        // Assign fields that are going to be snakes or ladders
        int assignedFields = 0;
        while (snakesAndLadders > 0) {
            // Get start and end square of Snake/Ladder
            final int start = min(shuffledSquares[assignedFields], shuffledSquares[assignedFields + 1]);
            final int end = max(shuffledSquares[assignedFields], shuffledSquares[assignedFields + 1]);
            //alternate snakes and ladder creation
            //create snake square + normal square
            if (snakesAndLadders % 2 == 1) {
                //Snakes have inverted start/end
                field[end - 1] = new SnakeSquare(end, end - start);
                field[start - 1] = new NormalSquare(start);
            }
            //create ladder square + normal square
            else {
                field[start - 1] = new LadderSquare(start, end - start);
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

    /**
     * Calculate a good amount of Snake and Ladder fields given the board size
     *
     * @param numberOfSquares The total number of squares of the field
     * @return A good amount of Snake and Ladder fields
     */
    private int calculateNumberOfSnakesAndLadders(final int numberOfSquares) {
        return (numberOfSquares - 2) / 3;
    }

    /**
     * Shuffle and return an array such that each Gameboard is random
     *
     * @param start index
     * @param end index
     * @return A shuffled array, containing integers from start to end in 1 step intervals
     */
    private int[] shuffleArray(final int start, final int end) {
        final Random rgen = new Random();
        final int size = end - start + 1;
        int[] array = new int[size];
        // Create an array counting from a to b in 1 step intervals
        for (int i = 0; i < size; i++) {
            array[i] = start + i;
        }
        //Shuffle this array
        for (int i = 0; i < array.length; i++) {
            final int randomPosition = rgen.nextInt(array.length);
            final int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
        return array;
    }

    /**
     * Override toString() for printing the gameboard
     *
     * @return String that represents the layout of the gameboard
     */
    @Override
    public String toString() {
        String output;
        // Greeting
        output += "Let the game begin! Your board looks like this: \n";
        for (final Square s: field) {
            // Every square type overrides print function differently
            output += s.toString();
        }
        return output;
    }
}