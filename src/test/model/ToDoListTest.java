package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoListTest {
    private ToDoList testToDoList;

    Task task1 = new Task("MATH Webwork", 20211016, false);
    Task task2 = new Task("STAT Webwork", 20211017, false);
    Task task3 = new Task("Reading", 20211018, true);
    Task task4 = new Task("Discussion", 20211017, false);

    @BeforeEach
    public void runbefore() {
        testToDoList = new ToDoList("The List");
    }

    @Test
    public void testInitialToDoList() {
        assertEquals(testToDoList.getTasks().size(),0);
        assertEquals(testToDoList.incompleteTask(),testToDoList.completeTask());

    }

    @Test
    public void testAddTask() {
        testToDoList.addTask(task1);
        testToDoList.addTask(task2);
        testToDoList.addTask(task3);

        assertEquals(testToDoList.getTasks().size(),3);
        assertTrue(testToDoList.getTasks().contains(task1));
        assertTrue(testToDoList.getTasks().contains(task2));
        assertTrue(testToDoList.getTasks().contains(task3));
        assertFalse(testToDoList.getTasks().contains(task4));

    }

    @Test
    public void testRemoveTaskByName() {
        testToDoList.addTask(task1);
        testToDoList.addTask(task3);
        testToDoList.addTask(task4);
        testToDoList.removeTask("Reading");
        testToDoList.removeTask("Discussion");

        assertEquals(testToDoList.getTasks().size(),1);
        assertTrue(testToDoList.getTasks().contains(task1));
        assertFalse(testToDoList.getTasks().contains(task3));
        assertFalse(testToDoList.getTasks().contains(task4));
        assertFalse(testToDoList.getTasks().contains(task2));

    }

    @Test
    public void testRemoveTaskByTask() {
        testToDoList.addTask(task1);
        testToDoList.addTask(task3);
        testToDoList.addTask(task4);
        testToDoList.remove(task3);
        testToDoList.remove(task4);

        assertEquals(testToDoList.getTasks().size(), 1);
        assertTrue(testToDoList.getTasks().contains(task1));
        assertFalse(testToDoList.getTasks().contains(task3));
        assertFalse(testToDoList.getTasks().contains(task4));
        assertFalse(testToDoList.getTasks().contains(task2));
    }

    @Test
    public void testCompleteTask() {
        testToDoList.addTask(task1);
        testToDoList.addTask(task2);
        testToDoList.addTask(task3);

        assertEquals(testToDoList.completeTask().size(),1);
        assertTrue(testToDoList.completeTask().contains(task3));
        assertFalse(testToDoList.completeTask().contains(task2));
    }

    @Test
    public void testIncompleteTask() {
        testToDoList.addTask(task1);
        testToDoList.addTask(task4);
        testToDoList.addTask(task3);

        assertEquals(testToDoList.incompleteTask().size(),2);
        assertTrue(testToDoList.incompleteTask().contains(task1));
        assertFalse(testToDoList.incompleteTask().contains(task3));
    }

}

