package persistence;

import model.ToDoList;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// code modelled from JsonSerializationDemo-Thingy
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Test for JsonReader
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ToDoList toDoList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTodoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTodoLis");
        try {
            ToDoList toDoList = reader.read();
            assertEquals("MyList", toDoList.getName());
            assertEquals(0, toDoList.getTasks().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTodoList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTodoList");
        try {
            ToDoList toDoList = reader.read();
            assertEquals("MyList", toDoList.getName());
            List<Task> tasks = toDoList.getTasks();
            assertEquals(2,tasks.size());
            checkTask("CPSC210", 20211027, false, tasks.get(0));
            checkTask("MATH200",20211029,true,tasks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
