import java.util.Scanner;

public class Woogie {
    public static void main(String[] args) {
        String line = "\n----------------------------------------\n";
        String greeting = line + "Hello! I'm Woogie\n" +
                "What can I do for you?" + line;

        System.out.println(greeting);

        String[] tasks = new String[100];
        int taskCount = 0;
        Boolean statuses[] = new Boolean[100];

        for (int i = 0; i < 100; i++) {
            statuses[i] = false;
        }

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
                    String taskList = "";

                    for (int i = 0; i < taskCount; i++) {
                        String state = statuses[i] ? "X" : " ";
                        taskList = taskList + (i+1) + ".[" + state + "] " + tasks[i] + "\n";
                    }
                    System.out.println(line + taskList + line);
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

                statuses[taskIndex] = true;
                System.out.println(line + "Nice! I've marked this task as done: \n  [X] " + tasks[taskIndex] + line);
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

                statuses[taskIndex] = false;
                System.out.println(line + "Ok, I've marked this task as not done yet: \n  [ ] " + tasks[taskIndex] + line);
            } else {
                if (taskCount < 100) {
                    tasks[taskCount] = input;
                    taskCount++;
                    System.out.println(line + "added: " + input + line);
                } else {
                    System.out.println(line + "no more space <3" + line);
                }
            }
        }

        scanner.close();
    }
}
