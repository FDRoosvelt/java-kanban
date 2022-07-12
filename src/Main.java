import kanban.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.newEpic(epic1);
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        taskManager.newEpic(epic2);
        Subtask subtask11 = new Subtask("Подзадача эпика 1-1", "Описание подзадачи 1-1", taskManager.getEpicId(epic1));
        taskManager.newSubtask(subtask11);
        Subtask subtask12 = new Subtask("Подзадача эпика 1-2", "Описание подзадачи 1-2", taskManager.getEpicId(epic1));
        taskManager.newSubtask(subtask12);
        Subtask subtask13 = new Subtask("Подзадача эпика 1-3", "Описание подзадачи 1-3", taskManager.getEpicId(epic1));
        taskManager.newSubtask(subtask13);
        System.out.println(taskManager);
        taskManager.getEpic(100001);
        taskManager.getEpic(100001);
        taskManager.getEpic(100001);
        taskManager.getSubtask(100003);
        taskManager.getSubtask(100002);
        taskManager.getSubtask(100002);
        taskManager.getSubtask(100002);
        taskManager.getSubtask(100002);
        taskManager.getSubtask(100002);
        taskManager.getSubtask(100004);
        taskManager.getSubtask(100003);
        taskManager.getEpic(100001);
        taskManager.getEpic(100000);
        taskManager.getSubtask(100004);
        taskManager.getSubtask(100004);
        taskManager.getSubtask(100004);
        taskManager.getSubtask(100004);
        System.out.println("");
        taskManager.printHistory();




        //taskManager.getEpic(100001);
        //taskManager.getEpic(100001);
        //taskManager.getEpic(100001);
        //taskManager.getEpic(100001);
        //taskManager.getEpic(100001);
        //taskManager.getEpic(100001);




    }
}
