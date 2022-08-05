import kanban.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TaskManager taskManager = Managers.getDefault();
        /*taskManager.newTask("task 1", "Описание эпика 1");
        taskManager.newEpic("epic 1", "Описание эпика 1");
        taskManager.newSubtask("sub 1", "Описание эпика 1", taskManager.getEpicId("epic 1"));
        taskManager.newTask("task 1", "Описание эпика 1");
        taskManager.newEpic("epic 1", "Описание эпика 1");
        taskManager.newSubtask("sub 1", "Описание эпика 1", taskManager.getEpicId("epic 1"));
        taskManager.newTask("task 1", "Описание эпика 1");
        taskManager.newEpic("epic 1", "Описание эпика 1");
        taskManager.newSubtask("sub 1", "Описание эпика 1", taskManager.getEpicId("epic 1"));
        taskManager.newTask("task 1", "Описание эпика 1");
        taskManager.newEpic("epic 1", "Описание эпика 1");
        taskManager.newSubtask("sub 1", "Описание эпика 1", taskManager.getEpicId("epic 1"));
        taskManager.newTask("task 1", "Описание эпика 1");
        taskManager.newEpic("epic 1", "Описание эпика 1");
        taskManager.newSubtask("sub 1", "Описание эпика 1", taskManager.getEpicId("epic 1"));*/
        taskManager.getTask(100000);
        taskManager.getTask(100000);
        taskManager.getSubtask(100002);
        taskManager.getEpic(100001);
        taskManager.getSubtask(100011);
        taskManager.getSubtask(100014);
        taskManager.getEpic(100013);
        taskManager.getTask(100012);
        taskManager.getEpic(100010);
        taskManager.getTask(100009);
        taskManager.getSubtask(100008);
        taskManager.getEpic(100007);
        taskManager.getTask(100006);
        taskManager.getSubtask(100005);
        taskManager.getEpic(100004);
        taskManager.getTask(100003);
        taskManager.getTask(100000);
        taskManager.getEpic(100001);
        System.out.println("\nHistory");
        taskManager.printHistory();
    }
}
