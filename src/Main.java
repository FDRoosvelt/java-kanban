import kanban.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        taskManager.newTask(task1);
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        taskManager.newTask(task2);
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.newEpic(epic1);
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        taskManager.newEpic(epic2);
        Subtask subtask11 = new Subtask("Подзадача эпика 1-1", "Описание подзадачи 1-1", taskManager.getEpicId(epic1));
        taskManager.newSubtask(subtask11);
        Subtask subtask12 = new Subtask("Подзадача эпика 1-2", "Описание подзадачи 1-2", taskManager.getEpicId(epic1));
        taskManager.newSubtask(subtask12);
        Subtask subtask21 = new Subtask("Подзадача эпика 2-1", "Описание подзадачи 2-1", taskManager.getEpicId(epic2));
        taskManager.newSubtask(subtask21);
        System.out.println(taskManager);
        Subtask subtask1 = new Subtask("Новое название подзадачи эпика 1-1", "Новое описание подзадачи 1-1", Status.DONE, 100002);
        Subtask subtask2 = new Subtask("Новое название подзадачи эпика 1-2", "Новое описание подзадачи 1-2", Status.DONE, 100002);
        taskManager.updateSubtask(100004, subtask1);
        taskManager.updateSubtask(100005, subtask2);
        taskManager.printEpicsSubtasks(100002);
        taskManager.deleteTask(100000);
        taskManager.deleteSubtask(100004);
        taskManager.printEpicsSubtasks(100002);
        taskManager.deleteTask(100002);
    }
}
