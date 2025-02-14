package woogie.storage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import woogie.task.Task;

/**
 * Handles saving and loading tasks from a file.
 * Ensures tasks persist between program runs.
 */
public class Storage {
    private final String filePath;

    /**
     * Initializes a Storage object with the specified file path.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the list of tasks to the file.
     *
     * @param tasks The list of tasks to be saved.
     */
    public String saveTasks(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);

            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();

            return "Tasks saved successfully •ᴗ•!";
        } catch (IOException e) {
            return "Error saving tasks: " + e.getMessage();
        }
    }

    /**
     * Loads tasks from the file.
     * If the file does not exist, returns an empty task list.
     *
     * @return An ArrayList containing the loaded tasks.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromFileFormat(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }
}
