import java.util.ArrayList;
import java.util.Scanner;

public class Woogie {
    private static final String LINE = "\n----------------------------------------------------\n";
    public static void main(String[] args) {
        String greeting = LINE + "Hello! I'm Woogie\n" + "What can I do for you?" + LINE;
        System.out.println(greeting);

        ArrayList<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(LINE + "Bye. Hope to see you again soon!" + LINE);
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                listTasks(tasks);
            } else if (input.startsWith("mark")) {
                markAsDone(tasks, input);
            } else if (input.startsWith("unmark")) {
                markAsNotDone(tasks, input);
            } else if (input.startsWith("todo")) {
                addTodo(tasks, input);
            } else if (input.startsWith("deadline")) {
                addDeadline(tasks, input);
            } else if (input.startsWith("event")) {
                addEvent(tasks, input);
            } else if (input.startsWith("delete")) {
                deleteTask(tasks, input);
            } else {
                System.out.println(LINE + "sorry idk this command ;-;" + LINE);
            }

        }

        scanner.close();
    }

    private static void listTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(LINE + "nothing here yet TT" + LINE);
        } else {
            System.out.println(LINE);
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
            System.out.println(LINE);
        }
    }

    private static void markAsDone(ArrayList<Task> tasks, String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            System.out.println(LINE + "INVALID! Pls specify task number :)" + LINE);
            return;
        }
        try {
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            tasks.get(taskIndex).markDone();
            System.out.println(LINE + "Nice! I've marked this task as done:\n  " + tasks.get(taskIndex) + LINE);
        } catch (NumberFormatException e) {
            System.out.println(LINE + "INVALID! Task number must be a valid number :)" + LINE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE + "pls choose a task between 1 and " + tasks.size() + "." + LINE);
        }
    }

    private static void markAsNotDone(ArrayList<Task> tasks, String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            System.out.println(LINE + "INVALID! Pls specify task number :)" + LINE);
            return;
        }

        try {
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            tasks.get(taskIndex).markUndone();
            System.out.println(LINE + "Ok, I've marked this task as not done yet:\n  " + tasks.get(taskIndex) + LINE);
        } catch (NumberFormatException e) {
            System.out.println(LINE + "INVALID! Task number must be a valid number :)" + LINE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE + "pls choose a task between 1 and " + tasks.size() + "." + LINE);
        }
    }

    private static void addTodo(ArrayList<Task> tasks, String input) {
        if (input.length() <= 5 || input.substring(5).trim().isEmpty()) {
            System.out.println(LINE + "todo's description cannot be empty, pls add one >:(" + LINE);
            return;
        }
        String description = input.substring(5).trim();
        Task newTask = new ToDo(description);
        addTask(tasks, newTask);
    }

    private static void addDeadline(ArrayList<Task> tasks, String input) {
        if (!input.contains(" /by ")) {
            System.out.println(LINE + "you can't have a deadline without a deadline, add a /by :)" + LINE);
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

            String description = parts[0].substring(9);
            String by = parts[1];
            Task newTask = new Deadline(description, by);
            addTask(tasks, newTask);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(LINE + "oop, smt went wrong with your deadline input." + LINE);
        } catch (IllegalArgumentException e) {
            System.out.println(LINE + e.getMessage() + LINE);
        }
    }

    private static void addEvent(ArrayList<Task> tasks, String input) {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            System.out.println(LINE + "i need to know when your event starts and ends,\n"
                    + "pls add both a /from and /to :<" + LINE);
            return;
        }

        try {
            String[] parts = input.split(" /from | /to ", 3);
            if (parts.length < 3 || parts[0].length() <= 6 || parts[0].substring(6).trim().isEmpty()) {
                throw new IllegalArgumentException("event's description cannot be empty, pls add one >:(");
            }
            if (parts[1].trim().isEmpty()) {
                throw new IllegalArgumentException("event's /from time cannot be empty!");
            }
            if (parts[2].trim().isEmpty()) {
                throw new IllegalArgumentException("event's /to time cannot be empty!");
            }

            String description = parts[0].substring(6);
            String from = parts[1];
            String to = parts[2];
            Task newTask = new Event(description, from, to);
            addTask(tasks, newTask);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(LINE + "oop, smt went wrong with your event input." + LINE);
        } catch (IllegalArgumentException e) {
            System.out.println(LINE + e.getMessage() + LINE);
        }
    }

    private static void addTask(ArrayList<Task> tasks, Task newTask) {
        tasks.add(newTask);
        System.out.println(LINE + "Got it. I've added this task:\n  " + newTask
                + "\nNow you have " + tasks.size() + " tasks in the list." + LINE);
    }

    private static void deleteTask(ArrayList<Task> tasks, String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            System.out.println(LINE + "INVALID! Pls specify task number :)" + LINE);
            return;
        }

        try {
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            Task rem = tasks.remove(taskIndex);
            System.out.println(LINE + "Noted. I've removed this task:\n  " + rem
                    + "\nNow you have " + tasks.size() + " tasks in the list." + LINE);
        } catch (NumberFormatException e) {
            System.out.println(LINE + "INVALID! Task number must be a valid number :)" + LINE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(LINE + "pls choose a task between 1 and " + tasks.size() + "." + LINE);
        }
    }

}
