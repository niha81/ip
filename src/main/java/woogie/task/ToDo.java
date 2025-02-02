package woogie.task;

/**
 * Represents a To-Do task in the Woogie chatbot.
 * A To-Do is a task without a specific date/time.
 */
public class ToDo extends Task {
    /**
     * Initializes a new To-Do task with the given description.
     *
     * @param description The description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Converts the To-Do task into a formatted string for file storage.
     *
     * @return A string representation of the task in file format.
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of the To-Do task.
     *
     * @return The formatted task string with type and status.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
