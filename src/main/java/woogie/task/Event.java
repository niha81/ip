package woogie.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task in the Woogie chatbot.
 * An Event task has a specific start date/time and an end date/time.
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Initializes a new Event task with a description, start time, and end time.
     *
     * @param description The description of the task.
     * @param from The start date and time in "yyyy-MM-dd HHmm" format.
     * @param to The end date and time in "yyyy-MM-dd HHmm" format.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDateTime.parse(from, INPUT_FORMATTER);
        this.to = LocalDateTime.parse(to, INPUT_FORMATTER);
    }

    public LocalDateTime getFromDate() {
        return this.from;
    }

    /**
     * Converts the Event task into a formatted string for file storage.
     *
     * @return A string representation of the task in file format.
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(INPUT_FORMATTER) + " | " + to.format(INPUT_FORMATTER);
    }

    /**
     * Returns a string representation of the Event task.
     *
     * @return The formatted task string with type, status, start time, and end time.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMATTER)
                + " to: " + to.format(OUTPUT_FORMATTER) + ")";
    }
}
