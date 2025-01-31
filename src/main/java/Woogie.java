public class Woogie {
    private static final String FILE_PATH = "./data/woogie.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Woogie() {
        ui = new Ui();
        storage = new Storage(FILE_PATH);
        tasks = new TaskList(storage.loadTasks());
    }

    public void run() {
        ui.showGreeting();
        while (true) {
            String input = ui.readCommand().trim();
            if (input.equalsIgnoreCase("bye")) {
                ui.showGoodbye();
                storage.saveTasks(tasks.getTasks());
                break;
            }
            Parser.processCommand(input, tasks, ui);
            storage.saveTasks(tasks.getTasks());
        }
    }

    public static void main(String[] args) {
        new Woogie().run();
    }
}
