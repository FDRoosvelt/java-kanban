import kanban.*;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.newTask("Задача 1", "Описание задачи 1");
        manager.newTask("Задача 2", "Описание задачи 2");
        manager.newEpic("Эпик 1", "Описание эпика 1");
        manager.newEpic("Эпик 2", "Описание эпика 2");
        manager.newSubtask("Подзадача эпика 1-1", "Описание подзадачи 1-1", 100002); // у меня программа не падает, ошибок нет
        manager.newSubtask("Подзадача эпика 1-2", "Описание подзадачи 1-2",100002);
        manager.newSubtask("Подзадача эпика 2-1", "Описание подзадачи 2-1", 100003);
        System.out.println(manager);
        Subtask subtask1 = new Subtask("Новое название подзадачи эпика 1-1", "Новое описание подзадачи 1-1", "DONE", 100002);
        manager.updateSubtask(100004, subtask1);
        Subtask subtask2 = new Subtask("Новое название подзадачи эпика 1-2", "Новое описание подзадачи 1-2", "DONE", 100002);
        manager.updateSubtask(100005, subtask2);
        manager.printEpicsSubtasks(100002);
        manager.deleteTask(100000);
        manager.deleteTask(100004);
        manager.printEpicsSubtasks(100002);
        manager.deleteTask(100002);
    }
}
