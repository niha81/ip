package woogie.ui;

import java.util.Scanner;

/**
 * Handles all user interactions, including input and output.
 * Manages user prompts, messages, and errors.
 */
public class Ui {
    /** Scanner object for reading user input. */
    private Scanner scanner;

    /**
     * Initializes the user interface.
     * Creates a scanner to read user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Returns the chatbot's greeting message.
     */
    public String getGreeting() {
        return "* Greetings! I'm Woogie *･ﾟ✧\n" + " How can I help you?";
    }

    /**
     * Returns the chatbot's farewell message.
     */
    public String getGoodbye() {
        return "It pains me to have to part ways ૮(ㅠ-ㅠ).\nHope to see you again soon!";
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to be displayed.
     */
    public String returnMessage(String message) {
        return message;
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public String returnError(String message) {
        return "Booooo!!\n" + message;
    }
}
