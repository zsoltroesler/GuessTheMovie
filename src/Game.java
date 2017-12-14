import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {

    // Fields:
    private File file;
    private Scanner scanner;
    private List<String> movieList = new ArrayList<>();
    private List<Character> wrongLetters = new ArrayList<>();
    private String movieTitle;
    private String guessTitle;
    private int randomIndex;
    private char inputLetter;
    private int wrongLetter;
    private boolean correctChar;
    private  StringBuilder sb;

    /**
     * Empty constructor
     */
    public Game() {
    }

    /**
     * This method give us the random movie from the movies.txt file
     * @return guessTitle in _____ format
     * @throws FileNotFoundException
     */
    public String getGuessTitle() throws FileNotFoundException {

        // Scanner object initialized taking file az input argument
        try {
            file = new File("movies.txt");
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                movieList.add(line);
            }
        } catch (FileNotFoundException exception) {
            System.out.println("File is missing!");
        }
        scanner.close();

        // Pick a random movieTitle from ArrayList<> between 0 index and size of ArrayList<>
        randomIndex = (int) (Math.random() * movieList.size());

        // Get a movieTitle according to randomIndex and convert it to lower cases
        movieTitle = movieList.get(randomIndex).toLowerCase();

        // Create the guessTitle from movieTitle with replacing all characters with "_" and eliminating leading
        // and trailing spaces
        guessTitle = movieTitle.replaceAll("[A-Za-z0-9]", "_").trim();

        return guessTitle;
    }

    /**
     * This method checks the user's input character whether the movie title contains it or not.
     * And furthermore the winning/losing logic
     */
    public void checkCharacter() {

        System.out.println("You are guessing: " + guessTitle);
        System.out.println("You have guessed (" + wrongLetter + ") wrong letters: " +
                Arrays.toString(wrongLetters.toArray()).replace("[", "").replace("]", ""));
        System.out.println("Guess a letter:");

        // Initialize correctChar to false
        correctChar = false;

        // Set up a new scanner which reads the user´s input
        scanner = new Scanner(System.in);
        inputLetter = scanner.next().toLowerCase().charAt(0);

        // Create a new StringBuilder which takes guessTitle as input parameter
        sb = new StringBuilder(guessTitle);

        // Identify in which index the user's input letter can be found
        int index = movieTitle.indexOf(inputLetter);

        // If index == -1 means the title does not contain this letter, if != -1 then the while loop checks the whole title
        // and retrieves all indexes, where the letter appears.
        if (index == -1) {
            if(wrongLetters.contains(inputLetter)){
                System.out.println("This letter was already used.");
            } else {
                wrongLetters.add(inputLetter);
                wrongLetter++;
            }
        } else { correctChar = true;
            while (index >= 0) {
                sb.setCharAt(index, inputLetter);
                index = movieTitle.indexOf(inputLetter, index + 1);
            }
            guessTitle = sb.toString();
        }

        if (correctChar) {
            System.out.println("Correct.");
            if (guessTitle.indexOf('_') < 0) {
                System.out.println("Congratulation! You won! The movie was: " + movieTitle);
                return;
            }
        } else {
            System.out.println("Incorrect.");
            if (wrongLetter == 10) {
                System.out.println("Sorry you lost. The movie was: " + movieTitle);
                return;
            }
        }
        checkCharacter();
    }
}

