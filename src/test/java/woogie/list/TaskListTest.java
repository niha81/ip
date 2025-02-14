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
}
