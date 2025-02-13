package woogie.list;
import java.util.ArrayList;

import woogie.task.Task;
import woogie.ui.Ui;

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
            ui.showMessage("INVALID! pls choose a task between 1 and " + tasks.size() + "  (•⌓•).");
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
            ui.showError("Task number must be a valid number :)");
        } catch (IndexOutOfBoundsException e) {
            ui.showMessage("INVALID! pls choose a task between 1 and " + tasks.size() + " (•⌓•).");
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
            ui.showError("Task number must be a valid number :)");
        } catch (IndexOutOfBoundsException e) {
            ui.showMessage("INVALID! pls choose a task between 1 and " + tasks.size() + " (•⌓•).");
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

    /**
     * Finds tasks that contain the specified keyword in their descriptions.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public void findTask(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            ui.showMessage("No matching tasks found ;-;");
        } else {
            ui.showMessage("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + "." + matchingTasks.get(i));
            }
            ui.showLine();
        }
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
     * Marks a specified task as done and returns a response message.
     *
     * @param input The user input containing the task number.
     * @return A response message confirming the task is marked done or an error message.
     */
    public String markTaskWithResponse(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            return "INVALID! Pls specify task number :)";
        }
        try {
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            tasks.get(taskIndex).markDone();
            return "Nice! I've marked this task as done:\n  " + tasks.get(taskIndex);
        } catch (NumberFormatException e) {
            return "OOP! smt went wrong:\nTask number must be a valid number :)";
        } catch (IndexOutOfBoundsException e) {
            return "INVALID! pls choose a task between 1 and " + tasks.size() + " (•⌓•).";
        }
    }

    /**
     * Marks a specified task as not done and returns a response message.
     *
     * @param input The user input containing the task number.
     * @return A response message confirming the task is unmarked or an error message.
     */
    public String unmarkTaskWithResponse(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            return "INVALID! Pls specify task number :)";
        }

        try {
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            tasks.get(taskIndex).markUndone();
            return "Ok, I've marked this task as not done yet:\n  " + tasks.get(taskIndex);
        } catch (NumberFormatException e) {
            return "OOP! smt went wrong:\nTask number must be a valid number :)";
        } catch (IndexOutOfBoundsException e) {
            return "INVALID! pls choose a task between 1 and " + tasks.size() + " (•⌓•).";
        }
    }

    /**
     * Adds a new task to the list and returns a response message.
     *
     * @param newTask The task to be added.
     * @return A response message confirming task addition.
     */
    public String addTaskWithResponse(Task newTask) {
        assert newTask != null : "Task being added should not be null";

        tasks.add(newTask);
        return "Oki. I've added this task:\n  " + newTask + "\nNow you have " + tasks.size()
                + " tasks in the list (˃ᴗ˂).";
    }

    /**
     * Deletes a specified task from the list and returns a response message.
     *
     * @param input The user input containing the task number.
     * @return A response message confirming task deletion or an error message.
     */
    public String deleteTaskWithResponse(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            return "INVALID! Pls specify task number :)";
        }

        try {
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            Task rem = tasks.remove(taskIndex);
            return "Noted. I've removed this task:\n  " + rem + "\nNow you have " + tasks.size()
                    + " tasks in the list ٩>ᴗ<)و.";
        } catch (NumberFormatException e) {
            return "OOP! smt went wrong:\nTask number must be a valid number :)";
        } catch (IndexOutOfBoundsException e) {
            return "INVALID! pls choose a task between 1 and " + tasks.size() + " (•⌓•).";
        }
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param keyword The keyword to search for.
     * @return A response message listing matching tasks or indicating no matches found.
     */
    public String findTaskWithResponse(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            return "No matching tasks found ;-;";
        } else {
            String res = "Here are the matching tasks in your list:\n";
            for (int i = 0; i < matchingTasks.size(); i++) {
                res += (i + 1) + "." + matchingTasks.get(i);
            }
            return res;
        }
    }
}
