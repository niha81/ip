package woogie.command;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import woogie.list.TaskList;
import woogie.storage.Storage;
import woogie.task.Deadline;
import woogie.task.Event;
import woogie.task.Task;
import woogie.task.ToDo;
import woogie.ui.Ui;

/**
 * Handles the processing of user commands.
 * Determines the appropriate actions to take based on user input.
 */
public class Parser {
    private static final String DATE_FORMAT = "\\d{4}-\\d{2}-\\d{2} \\d{4}";
    private static Ui ui;

    /**
     * Sets the global UI instance for use in all methods.
     *
     * @param uiInstance The Ui instance to set.
     */
    private static void setUi(Ui uiInstance) {
        ui = uiInstance;
    }

    /**
     * Processes user input and executes the corresponding command.
     * Returns a response string instead of printing directly.
     *
     * @param input User input command.
     * @param tasks TaskList containing the tasks.
     * @param uiInstance User interface for displaying messages.
     * @return A response string based on the command execution.
     */
    public static String processCommandWithResponse(String input, TaskList tasks, Ui uiInstance, Storage storage) {
        setUi(uiInstance);
        String command = input.split(" ")[0].toLowerCase();

        if (command.equals("bye")) {
            return storage.saveTasks(tasks.getTasks()) + "\n" + ui.getGoodbye();
        }
        if (command.equals("list")) {
            return tasks.getTaskListAsString();
        }
        if (command.equals("find")) {
            return processFindCommand(input, tasks);
        }
        if (command.equals("sort_todos")) {
            return sortTodosWithResponse(tasks);
        }
        if (command.equals("sort_deadlines")) {
            return sortDeadlinesWithResponse(tasks);
        }
        if (command.equals("sort_events")) {
            return sortEventsWithResponse(tasks);
        }

        Map<String, Function<String, String>> commands = initializeCommands(tasks, ui);
        return commands.getOrDefault(command, (cmd) -> "sorry idk this command ;-;").apply(input);
    }

    /**
     * Initializes the command map with corresponding task functions.
     *
     * @param tasks TaskList containing tasks.
     * @return A map of command strings to processing functions.
     */
    private static Map<String, Function<String, String>> initializeCommands(TaskList tasks, Ui ui) {
        Map<String, Function<String, String>> commands = new HashMap<>();
        commands.put("mark", tasks::markTaskWithResponse);
        commands.put("unmark", tasks::unmarkTaskWithResponse);
        commands.put("todo", (cmd) -> addTodoWithResponse(tasks, cmd));
        commands.put("deadline", (cmd) -> addDeadlineWithResponse(tasks, cmd));
        commands.put("event", (cmd) -> addEventWithResponse(tasks, cmd));
        commands.put("delete", tasks::deleteTaskWithResponse);
        return commands;
    }

    /**
     * Processes the 'find' command.
     *
     * @param input The user input command.
     * @param tasks TaskList where tasks are stored.
     * @return A response message listing matching tasks or indicating no matches found.
     */
    private static String processFindCommand(String input, TaskList tasks) {
        String keyword = extractFindKeyword(input);
        if (keyword.isEmpty()) {
            return ui.returnError("Please enter a keyword to search for (•¬•)");
        }
        return tasks.findTaskWithResponse(keyword);
    }

    /**
     * Extracts the keyword or task description from user input.
     *
     * @param input The user input command.
     * @return The extracted description or keyword.
     */
    private static String extractFindKeyword(String input) {
        String command = "find";
        if (input.length() > command.length()) {
            return input.substring(command.length()).trim();
        }
        return "";
    }

    /**
     * Adds a ToDo task to the TaskList and returns a response message.
     *
     * @param tasks TaskList where the task will be added.
     * @param input User input command for adding a ToDo.
     * @return Response message confirming task addition or an error message.
     */
    private static String addTodoWithResponse(TaskList tasks, String input) {
        String description = extractTaskDescription(input, "todo");
        if (description.isEmpty()) {
            return ui.returnError("todo's description cannot be empty, pls add one (•¬•)");
        }
        Task newTask = new ToDo(description);
        return tasks.addTaskWithResponse(newTask);
    }

