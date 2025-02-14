package woogie.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void toDo_toString_correctFormat() {
        Task todo = new ToDo("Buy groceries");
        assertEquals("[T][ ] Buy groceries", todo.toString());
    }

    @Test
    public void deadline_toString_correctFormat() {
        Task deadline = new Deadline("Submit report", "2025-02-20 1800");
        assertEquals("[D][ ] Submit report (by: Feb 20 2025, 6:00 PM)", deadline.toString());
    }

    @Test
    public void event_toString_correctFormat() {
        Task event = new Event("Concert", "2025-03-05 1900", "2025-03-05 2200");
        assertEquals("[E][ ] Concert (from: Mar 5 2025, 7:00 PM to: Mar 5 2025, 10:00 PM)", event.toString());
    }

    @Test
    public void markTask_correctlyUpdatesStatus() {
        Task todo = new ToDo("Finish assignment");
        todo.markDone();
        assertEquals("[T][X] Finish assignment", todo.toString());
    }

    @Test
    public void unmarkTask_correctlyUpdatesStatus() {
        Task todo = new ToDo("Finish assignment");
        todo.markDone();
        todo.markUndone();
        assertEquals("[T][ ] Finish assignment", todo.toString());
    }
}
