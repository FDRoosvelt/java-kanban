package kanban.managers;

import kanban.http.HttpTaskServer;
import kanban.http.KVServer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.net.http.HttpClient;

import static kanban.Managers.file;
import static org.junit.jupiter.api.Assertions.*;

class HTTPTaskManagerTest {

    static HTTPTaskManager httpTaskManager;
    static String url = "http://localhost:8078";
    static String key = "test";
    static HttpClient httpClient;
    static HttpTaskServer httpTaskServer;
    static KVServer kvServer;


    @BeforeEach
    public void BeforeEach() throws IOException {
        file.delete();
        httpTaskServer = new HttpTaskServer();
        kvServer = new KVServer();
        kvServer.start();
        httpTaskManager = new HTTPTaskManager(url, key);
        httpClient = HttpClient.newHttpClient();
    }

    @Test
    void newTaskEpicSubtask() {
        assertTrue(httpTaskManager.getTasks().isEmpty());
        assertTrue(httpTaskManager.getEpics().isEmpty());
        assertTrue(httpTaskManager.getSubtasks().isEmpty());
        httpTaskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:01");
        httpTaskManager.newEpic("epic 1", "description epic 1");
        httpTaskManager.newSubtask("sub 1", "description sub 1", "epic 1", 10, "01.01.1010 03:03");
        assertFalse(httpTaskManager.getTasks().isEmpty());
        assertFalse(httpTaskManager.getEpics().isEmpty());
        assertFalse(httpTaskManager.getSubtasks().isEmpty());
    }

    @Test
    void makeHistory() {
        assertTrue(httpTaskManager.getHistory().isEmpty());
        httpTaskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:01");
        httpTaskManager.newEpic("epic 1", "description epic 1");
        httpTaskManager.newSubtask("sub 1", "description sub 1", "epic 2", 10, "01.01.1010 03:03");
        httpTaskManager.getTask(100000);
        httpTaskManager.getEpic(100001);
        httpTaskManager.getSubtask(100002);
        assertFalse(httpTaskManager.getHistory().isEmpty());
    }

    @Test
    void makePrioritizedTasks () {
        assertTrue(httpTaskManager.getPrioritizedTasks().isEmpty());
        httpTaskManager.newTask("task 1", "Описание task 1", 10, "01.01.1010 01:01");
        httpTaskManager.newTask("task 2", "Описание task 2", 10, "01.01.1010 02:01");
        httpTaskManager.newTask("task 3", "Описание task 3", 10, "01.01.1010 01:13");
        httpTaskManager.newTask("task 4", "Описание task 4", 10, "01.01.1010 00:55");
        httpTaskManager.newTask("task 5", "Описание task 5", 0, "");
        assertEquals(4, httpTaskManager.getPrioritizedTasks().size());
    }

}