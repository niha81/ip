package woogie;

import woogie.command.Parser;
import woogie.list.TaskList;
import woogie.storage.Storage;
import woogie.ui.Ui;

/**
 * The main entry point for the Woogie chatbot.
 * Processes user input and manages tasks.
 */
public class Woogie {
    /** File path where tasks are stored. */
    private static final String FILE_PATH = "./data/woogie.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initializes the Woogie chatbot.
     * Loads saved tasks and prepares for user input.
     */
    public Woogie() {
        ui = new Ui();
        storage = new Storage(FILE_PATH);
        tasks = new TaskList(storage.loadTasks());

        assert tasks != null : "TaskList should not be null after loading tasks!";
    }

    /**
     * Processes user input and generates a response.
     * If the input is "bye", tasks are saved before returning the goodbye message.
     * Otherwise, it delegates command processing to the Parser.
     *
     * @param input The user's input command.
     * @return The response message from Woogie.
     */
    public String getResponse(String input) {
        return Parser.processCommandWithResponse(input, tasks, ui, storage);
    }
}
