package woogie.command;

import woogie.list.TaskList;
import woogie.task.Deadline;
import woogie.task.Event;
import woogie.task.Task;
import woogie.task.ToDo;
import woogie.ui.Ui;

/**
 * Handles the processing of user commands.
 * Determines the appropriate actions to take based on user input.
 */
public class Parser {
    /**
     * Processes user input and executes the corresponding command.
     *
     * @param input User input command.
     * @param tasks TaskList containing the tasks.
     * @param ui    User interface for displaying messages.
     */
    public static void processCommand(String input, TaskList tasks, Ui ui) {
        if (input.equalsIgnoreCase("bye")) {
            ui.showGoodbye();
            System.exit(0);
        } else if (input.equalsIgnoreCase("list")) {
            tasks.listTasks();
        } else if (input.startsWith("mark")) {
            tasks.markTask(input);
        } else if (input.startsWith("unmark")) {
            tasks.unmarkTask(input);
        } else if (input.startsWith("todo")) {
            addTodo(tasks, input, ui);
        } else if (input.startsWith("deadline")) {
            addDeadline(tasks, input, ui);
        } else if (input.startsWith("event")) {
            addEvent(tasks, input, ui);
        } else if (input.startsWith("delete")) {
            tasks.deleteTask(input);
        } else {
            ui.showMessage("sorry idk this command ;-;");
        }
    }

    /**
     * Adds a ToDo task to the TaskList.
     *
     * @param tasks TaskList where the task will be added.
     * @param input User input command for adding a ToDo.
     * @param ui    User interface for displaying messages.
     */
    private static void addTodo(TaskList tasks, String input, Ui ui) {
        if (input.length() <= 5 || input.substring(5).trim().isEmpty()) {
            ui.showMessage("todo's description cannot be empty, pls add one >:(");
            return;
        }
        String description = input.substring(5).trim();
        Task newTask = new ToDo(description);
        tasks.addTask(newTask);
    }

    /**
     * Adds a Deadline task to the TaskList.
     *
     * @param tasks TaskList where the task will be added.
     * @param input User input command for adding a Deadline.
     * @param ui    User interface for displaying messages.
     */
    private static void addDeadline(TaskList tasks, String input, Ui ui) {
        if (!input.contains(" /by ")) {
            ui.showMessage("you can't have a deadline without a deadline, add a /by :)");
            return;
        }

        try {
            String[] parts = input.split(" /by ", 2);
            if (parts[0].length() <= 9 || parts[0].substring(9).trim().isEmpty()) {
                throw new IllegalArgumentException("deadline's description cannot be empty, pls add one >:(");
            }
            if (parts[1].trim().isEmpty()) {
                throw new IllegalArgumentException("deadline's /by time cannot be empty!");
            }
            if (!parts[1].matches("\\d{4}-\\d{2}-\\d{2} \\d{4}")) { // Ensure correct format
                throw new IllegalArgumentException("deadline must be in yyyy-MM-dd HHmm format!");
            }

            String description = parts[0].substring(9);
            String by = parts[1];
            Task newTask = new Deadline(description, by);
            tasks.addTask(newTask);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showMessage("oop, smt went wrong with your deadline input ( ˶°o°) !!");
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Adds an Event task to the TaskList.
     *
     * @param tasks TaskList where the task will be added.
     * @param input User input command for adding an Event.
     * @param ui    User interface for displaying messages.
     */
    private static void addEvent(TaskList tasks, String input, Ui ui) {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            ui.showMessage("i need to know when your event starts and ends,\n" + "pls add both a /from and /to :<");
            return;
        }

        try {
            String[] firstSplit = input.split(" /from ", 2);
            if (firstSplit.length < 2 || firstSplit[0].substring(6).trim().isEmpty()) {
                throw new IllegalArgumentException("event's description cannot be empty, pls add one >:(");
            }

            String description = firstSplit[0].substring(6).trim();
            String[] secondSplit = firstSplit[1].split(" /to ", 2);
            if (secondSplit.length < 2 || secondSplit[0].trim().isEmpty() || secondSplit[1].trim().isEmpty()) {
                throw new IllegalArgumentException("event's /from and /to times cannot be empty!");
            }

            String from = secondSplit[0].trim();
            String to = secondSplit[1].trim();

            if (!from.matches("\\d{4}-\\d{2}-\\d{2} \\d{4}") || !to.matches("\\d{4}-\\d{2}-\\d{2} \\d{4}")) {
                throw new IllegalArgumentException("event times must be in yyyy-MM-dd HHmm format!");
            }

            Task newTask = new Event(description, from, to);
            tasks.addTask(newTask);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showMessage("oop, smt went wrong with your event input ( ˶°o°) !!");
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }
}
