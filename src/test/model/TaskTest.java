package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for Tasks
class TaskTest {
    private Task testTask;

    @BeforeEach
    public void runBefore() {
        testTask = new Task("CPSC210 Project", 20211015, false);
    }

    @Test
    public void testInitialTask(){
        assertEquals(testTask.getName(),"CPSC210 Project");
        assertEquals(testTask.getDeadline(),20211015);
        assertFalse(testTask.getStatus());
    }

    @Test
    public void TestSetTaskName() {
        testTask.setName("CPSC210 Project Phrase1");
        assertEquals(testTask.getName(), "CPSC210 Project Phrase1");
    }

    @Test
    public void TestSetDeadline() {
        testTask.setDeadline(20211016);
        assertEquals(testTask.getDeadline(),20211016);
    }

    @Test
    public void TestChangeStatus() {
        testTask.changeStatus(true);
        assertTrue(testTask.getStatus());
    }

    @Test
    public void TestisCalled() {
        testTask.setName("MATH Project");
        assertTrue(testTask.isCalled("MATH Project"));
        assertFalse(testTask.isCalled("CPSC210 Project Phrase1"));
    }
}