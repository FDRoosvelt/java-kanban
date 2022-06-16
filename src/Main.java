import kanban.*;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        Subtask subtask11 = new Subtask("Подзадача эпика 1-1", "Описание подзадачи 1-1", 100002);
        Subtask subtask12 = new Subtask("Подзадача эпика 1-2", "Описание подзадачи 1-2",100002);
        Subtask subtask21 = new Subtask("Подзадача эпика 2-1", "Описание подзадачи 2-1", 100003);
        manager.newTask(task1);
        manager.newTask(task2);
        manager.newEpic(epic1);
        manager.newEpic(epic2);
        manager.newSubtask(subtask11);
        manager.newSubtask(subtask12);
        manager.newSubtask(subtask21);
        System.out.println(manager);
        Subtask subtask1 = new Subtask("Новое название подзадачи эпика 1-1", "Новое описание подзадачи 1-1", "DONE", 100002);
        Subtask subtask2 = new Subtask("Новое название подзадачи эпика 1-2", "Новое описание подзадачи 1-2", "DONE", 100002);
        manager.updateSubtask(100004, subtask1);
        manager.updateSubtask(100005, subtask2);
        manager.printEpicsSubtasks(100002);
        manager.deleteTask(100000);
        manager.deleteSubtask(100004);
        manager.printEpicsSubtasks(100002);
        manager.deleteTask(100002);
    }
}
