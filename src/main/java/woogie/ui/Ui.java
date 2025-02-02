package woogie.ui;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;
    private static final String LINE = "\n---------------------------------------------------------------\n";

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showGreeting() {
        System.out.println(LINE + "｡ﾟ☆ Greetings! I'm woogie.Woogie ☆ﾟ\n" + "How can I help you?" + LINE);
    }

    public void showGoodbye() {
        System.out.println(LINE + "It pains me to have to part ways ૮(˶ㅠ︿ㅠ).\nHope to see you again soon!" + LINE);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(LINE + message + LINE);
    }

    public void showError(String message) {
        System.out.println(LINE + "OOP! smt went wrong: " + message + " (ꞋꞌꞋꞌŏ_ŏ)" + LINE);
    }
}
