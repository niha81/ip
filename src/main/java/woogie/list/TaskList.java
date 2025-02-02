package woogie.list;

import woogie.task.Task;
import woogie.ui.Ui;

import java.util.ArrayList;

/**
 * Manages the list of tasks in Woogie.
 * Handles adding, deleting, marking, unmarking, and listing tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private Ui ui;

    /**
     * Initializes a TaskList with a preloaded list of tasks.
     *
     * @param loadedTasks An ArrayList of tasks loaded from storage.
     */
    public TaskList(ArrayList<Task> loadedTasks) {
        this.tasks = loadedTasks;
        this.ui = new Ui();
    }

    /**
     * Initializes an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        this.ui = new Ui();
    }

    /**
     * Retrieves the current list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Adds a task to the task list and notifies the user.
     *
     * @param newTask The task to be added.
     */
    public void addTask(Task newTask) {
        tasks.add(newTask);
        ui.showMessage("Oki. I've added this task:\n  " + newTask + "\nNow you have " + tasks.size()
                + " tasks in the list (੭˃ᴗ˂)੭.");
    }

    /**
     * Deletes a task from the list based on user input.
     *
     * @param input The user's delete command, expected format: "delete X" where X is task index.
     */
    public void deleteTask(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            ui.showMessage("INVALID! Pls specify task number :)");
            return;
        }

        try {
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            Task rem = tasks.remove(taskIndex);
            ui.showMessage("Noted. I've removed this task:\n  " + rem + "\nNow you have " + tasks.size()
                    + " tasks in the list ٩>ᴗ<)و.");
        } catch (NumberFormatException e) {
            ui.showError("woogie.task.Task number must be a valid number :)");
        } catch (IndexOutOfBoundsException e) {
            ui.showMessage("INVALID! pls choose a task between 1 and " + tasks.size() + "  (•̀⤙•́ ).");
        }
    }

    /**
     * Marks a task as done based on user input.
     *
     * @param input The user's mark command, expected format: "mark X" where X is task index.
     */
    public void markTask(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            ui.showMessage("INVALID! Pls specify task number :)");
            return;
        }
        try {
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            tasks.get(taskIndex).markDone();
            ui.showMessage("Nice! I've marked this task as done:\n  " + tasks.get(taskIndex));
        } catch (NumberFormatException e) {
            ui.showError("woogie.task.Task number must be a valid number :)");
        } catch (IndexOutOfBoundsException e) {
            ui.showMessage("INVALID! pls choose a task between 1 and " + tasks.size() + " (•̀⤙•́ ).");
        }
    }

    /**
     * Marks a task as not done based on user input.
     *
     * @param input The user's unmark command, expected format: "unmark X" where X is task index.
     */
    public void unmarkTask(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            ui.showMessage("INVALID! Pls specify task number :)");
            return;
        }

        try {
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            tasks.get(taskIndex).markUndone();
            ui.showMessage("Ok, I've marked this task as not done yet:\n  " + tasks.get(taskIndex));
        } catch (NumberFormatException e) {
            ui.showError("woogie.task.Task number must be a valid number :)");
        } catch (IndexOutOfBoundsException e) {
            ui.showMessage("INVALID! pls choose a task between 1 and " + tasks.size() + " (•̀⤙•́ ).");
        }
    }

    /**
     * Lists all tasks currently in the task list.
     * Displays a message if the task list is empty.
     */
    public void listTasks() {
        if (tasks.isEmpty()) {
            ui.showMessage("nothing here yet TT");
        } else {
            ui.showLine();
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
            ui.showLine();
        }
    }
}
