package kanban;

import kanban.managers.FileBackedTasksManager;
import kanban.managers.InMemoryTaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    static TaskManager taskManager;
    static File file;

    @BeforeEach
    public void BeforeEach() {
        file = new File(System.getProperty("user.dir")+"/tests/kanban/data/data.csv");
        taskManager = new InMemoryTaskManager();
    }

    @AfterEach
    public void AfterEach() {
        file.delete();
    }

    @Test
    void getHistory() {
        assertTrue(taskManager.getHistory().isEmpty());
        taskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:01");
        taskManager.newEpic("epic 1", "description epic 1");
        taskManager.newSubtask("subtask 1", "description subtask 1", "epic 1", 20, "02.02.2020 02:02");
        List<Task> tasks = new ArrayList<>();
        tasks.add(taskManager.getTask(100000));
        tasks.add(taskManager.getEpic(100001));
        tasks.add(taskManager.getSubtask(100002));
        assertEquals(tasks, taskManager.getHistory());
    }

    @Test
    void newTask() {
        assertTrue(taskManager.getTasks().isEmpty());
        taskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:01");
        HashMap<Integer, Task> tasks= new HashMap<>();
        tasks.put(1, taskManager.getTask(100000));
        assertEquals(tasks.get(1), taskManager.getTask(100000));
        assertNull(taskManager.getTask(100003));
        taskManager.newTask("task 2", "description task 2", 10, "01.01.1010 00:59");
        taskManager.newTask("task 3", "description task 3", 20, "01.01.1010 00:59");
        taskManager.newTask("task 4", "description task 4", 2, "01.01.1010 01:05");
        taskManager.newTask("task 5", "description task 5", 10, "01.01.1010 01:05");
        taskManager.newTask("task 6", "description task 6", 10, "01.01.1010 01:01");
        assertEquals(1, taskManager.getTasks().size());
        taskManager.newTask("task 7", "description task 7", 0, "");
        tasks.put(2, taskManager.getTask(100001));
        assertEquals(tasks.get(2), taskManager.getTask(100001));
    }

    @Test
    void newEpic() {
        assertTrue(taskManager.getEpics().isEmpty());
        taskManager.newEpic("epic 1", "description epic 1");
        HashMap<Integer, Task> tasks= new HashMap<>();
        tasks.put(1, taskManager.getEpic(100000));
        assertEquals(tasks.get(1), taskManager.getEpic(100000));
        assertNull(taskManager.getEpic(100003));
    }

    @Test
    void newSubtask() {
        assertTrue(taskManager.getSubtasks().isEmpty());
        taskManager.newEpic("epic 1", "description epic 1");
        taskManager.newSubtask("sub 1", "description sub 1", "epic 2", 10, "01.01.1010 01:01");
        assertTrue(taskManager.getSubtasks().isEmpty());
        taskManager.newSubtask("sub 1", "description sub 1", "epic 1", 10, "01.01.1010 01:01");
        HashMap<Integer, Task> tasks= new HashMap<>();
        tasks.put(1, taskManager.getEpic(100000));
        tasks.put(2, taskManager.getSubtask(100001));
        assertEquals(tasks.get(2), taskManager.getSubtask(100001));
        assertNull(taskManager.getSubtask(100003));
        taskManager.newSubtask("sub 2", "description sub 2", "epic 1",10, "01.01.1010 00:59");
        taskManager.newSubtask("sub 3", "description sub 3", "epic 1",20, "01.01.1010 00:59");
        taskManager.newSubtask("sub 4", "description sub 4", "epic 1",2, "01.01.1010 01:05");
        taskManager.newSubtask("sub 5", "description sub 5", "epic 1",10, "01.01.1010 01:05");
        taskManager.newSubtask("sub 6", "description sub 6", "epic 1",10, "01.01.1010 01:01");
        assertEquals(1, taskManager.getSubtasks().size());
        taskManager.newSubtask("sub 7", "description sub 7", "epic 1",0, "");
        tasks.put(3, taskManager.getSubtask(100002));
        assertEquals(tasks.get(3), taskManager.getSubtask(100002));
        assertEquals(taskManager.getSubtask(100001).getStartTime(), taskManager.getEpic(100000).getStartTime());
        assertEquals(taskManager.getSubtask(100001).getDuration(), taskManager.getEpic(100000).getDuration());
    }

    @Test
    void getTasks() {
        taskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:01");
        taskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:21");
        HashMap<Integer, Task> tasks= new HashMap<>();
        tasks.put(100000,taskManager.getTask(100000));
        tasks.put(100001,taskManager.getTask(100001));
        assertEquals(tasks, taskManager.getTasks());
    }

    @Test
    void getEpics() {
        taskManager.newEpic("epic 1", "description epic 1");
        taskManager.newEpic("epic 2", "description epic 2");
        HashMap<Integer, Task> tasks= new HashMap<>();
        tasks.put(100000,taskManager.getEpic(100000));
        tasks.put(100001,taskManager.getEpic(100001));
        assertEquals(tasks, taskManager.getEpics());
    }

    @Test
    void getSubtasks() {
        taskManager.newEpic("epic 1", "description epic 1");
        taskManager.newSubtask("sub 1", "description sub 1", "epic 1", 10, "01.01.1010 01:01");
        taskManager.newSubtask("sub 2", "description sub 2", "epic 1", 10, "01.01.1010 01:21");
        HashMap<Integer, Task> tasks= new HashMap<>();
        tasks.put(100001,taskManager.getSubtask(100001));
        tasks.put(100002,taskManager.getSubtask(100002));
        assertEquals(tasks, taskManager.getSubtasks());
    }

    @Test
    void clearTasks() {
        taskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:01");
        taskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:21");
        assertFalse(taskManager.getTasks().isEmpty());
        taskManager.clearTasks();
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void clearEpics() {
        taskManager.newEpic("epic 1", "description epic 1");
        taskManager.newEpic("epic 2", "description epic 2");
        assertFalse(taskManager.getEpics().isEmpty());
        taskManager.clearEpics();
        assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void clearSubtasks() {
        taskManager.newEpic("epic 1", "description epic 1");
        taskManager.newSubtask("sub 1", "description sub 1", "epic 1", 10, "01.01.1010 01:01");
        taskManager.newSubtask("sub 2", "description sub 2", "epic 1", 10, "01.01.1010 01:21");
        assertFalse(taskManager.getSubtasks().isEmpty());
        taskManager.clearSubtasks();
        assertTrue(taskManager.getSubtasks().isEmpty());
    }

    @Test
    void getTask() {
        taskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:01");
        Task task = taskManager.getTask(100000);
        assertNull(taskManager.getTask(100001));
        assertEquals(task, taskManager.getTask(100000));
    }

    @Test
    void getEpic() {
        taskManager.newEpic("epic 1", "description epic 1");
        Epic epic = taskManager.getEpic(100000);
        assertNull(taskManager.getEpic(100001));
        assertEquals(epic, taskManager.getEpic(100000));
    }

    @Test
    void getSubtask() {
        taskManager.newEpic("epic 1", "description epic 1");
        taskManager.newSubtask("sub 1", "description sub 1", "epic 1", 10, "01.01.1010 01:01");
        Subtask subtask = taskManager.getSubtask(100001);
        assertNull(taskManager.getSubtask(100002));
        assertEquals(subtask, taskManager.getSubtask(100001));
    }

    @Test
    void updateTask() {
        taskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:01");
        Task task = taskManager.getTask(100000);
        taskManager.updateTask(100001,"task 123", "description task 123", Status.IN_PROGRESS,10, "12.12.1212 12:12");
        assertEquals(task, taskManager.getTask(100000));
        taskManager.updateTask(100000,"task 123", "description task 123", Status.IN_PROGRESS,10, "12.12.1212 12:12");
        assertNotEquals(task, taskManager.getTask(100000));
    }

    @Test
    void updateEpic() {
        taskManager.newEpic("epic 1", "description epic 1");
        Epic epic = new Epic(100000, Type.EPIC, "epic 1", "description epic 1", Status.NEW, 0,"");
        taskManager.updateEpic(100001,"epic 123", "description epic 123");
        assertEquals(epic, taskManager.getEpic(100000));
        taskManager.updateEpic(100000,"task 123", "description task 123");
        assertNotEquals(epic, taskManager.getEpic(100000));
    }

    @Test
    void updateSubtask() {
        taskManager.newEpic("epic 1", "description epic 1");
        taskManager.newSubtask("sub 1", "description sub 1", "epic 1", 10, "01.01.1010 01:01");
        Subtask subtask = taskManager.getSubtask(100001);
        taskManager.updateSubtask(100003, "sub 21", "description sub 21", "epic 21", Status.IN_PROGRESS, 10, "01.01.1010 01:01");
        assertEquals(subtask, taskManager.getSubtask(100001));
        taskManager.updateSubtask(100001, "sub 21", "description sub 21", "epic 1", Status.IN_PROGRESS, 10, "11.11.1111 11:11");
        assertNotEquals(subtask, taskManager.getSubtask(100001));
        assertEquals(taskManager.getSubtask(100001).getStatus(), taskManager.getEpic(100000).getStatus());
    }

    @Test
    void deleteTask() {
        taskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:01");
        taskManager.deleteTask(100001);
        assertTrue(taskManager.getTasks().containsValue(taskManager.getTask(100000)));
        taskManager.deleteTask(100000);
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void deleteEpic() {
        taskManager.newEpic("epic 1", "description epic 1");
        taskManager.deleteEpic(100001);
        assertTrue(taskManager.getEpics().containsValue(taskManager.getEpic(100000)));
        taskManager.deleteEpic(100000);
        assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void deleteSubtask() {
        taskManager.newEpic("epic 1", "description epic 1");
        taskManager.newSubtask("sub 1", "description sub 1", "epic 1", 10, "01.01.1010 01:01");
        taskManager.deleteSubtask(100003);
        assertTrue(taskManager.getSubtasks().containsValue(taskManager.getSubtask(100001)));
        taskManager.deleteSubtask(100001);
        assertTrue(taskManager.getSubtasks().isEmpty());
        assertTrue(taskManager.getEpic(100000).getSubtaskIdList().isEmpty());
    }

    @Test
    void loadFromFile() throws IOException {
        TaskManager taskManager = new FileBackedTasksManager(file);
        assertTrue(taskManager.getHistory().isEmpty());
        assertTrue(taskManager.getTasks().isEmpty());
        assertTrue(taskManager.getEpics().isEmpty());
        assertTrue(taskManager.getSubtasks().isEmpty());
        taskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:01");
        taskManager.newEpic("epic 1", "description epic 1");
        taskManager.newSubtask("sub 1", "description sub 1", "epic 1", 10, "12.12.1212 12:12");
        taskManager.getTask(100000);
        taskManager.getEpic(100001);
        taskManager.getSubtask(100002);
        assertFalse(taskManager.getHistory().isEmpty());
        assertFalse(taskManager.getTasks().isEmpty());
        assertFalse(taskManager.getEpics().isEmpty());
        assertFalse(taskManager.getSubtasks().isEmpty());
        }

    @Test
    void getEndTime() {
        taskManager.newTask("task 1", "description task 1", 10, "01.01.1010 01:01");
        taskManager.newEpic("epic 1", "description epic 1");
        taskManager.newSubtask("sub 1", "description sub 1", "epic 1", 10, "12.12.1212 12:12");
        assertEquals("Такой задачи не существует", taskManager.getEndTime(100007));
        assertEquals("Время завершения задачи - 01.01.1010 01:11", taskManager.getEndTime(100000));
        assertEquals("Время завершения задачи - 12.12.1212 12:22", taskManager.getEndTime(100001));
        assertEquals("Время завершения задачи - 12.12.1212 12:22", taskManager.getEndTime(100002));
    }

    @Test
    void getPrioritizedTasks() {
        assertTrue(taskManager.getPrioritizedTasks().isEmpty());
        taskManager.newTask("task 1", "Описание task 1", 10, "01.01.1010 01:01");
        taskManager.newTask("task 2", "Описание task 2", 10, "01.01.1010 02:01");
        taskManager.newTask("task 3", "Описание task 3", 10, "01.01.1010 01:13");
        taskManager.newTask("task 5", "Описание task 5", 0, "");
        taskManager.newTask("task 6", "Описание task 6", 0, "");
        Set<Task> tasks = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Task::getId));
        tasks.add(taskManager.getTask(100000));
        tasks.add(taskManager.getTask(100001));
        tasks.add(taskManager.getTask(100002));
        tasks.add(taskManager.getTask(100003));
        tasks.add(taskManager.getTask(100004));
        assertEquals(tasks, taskManager.getPrioritizedTasks());
    }
}