package woogie.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import woogie.task.Task;
import woogie.task.ToDo;

import java.util.ArrayList;

public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList(new ArrayList<>());
    }

    @Test
    public void testAddTask() {
        Task task = new ToDo("Finish homework");
        taskList.addTask(task);
        assertEquals(1, taskList.getTasks().size(), "Task should be added successfully.");
    }

    @Test
    public void testDeleteTask_Valid() {
        Task task1 = new ToDo("Read book");
        Task task2 = new ToDo("Write notes");
        taskList.addTask(task1);
        taskList.addTask(task2);

        taskList.deleteTask("delete 1");
        assertEquals(1, taskList.getTasks().size(), "Task should be deleted successfully.");
    }

    @Test
    public void testDeleteTask_InvalidIndex() {
        Task task = new ToDo("Buy groceries");
        taskList.addTask(task);

        taskList.deleteTask("delete 10"); // Invalid index
        assertEquals(1, taskList.getTasks().size(), "List should remain unchanged if delete index is out of range.");
    }

    @Test
    public void testMarkTask() {
        Task task = new ToDo("Clean room");
        taskList.addTask(task);

        taskList.markTask("mark 1");
        assertTrue(taskList.getTasks().get(0).getStatus(), "Task should be marked as done.");
    }

    @Test
    public void testUnmarkTask() {
        Task task = new ToDo("Watch movie");
        taskList.addTask(task);
        taskList.markTask("mark 1");

        taskList.unmarkTask("unmark 1");
        assertFalse(taskList.getTasks().get(0).getStatus(), "Task should be marked as not done.");
    }
}
