package woogie.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void testMarkDone() {
        Task task = new ToDo("Finish homework");
        task.markDone();
        assertTrue(task.isDone, "Task should be marked as done.");
    }

    @Test
    public void testMarkUndone() {
        Task task = new ToDo("Read book");
        task.markDone();
        task.markUndone();
        assertFalse(task.isDone, "Task should be marked as not done.");
    }

    @Test
    public void testFromFileFormatTodo() {
        Task task = Task.fromFileFormat("T | 1 | Buy milk");
        assertNotNull(task, "Task should not be null.");
        assertInstanceOf(ToDo.class, task, "Task should be an instance of ToDo.");
        assertEquals("[T][X] Buy milk", task.toString(), "Task should be correctly parsed from file.");
    }

    @Test
    public void testFromFileFormatInvalidFormat() {
        Task task = Task.fromFileFormat("X | 0 | Invalid task");
        assertNull(task, "Invalid task format should return null.");
    }
}
