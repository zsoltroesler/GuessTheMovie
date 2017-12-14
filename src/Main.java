
public class Main {

    public static void main (String[] args) throws Exception{
        // Create a new game object
        Game game = new Game();

        // Get a new random movie title
        game.getGuessTitle();

        // Handle the user's input
        game.checkCharacter();
    }
}
