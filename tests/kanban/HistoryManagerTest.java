package kanban;

import kanban.managers.InMemoryHistoryManager;
import kanban.tasks.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    @Test
    void addHistory() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        assertTrue(inMemoryHistoryManager.getHistory().isEmpty());
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 1; i <=11; i++) {
            Task task = new Task(100000 + i, Type.TASK, "task" + i, "description task" + i, Status.NEW, 10, "01.01.1010 01:01");
            tasks.add(task);
            inMemoryHistoryManager.addHistory(100000 + i, task);
        }
        assertFalse(inMemoryHistoryManager.getHistory().contains(tasks.get(0)));
        tasks.remove(0);
        assertEquals(10, inMemoryHistoryManager.getHistory().size());
        assertEquals(tasks, inMemoryHistoryManager.getHistory());
    }

    @Test
    void getHistory() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        assertTrue(inMemoryHistoryManager.getHistory().isEmpty());
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 1; i <=11; i++) {
            Task task = new Task(100000 + i, Type.TASK, "task" + i, "description task" + i, Status.NEW, 10, "01.01.1010 01:01");
            tasks.add(task);
            inMemoryHistoryManager.addHistory(100000 + i, task);
        }
        assertEquals(tasks, inMemoryHistoryManager.getHistory());
    }

    @Test
    void loadHistory() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        assertTrue(inMemoryHistoryManager.getHistory().isEmpty());
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 1; i <=10; i++) {
            Task task = new Task(100000 + i, Type.TASK, "task" + i, "description task" + i, Status.NEW, 10, "01.01.1010 01:01");
            tasks.add(task);
        }
        inMemoryHistoryManager.loadHistory(tasks);
        assertEquals(tasks, inMemoryHistoryManager.getHistory());
    }


    @Test
    void getTasks() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        assertTrue(inMemoryHistoryManager.getHistory().isEmpty());
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 1; i <=10; i++) {
            Task task = new Task(100000 + i, Type.TASK, "task" + i, "description task" + i, Status.NEW, 10, "01.01.1010 01:01");
            tasks.add(task);
            inMemoryHistoryManager.addHistory(100000 + i, task);
        }
        assertEquals(tasks, inMemoryHistoryManager.getHistory());
    }
}