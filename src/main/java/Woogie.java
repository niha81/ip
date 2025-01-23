import java.util.Scanner;

public class Woogie {
    public static void main(String[] args) {
        String line = "\n----------------------------------------\n";
        String greeting = line + "Hello! I'm Woogie\n" +
                "What can I do for you?" + line;

        System.out.println(greeting);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(line + "Bye. Hope to see you again soon!" + line);
                break;
            }

            System.out.println(line + input + line);
        }

        scanner.close();
    }
}
