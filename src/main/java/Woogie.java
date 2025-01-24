import java.util.Scanner;

public class Woogie {
    public static void main(String[] args) {
        String line = "\n------------------------------------------------\n";
        String greeting = line + "Hello! I'm Woogie\n" + "What can I do for you?" + line;
        System.out.println(greeting);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(line + "Bye. Hope to see you again soon!" + line);
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                if (taskCount == 0) {
                    System.out.println(line + "nothing here yet TT" + line);
                } else {
                    System.out.println(line);
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                    System.out.println(line);
                }
            } else if (input.startsWith("mark")) {
                String[] parts = input.split(" ");
                if (parts.length < 2) {
                    System.out.println(line + "INVALID! Pls specify task number :)" + line);
                    continue;
                }
                int taskIndex = Integer.parseInt(parts[1]) - 1;

                if (taskIndex < 0 || taskIndex >= taskCount) {
                    System.out.println(line + "pls choose a task between 1 and " + taskCount + "." + line);
                    continue;
                }

                tasks[taskIndex].markDone();
                System.out.println(line + "Nice! I've marked this task as done:\n  " + tasks[taskIndex] + line);
            } else if (input.startsWith("unmark")) {
                String[] parts = input.split(" ");
                if (parts.length < 2) {
                    System.out.println(line + "INVALID! Pls specify task number :)" + line);
                    continue;
                }
                int taskIndex = Integer.parseInt(parts[1]) - 1;

                if (taskIndex < 0 || taskIndex >= taskCount) {
                    System.out.println(line + "pls choose a task between 1 and " + taskCount + "." + line);
                    continue;
                }

                tasks[taskIndex].markUndone();
                System.out.println(line + "Ok, I've marked this task as not done yet:\n  " + tasks[taskIndex] + line);
            } else if (input.startsWith("todo")) {
                if (taskCount >= 100) {
                    System.out.println(line + "no more space <3" + line);
                }
                if (input.length() <= 5 || input.substring(5).trim().isEmpty()) {
                    System.out.println(line + "todo's description cannot be empty, pls add one >:(" + line);
                    continue;
                }
                String description = input.substring(5);
                Task newTask = new ToDo(description);
                addTask(tasks, taskCount, newTask, line);
                taskCount++;
            } else if (input.startsWith("deadline")) {
                if (taskCount >= 100) {
                    System.out.println(line + "no more space <3" + line);
                }

                if (!input.contains(" /by ")) {
                    System.out.println(line + "you can't have a deadline without a deadline, add a /by :)" + line);
                    continue;
                }

                String[] parts = input.split(" /by ", 2);
                if (parts[0].length() <= 9 || parts[0].substring(9).trim().isEmpty()) {
                    System.out.println(line + "deadline's description cannot be empty, pls add one >:(" + line);
                    continue;
                }
                if (parts[1].trim().isEmpty()) {
                    System.out.println(line + "deadline's /by time cannot be empty!" + line);
                    continue;
                }

                String description = parts[0].substring(9);
                String by = parts[1];
                Task newTask = new Deadline(description, by);
                addTask(tasks, taskCount, newTask, line);
                taskCount++;
            } else if (input.startsWith("event")) {
                if (taskCount >= 100) {
                    System.out.println(line + "no more space <3" + line);
                }

                if (!input.contains(" /from ")) {
                    System.out.println(line + "i need to know when you event starts, pls add a /from :<" + line);
                    continue;
                }
                if (!input.contains(" /to ")) {
                    System.out.println(line + "i need to know when you event ends, pls add a /to :<" + line);
                    continue;
                }

                String[] parts = input.split(" /from | /to ", 3);
                if (parts[0].length() <= 6 || parts[0].substring(6).trim().isEmpty()) {
                    System.out.println(line + "event's description cannot be empty, pls add one >:(" + line);
                    continue;
                }
                if (parts[1].trim().isEmpty()) {
                    System.out.println(line + "event's /from time cannot be empty!" + line);
                    continue;
                }
                if (parts[2].trim().isEmpty()) {
                    System.out.println(line + "event's /to time cannot be empty!" + line);
                    continue;
                }

                String description = parts[0].substring(6);
                String from = parts[1];
                String to = parts[2];
                Task newTask = new Event(description, from, to);
                addTask(tasks, taskCount, newTask, line);
                taskCount++;
            } else if (input.startsWith("delete")) {
                String[] parts = input.split(" ");
                if (parts.length < 2) {
                    System.out.println(line + "INVALID! Pls specify task number :)" + line);
                    continue;
                }
                int taskIndex = Integer.parseInt(parts[1]) - 1;

                if (taskIndex < 0 || taskIndex >= taskCount) {
                    System.out.println(line + "pls choose a task between 1 and " + taskCount + "." + line);
                    continue;
                }
                taskCount--;
                deleteTask(tasks, taskCount, taskIndex, line);
            } else {
                System.out.println(line + "sorry idk this command ;-;" + line);
            }
        }

        scanner.close();
    }

    private static void addTask(Task[] tasks, int taskCount, Task newTask, String line) {
        tasks[taskCount] = newTask;
        System.out.println(line + "Got it. I've added this task:\n  " + tasks[taskCount]
                + "\nNow you have " + (taskCount + 1) + " tasks in the list." + line);
    }

    private static void deleteTask(Task[] tasks, int taskCount, int taskIndex, String line) {
        Task rem = tasks[taskIndex];
        for(int i = taskIndex; i < taskCount; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[taskCount] = null;
        System.out.println(line + "Noted. I've removed this task:\n  " + rem
                + "\nNow you have " + taskCount + " tasks in the list." + line);
    }

}
