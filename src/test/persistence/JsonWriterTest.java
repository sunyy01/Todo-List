package persistence;

import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// code modelled from JsonSerializationDemo-Thingy
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Test for JsonWriter
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.


    @Test
    void testWriterInvalidFile() {
        try {
            ToDoList tasks = new ToDoList("MyList");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTodoList() {
        try {
            ToDoList tasks = new ToDoList("MyList");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTodoList");
            writer.open();
            writer.write(tasks);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTodoList");
            tasks = reader.read();
            assertEquals("MyList", tasks.getName());
            assertEquals(0, tasks.getTasks().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTodoList() {
        try {
            ToDoList tasks = new ToDoList("MyList");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTodoList");
            tasks.addTask(new Task("STAT200",20211103,false));
            tasks.addTask(new Task("EDCP303",20211030,false));
            writer.open();
            writer.write(tasks);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTodoList");
            tasks = reader.read();
            assertEquals("MyList", tasks.getName());
            List<Task> taskList = tasks.getTasks();
            assertEquals(2, taskList.size());
            checkTask("STAT200",20211103,false, taskList.get(0));
            checkTask( "EDCP303",20211030,false, taskList.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}