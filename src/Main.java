import kanban.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TaskManager taskManager = Managers.getDefault();
        taskManager.newTask("task 1", "Описание эпика 1");
        taskManager.newEpic("epic 1", "Описание эпика 1");
        taskManager.newSubtask("sub 1", "Описание эпика 1", taskManager.getEpicId("epic 1"));
        taskManager.getTask(100000);
        taskManager.getTask(100000);
        taskManager.getSubtask(100002);
        taskManager.getEpic(100001);
    }
}
