import java.util.Scanner;

public class Woogie {
    public static void main(String[] args) {
        String line = "\n----------------------------------------\n";
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
                System.out.println(line + "Nice! I've marked this task as done: \n  " + tasks[taskIndex] + line);
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
                System.out.println(line + "Ok, I've marked this task as not done yet: \n  " + tasks[taskIndex] + line);
            } else if (input.startsWith("todo")) {
                if (taskCount >= 100) {
                    System.out.println(line + "no more space <3" + line);
                }
                String description = input.substring(5);
                tasks[taskCount] = new ToDo(description);
                taskCount++;
                System.out.println(line + "Got it. I've added this task:\n  " + tasks[taskCount - 1] + "\nNow you have "
                        + taskCount + " tasks in the list." + line);
            } else if (input.startsWith("deadline")) {
                if (taskCount >= 100) {
                    System.out.println(line + "no more space <3" + line);
                }
                String[] parts = input.split(" /by ");
                String description = parts[0].substring(9);
                String by = parts[1];
                tasks[taskCount] = new Deadline(description, by);
                taskCount++;
                System.out.println(line + "Got it. I've added this task:\n  " + tasks[taskCount - 1] + "\nNow you have "
                        + taskCount + " tasks in the list." + line);
            } else if (input.startsWith("event")) {
                if (taskCount >= 100) {
                    System.out.println(line + "no more space <3" + line);
                }
                String[] parts = input.split(" /from | /to");
                String description = parts[0].substring(6);
                String from = parts[1];
                String to = parts[2];
                tasks[taskCount] = new Event(description, from, to);
                taskCount++;
                System.out.println(line + "Got it. I've added this task:\n  " + tasks[taskCount - 1] + "\nNow you have "
                        + taskCount + " tasks in the list." + line);
            } else {
                System.out.println(line + "idk this command ;-;" + line);
            }
        }

        scanner.close();
    }
}
