import kanban.*;

public class Main {

    public static void main(String[] args) {
        InMemoryHistoryManager history = new InMemoryHistoryManager();
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        Subtask subtask11 = new Subtask("Подзадача эпика 1-1", "Описание подзадачи 1-1", 100002);
        Subtask subtask12 = new Subtask("Подзадача эпика 1-2", "Описание подзадачи 1-2",100002);
        Subtask subtask21 = new Subtask("Подзадача эпика 2-1", "Описание подзадачи 2-1", 100003);
        inMemoryTaskManager.newTask(task1);
        inMemoryTaskManager.newTask(task2);
        inMemoryTaskManager.newEpic(epic1);
        inMemoryTaskManager.newEpic(epic2);
        inMemoryTaskManager.newSubtask(subtask11);
        inMemoryTaskManager.newSubtask(subtask12);
        inMemoryTaskManager.newSubtask(subtask21);
        System.out.println(inMemoryTaskManager);
        Subtask subtask1 = new Subtask("Новое название подзадачи эпика 1-1", "Новое описание подзадачи 1-1", Status.DONE, 100002);
        Subtask subtask2 = new Subtask("Новое название подзадачи эпика 1-2", "Новое описание подзадачи 1-2", Status.DONE, 100002);
        inMemoryTaskManager.updateSubtask(100004, subtask1);
        inMemoryTaskManager.updateSubtask(100005, subtask2);
        inMemoryTaskManager.printEpicsSubtasks(100002);
        inMemoryTaskManager.deleteTask(100000);
        inMemoryTaskManager.deleteSubtask(100004);
        inMemoryTaskManager.printEpicsSubtasks(100002);
        inMemoryTaskManager.deleteTask(100002);
    }
}
