package woogie.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import woogie.task.Deadline;
import woogie.task.Event;
import woogie.task.Task;
import woogie.task.ToDo;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList(new ArrayList<>());
    }

    @Test
    public void addTask_correctlyAddsTask() {
        Task todo = new ToDo("Read book");
        taskList.addTaskWithResponse(todo);
        assertEquals(1, taskList.getTasks().size());
        assertEquals(todo, taskList.getTasks().get(0));
    }

    @Test
    public void deleteTask_removesTask() {
        Task event = new Event("Meeting", "2025-04-10 1500", "2025-04-10 1600");
        taskList.addTaskWithResponse(event);
        taskList.deleteTaskWithResponse("delete 1");

        assertEquals(0, taskList.getTasks().size());
    }

    @Test
    public void markTask_correctlyMarksTask() {
        Task deadline = new Deadline("Submit proposal", "2025-05-01 1200");
        taskList.addTaskWithResponse(deadline);
        taskList.markTaskWithResponse("mark 1");

        assertTrue(taskList.getTasks().get(0).getStatus());
    }

    @Test
    public void unmarkTask_correctlyUnmarksTask() {
        Task deadline = new Deadline("Submit project", "2025-06-01 1200");
        taskList.addTaskWithResponse(deadline);
        taskList.markTaskWithResponse("mark 1");
        taskList.unmarkTaskWithResponse("unmark 1");

        assertFalse(taskList.getTasks().get(0).getStatus());
    }

    @Test
    public void getTaskListAsString_correctFormat() {
        taskList.addTaskWithResponse(new ToDo("Walk dog"));
        taskList.addTaskWithResponse(new Deadline("Pay bills", "2025-07-01 1800"));

        String expected = "1. [T][ ] Walk dog\n"
                + "2. [D][ ] Pay bills (by: Jul 1 2025, 6:00 PM)\n";
        assertEquals(expected, taskList.getTaskListAsString());
    }

    @Test
    public void getAlphabeticalTodos_correctSorting() {
        TaskList taskList = new TaskList(new ArrayList<>());

        taskList.addTaskWithResponse(new ToDo("Write report"));
        taskList.addTaskWithResponse(new ToDo("Buy groceries"));
        taskList.addTaskWithResponse(new ToDo("Attend meeting"));

        TaskList sortedList = taskList.getAlphabeticalTodos();

        assertEquals("[T][ ] Attend meeting", sortedList.getTasks().get(0).toString());
        assertEquals("[T][ ] Buy groceries", sortedList.getTasks().get(1).toString());
        assertEquals("[T][ ] Write report", sortedList.getTasks().get(2).toString());

        // Ensure original list remains unchanged
        assertEquals("[T][ ] Write report", taskList.getTasks().get(0).toString());
    }

    @Test
    public void getChronologicalDeadlines_correctSorting() {
        TaskList taskList = new TaskList(new ArrayList<>());

        taskList.addTaskWithResponse(new Deadline("Submit Report", "2025-02-20 1800"));
        taskList.addTaskWithResponse(new Deadline("Project Due", "2025-02-10 1800"));
        taskList.addTaskWithResponse(new Deadline("Exam", "2025-03-01 1000"));

        TaskList sortedList = taskList.getChronologicalDeadlines();

        assertEquals("[D][ ] Project Due (by: Feb 10 2025, 6:00 PM)", sortedList.getTasks().get(0).toString());
        assertEquals("[D][ ] Submit Report (by: Feb 20 2025, 6:00 PM)", sortedList.getTasks().get(1).toString());
        assertEquals("[D][ ] Exam (by: Mar 1 2025, 10:00 AM)", sortedList.getTasks().get(2).toString());
    }

    @Test
    public void getChronologicalEvents_correctSorting() {
        TaskList taskList = new TaskList(new ArrayList<>());

        taskList.addTaskWithResponse(new Event("Concert", "2025-03-05 1900", "2025-03-05 2200"));
        taskList.addTaskWithResponse(new Event("Workshop", "2025-02-25 1400", "2025-02-25 1600"));
        taskList.addTaskWithResponse(new Event("Meeting", "2025-02-15 1000", "2025-02-15 1200"));

        TaskList sortedList = taskList.getChronologicalEvents();

        assertEquals("[E][ ] Meeting (from: Feb 15 2025, 10:00 AM to: Feb 15 2025, 12:00 PM)",
                sortedList.getTasks().get(0).toString());
        assertEquals("[E][ ] Workshop (from: Feb 25 2025, 2:00 PM to: Feb 25 2025, 4:00 PM)",
                sortedList.getTasks().get(1).toString());
        assertEquals("[E][ ] Concert (from: Mar 5 2025, 7:00 PM to: Mar 5 2025, 10:00 PM)",
                sortedList.getTasks().get(2).toString());
    }

}
