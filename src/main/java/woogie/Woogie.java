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
     * Runs the chatbot by processing user commands in a loop.
     * Saves tasks after each command execution.
     */
    public void run() {
        ui.showGreeting();
        while (true) {
            String input = ui.readCommand().trim();
            if (input.equalsIgnoreCase("bye")) {
                ui.showGoodbye();
                storage.saveTasks(tasks.getTasks());
                break;
            }
            Parser.processCommand(input, tasks, ui);
            storage.saveTasks(tasks.getTasks());
        }
    }

    public String getResponse(String input) {
        if (input.equalsIgnoreCase("bye")) {
            storage.saveTasks(tasks.getTasks());
            return ui.getGoodbye();
        }

        return Parser.processCommandWithResponse(input, tasks, ui);
    }

    /**
     * Starts the Woogie chatbot.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Woogie().run();
    }
}
