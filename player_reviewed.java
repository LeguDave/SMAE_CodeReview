/**
 * This Class represents the player piece for a game of Snakes & Ladders
 *
 * @author David Steiger
 */
public class player {
    /**
     * Holds the position of the player
     */
    int position;
    /**
     * Holds the name of the player
     */
    String name;

    /**
     * Initialize a player with the desired name and at position 1 on the gameboard
     *
     * @param name Desired name of Player
     * @void
     */
    public player(String name) {
        this.name = name;
        this.position = 1;
    }

    /**
     * Move the player on the gameboard
     *
     * @param steps The result of a dice roll
     * @param gameboard The gameboard the player is playing on
     * @void
     */
    public void move(int steps, Gameboard gameboard) {
        // Free the square the player is on before moving
        gameboard.field[this.position - 1].occupied = false;
        // Board Underflow protection
        if (this.position + steps < 1) {
            this.position = 1;
            return;
        }
        // Board Overflow protection
        if (this.position + steps > gameboard.squares) {
            this.move((((gameboard.squares - this.position) * 2) - steps), gameboard);
            return;
        }
        // Depending on the square the player lands on, we move again
        Square futurePosition = gameboard.field[this.position + steps - 1];
        // Checks if snake
        if (futurePosition.type == "snake_square") {
            this.position += steps;
            System.out.println("Oh no, you landed on a snake! \n");
            this.move(((snake_square)(futurePosition)).steps_back * (-1), gameboard);
            return;
        }
        // Checks if ladder
        else if (futurePosition.type == "ladder_square") {
            this.position += steps;
            System.out.println("Good job, you landed on a ladder! \n");
            this.move(((ladder_square)(futurePosition)).steps_forward, gameboard);
            return;
        }
        // Normal square
        else {
            this.position += steps;
        }
        // Checks if landing on occupied field
        newPosition = gameboard.field[this.position-1]
        if (newPosition.type == "normal_square" || newPosition.type == "end_square") {
            // If player x lands on another player y, player x returns to field 1
            if (newPosition.occupied == true) {
                this.position = 1;
                System.out.println("Oh no, you landed on another player! Back to square one! \n");
            }
            // Else the player occupies the field he/she moved on
            else {
                newPosition.occupied = true;
            }
        }
        return;
    }

}
