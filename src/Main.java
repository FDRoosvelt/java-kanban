import kanban.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        taskManager.newEpic("Эпик 1", "Описание эпика 1");
        taskManager.newEpic("Эпик 2", "Описание эпика 2");
        taskManager.newSubtask("Подзадача эпика 1-1", "Описание подзадачи 1-1", taskManager.getEpicId("Эпик 1"));
        taskManager.newSubtask("Подзадача эпика 1-2", "Описание подзадачи 1-2", taskManager.getEpicId("Эпик 1"));
        taskManager.newSubtask("Подзадача эпика 1-3", "Описание подзадачи 1-3", taskManager.getEpicId("Эпик 1"));
        System.out.println(taskManager);

        taskManager.getEpic(100001);
        taskManager.getEpic(100001);
        taskManager.getEpic(100001);
        taskManager.getSubtask(100003);
        taskManager.getSubtask(100002);
        taskManager.getSubtask(100002);
        taskManager.getSubtask(100002);
        taskManager.getSubtask(100002);
        taskManager.getSubtask(100004);
        taskManager.getSubtask(100003);
        taskManager.getEpic(100001);
        taskManager.getSubtask(100004);
        taskManager.getSubtask(100004);
        taskManager.getSubtask(100004);

        System.out.println("");
        taskManager.printHistory();


    }
}
