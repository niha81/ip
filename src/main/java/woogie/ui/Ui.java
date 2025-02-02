package woogie.ui;

import java.util.Scanner;

/**
 * Handles all user interactions, including input and output.
 * Manages user prompts, messages, and errors.
 */
public class Ui {
    /** Scanner object for reading user input. */
    private Scanner scanner;
    /** Constant line separator for formatting messages. */
    private static final String LINE = "\n---------------------------------------------------------------\n";

    /**
     * Initializes the user interface.
     * Creates a scanner to read user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the chatbot's greeting message.
     */
    public void showGreeting() {
        System.out.println(LINE + "* Greetings! I'm Woogie *･ﾟ✧\n" + "How can I help you?" + LINE);
    }

    /**
     * Displays the chatbot's farewell message.
     */
    public void showGoodbye() {
        System.out.println(LINE + "It pains me to have to part ways ૮(ㅠ-ㅠ).\nHope to see you again soon!" + LINE);
    }

    /**
     * Prints a horizontal line separator.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Reads and returns the user's input command.
     *
     * @return The user's input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to be displayed.
     */
    public void showMessage(String message) {
        System.out.println(LINE + message + LINE);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        System.out.println(LINE + "OOP! smt went wrong:\n" + message + LINE);
    }
}
