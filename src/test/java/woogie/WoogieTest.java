package woogie;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import woogie.list.TaskList;
import woogie.storage.Storage;
import woogie.task.Task;
import woogie.task.ToDo;
import woogie.ui.Ui;


public class WoogieTest {
    private Woogie woogie;
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    @BeforeEach
    public void setUp() {
        ui = new Ui();
        storage = new Storage("./test_data/woogie_test.txt");
        tasks = new TaskList(new ArrayList<>());
        woogie = new Woogie();
    }

    @Test
    public void testWoogieInitialization() {
        assertNotNull(woogie, "Woogie should be initialized properly.");
        assertNotNull(woogie.getClass(), "Woogie should have a valid class definition.");
    }

    @Test
    public void testTaskListPersistsAfterExit() {
        Task task = new ToDo("Read book");
        tasks.addTask(task);
        storage.saveTasks(tasks.getTasks());

        TaskList loadedTasks = new TaskList(storage.loadTasks());
        assertEquals(1, loadedTasks.getTasks().size(), "Task should persist after saving and reloading.");
    }

}