    /**
     * Extracts the task description from user input.
     *
     * @param input   The user input command.
     * @param command The command keyword (e.g., "todo").
     * @return The extracted description.
     */
    private static String extractTaskDescription(String input, String command) {
        if (input.length() > command.length()) {
            return input.substring(command.length()).trim();
        }
        return "";
    }

    /**
     * Adds a Deadline task to the TaskList and returns a response message.
     *
     * @param tasks TaskList where the task will be added.
     * @param input User input command for adding a Deadline.
     * @return Response message confirming task addition or an error message.
     */
    private static String addDeadlineWithResponse(TaskList tasks, String input) {
        if (!input.contains("/by")) {
            return ui.returnError("you can't have a deadline without a deadline, add a /by date (•︿•)");
        }

        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            return ui.returnError("deadline's description or date cannot be empty, pls add one (°△°)!!");
        }

        String description = parts[0].replaceFirst("^deadline\\s*", "").trim();
        if (description.isEmpty()) {
            return ui.returnError("deadline's description cannot be empty, pls add one (°△°)!!");
        }

        String deadline = parts[1].trim();
        if (!deadline.matches(DATE_FORMAT)) {
            return ui.returnError("deadline must be in yyyy-MM-dd HHmm format (•¬•)!");
        }

        Task newTask = new Deadline(description, deadline);
        return tasks.addTaskWithResponse(newTask);
    }

    /**
     * Adds an Event task to the TaskList and returns a response message.
     *
     * @param tasks TaskList where the task will be added.
     * @param input User input command for adding an Event.
     * @return Response message confirming task addition or an error message.
     */
    private static String addEventWithResponse(TaskList tasks, String input) {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            return ui.returnError("i need to know when your event starts and ends,\n"
                    + "pls add both a /from and /to (•︿•)");
        }

        String[] firstSplit = input.split(" /from ", 2);
        if (firstSplit.length < 2 || firstSplit[1].trim().isEmpty()) {
            return ui.returnError("event's description or start time cannot be empty, pls add one (°△°)!!");
        }

        String description = firstSplit[0].replaceFirst("^event\\s*", "").trim();
        if (description.isEmpty()) {
            return ui.returnError("event's description cannot be empty, pls add one (•¬•)");
        }

        String[] secondSplit = firstSplit[1].split(" /to ", 2);
        if (secondSplit.length < 2 || secondSplit[1].trim().isEmpty()) {
            return ui.returnError("i need to know when your event starts and ends,\n"
                    + "pls add both a /from and /to (•︿•)");
        }

        String from = secondSplit[0].trim();
        String to = secondSplit[1].trim();

        if (!from.matches(DATE_FORMAT) || !to.matches(DATE_FORMAT)) {
            return ui.returnError("event times must be in yyyy-MM-dd HHmm format (•¬•)!");
        }

        Task newTask = new Event(description, from, to);
        return tasks.addTaskWithResponse(newTask);
    }

    private static String sortTodosWithResponse(TaskList tasks) {
        TaskList sortedTodos = tasks.getAlphabeticalTodos();
        return ui.returnMessage("Here are the todos sorted alphabetically:\n"
                + sortedTodos.getTaskListAsString() + "You have some work to do (≧ᗜ≦)!!");
    }

    private static String sortDeadlinesWithResponse(TaskList tasks) {
        TaskList sortedDeadlines = tasks.getChronologicalDeadlines();
        return ui.returnMessage("Here are the deadlines sorted chronologically:\n"
                + sortedDeadlines.getTaskListAsString() + "Lets get to work (๑•̀ ᴗ•́)૭✧");
    }

    private static String sortEventsWithResponse(TaskList tasks) {
        TaskList sortedEvents = tasks.getChronologicalEvents();
        return ui.returnMessage("Here are the events sorted by start date:\n"
                + sortedEvents.getTaskListAsString() + "Hope you get them done ⚝");
    }
}
