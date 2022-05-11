package persistence;

import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTask(String name, int deadline, boolean isComplete, Task task) {
        assertEquals(name, task.getName());
        assertEquals(deadline, task.getDeadline());
    }
}