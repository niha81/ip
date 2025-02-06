package woogie.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline in the Woogie chatbot.
 * A Deadline task has a specific date and time by which it must be completed.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    private LocalDateTime by;

    /**
     * Initializes a new Deadline task with a description and due date.
     *
     * @param description The description of the task.
     * @param by The due date and time in "yyyy-MM-dd HHmm" format.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by, INPUT_FORMATTER);
    }

    /**
     * Converts the Deadline task into a formatted string for file storage.
     *
     * @return A string representation of the task in file format.
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(INPUT_FORMATTER);
    }

    /**
     * Returns a string representation of the Deadline task.
     *
     * @return The formatted task string with type, status, and due date.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMATTER) + ")";
    }
}
