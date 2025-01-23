import java.util.Scanner;

public class Woogie {
    public static void main(String[] args) {
        String line = "\n----------------------------------------\n";
        String greeting = line + "Hello! I'm Woogie\n" +
                "What can I do for you?" + line;

        System.out.println(greeting);

        String[] tasks = new String[100];
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
                    String taskList = "";
                    for (int i = 0; i < taskCount; i++) {
                        taskList = taskList + (i+1) + ". " + tasks[i] + "\n";
                    }
                    System.out.println(line + taskList + line);
                }
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
