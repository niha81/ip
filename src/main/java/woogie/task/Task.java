package woogie.task;

/**
 * Represents a task in the Woogie chatbot.
 * Provides functionality to mark tasks as done or not done.
 */
public abstract class Task {
    /** Description of the task. */
    protected String description;
    /** Completion status of the task. */
    protected boolean isDone;

    /**
     * Initializes a task with the given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return true if the task is done, otherwise false.
     */
    public boolean getStatus() {
        return this.isDone;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a string representation of the task in file format.
     * This method is implemented in subclasses.
     *
     * @return The task formatted for file storage.
     */
    public abstract String toFileFormat();

    /**
     * Creates a task object from a string stored in a file.
     *
     * @param line The line from the file representing a task.
     * @return The corresponding Task object, or null if the format is invalid.
     */
    public static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null;

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
            case "T":
                ToDo todo = new ToDo(description);
                if (isDone) todo.markDone();
                return todo;
            case "D":
                if (parts.length < 4) return null;
                Deadline deadline = new Deadline(description, parts[3]);
                if (isDone) deadline.markDone();
                return deadline;
            case "E":
                if (parts.length < 5) return null;
                Event event = new Event(description, parts[3], parts[4]);
                if (isDone) event.markDone();
                return event;
            default:
                return null;
        }
    }

    /**
     * Returns a string representation of the task.
     *
     * @return The formatted task string with status and description.
     */
    @Override
    public String toString() {
        String status = isDone ? "X" : " ";
        return "[" + status + "] " + description;
    }
}
