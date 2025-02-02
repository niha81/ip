package woogie.list;

import woogie.task.Task;
import woogie.ui.Ui;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    private Ui ui;

    public TaskList(ArrayList<Task> loadedTasks) {
        tasks = loadedTasks;
        ui = new Ui();
    }

    public TaskList() {
        ui = new Ui();
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        ui.showMessage("Oki. I've added this task:\n  " + newTask + "\nNow you have " + tasks.size()
                + " tasks in the list (੭˃ᴗ˂)੭.");
    }

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
            ui.showMessage("INVALID! pls choose a task between 1 and " + tasks.size() + " (•⌓•).");
        }
    }

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
            ui.showMessage("INVALID! pls choose a task between 1 and " + tasks.size() + " (•⌓•).");
        }
    }

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

}
