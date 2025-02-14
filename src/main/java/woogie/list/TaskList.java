package woogie.list;
import java.util.ArrayList;

import woogie.task.Task;
import woogie.ui.Ui;

/**
 * Manages the list of tasks in Woogie.
 * Handles adding, deleting, marking, unmarking, and listing tasks.
 */
public class TaskList {
    private static final int NO_TASK_NUMBER = -1;
    private static final int INVALID_NUMBER_FORMAT = -2;
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
     * Retrieves the current list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Returns the list of tasks as a formatted string.
     *
     * @return A string representation of the task list.
     */
    public String getTaskListAsString() {
        if (tasks.isEmpty()) {
            return "nothing here yet TT";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Extracts the task index from user input.
     *
     * @param input The user's command.
     * @return The task index (0-based) or special error values.
     */

    private int extractTaskIndexFromInput(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            return NO_TASK_NUMBER;
        }

        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            return INVALID_NUMBER_FORMAT;
        }
    }

    /**
     * Marks a specified task as done and returns a response message.
     *
     * @param input The user input containing the task number.
     * @return A response message confirming the task is marked done or an error message.
     */
    public String markTaskWithResponse(String input) {
        int taskIndex = extractTaskIndexFromInput(input);
        if (taskIndex == NO_TASK_NUMBER) {
            return ui.returnError("INVALID! Pls specify task number :)");
        }
        if (taskIndex == INVALID_NUMBER_FORMAT) {
            return ui.returnError("Task number must be a valid number :)");
        }
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            return ui.returnError("INVALID! pls choose a task between 1 and " + tasks.size() + " (•⌓•).");
        }
        tasks.get(taskIndex).markDone();
        return ui.returnMessage("Nice! I've marked this task as done:\n  " + tasks.get(taskIndex) + "\n⸜( ˃ ᵕ ˂ )⸝");
    }

    /**
     * Marks a specified task as not done and returns a response message.
     *
     * @param input The user input containing the task number.
     * @return A response message confirming the task is unmarked or an error message.
     */
    public String unmarkTaskWithResponse(String input) {
        int taskIndex = extractTaskIndexFromInput(input);
        if (taskIndex == NO_TASK_NUMBER) {
            return ui.returnError("INVALID! Pls specify task number :)");
        }
        if (taskIndex == INVALID_NUMBER_FORMAT) {
            return ui.returnError("Task number must be a valid number :)");
        }
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            return ui.returnError("INVALID! pls choose a task between 1 and " + tasks.size() + " (•⌓•)");
        }
        tasks.get(taskIndex).markUndone();
        return ui.returnMessage("Nice! I've marked this task as done:\n  " + tasks.get(taskIndex) + "\n⸜( ˃ ᵕ ˂ )⸝");
    }

    /**
     * Adds a new task to the list and returns a response message.
     *
     * @param newTask The task to be added.
     * @return A response message confirming task addition.
     */
    public String addTaskWithResponse(Task newTask) {
        tasks.add(newTask);
        return "Oki. I've added this task:\n  " + newTask + "\nNow you have " + tasks.size()
                + " tasks in the list (˃ᴗ˂)♡";
    }

    /**
     * Deletes a specified task from the list and returns a response message.
     *
     * @param input The user input containing the task number.
     * @return A response message confirming task deletion or an error message.
     */
    public String deleteTaskWithResponse(String input) {
        int taskIndex = extractTaskIndexFromInput(input);
        if (taskIndex == NO_TASK_NUMBER) {
            return ui.returnError("INVALID! Pls specify task number :)");
        }
        if (taskIndex == INVALID_NUMBER_FORMAT) {
            return ui.returnError("Task number must be a valid number :)");
        }
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            return ui.returnError("INVALID! pls choose a task between 1 and " + tasks.size() + " (•⌓•)");
        }

        Task rem = tasks.remove(taskIndex);
        return "Noted. I've removed this task:\n  " + rem + "\nNow you have " + tasks.size()
                + " tasks in the list ( ˃ ᵕ ˂ )♡";
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param keyword The keyword to search for.
     * @return A response message listing matching tasks or indicating no matches found.
     */
    public String findTaskWithResponse(String keyword) {

        ArrayList<Task> matchingTasks = findMatchingTasks(keyword);

        if (matchingTasks.isEmpty()) {
            return ui.returnError("No matching tasks found ;-;");
        }
        String res = "Here are the matching tasks in your list:\n";
        for (int i = 0; i < matchingTasks.size(); i++) {
            res += (i + 1) + "." + matchingTasks.get(i) + "\n";
        }
        return res;
    }

    /**
     * Finds all tasks that contain the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return A list of matching tasks.
     */
    private ArrayList<Task> findMatchingTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}
