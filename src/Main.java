import kanban.Managers;
import kanban.TaskManager;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TaskManager taskManager = Managers.getDefault();
        taskManager.newTask("task 1", "Описание task 1", 10, "01.01.1010 01:01");
        taskManager.newTask("task 2", "Описание task 2", 10, "01.01.1010 02:01");
        taskManager.newTask("task 3", "Описание task 3", 10, "01.01.1010 01:13");
        taskManager.newTask("task 4", "Описание task 4", 10, "01.01.1010 00:55");
        taskManager.newTask("task 6", "Описание task 6", 30, "01.01.1010 00:55");
        taskManager.newTask("task 7", "Описание task 7", 10, "01.01.1010 01:05");
        taskManager.newTask("task 8", "Описание task 8", 10, "01.01.1010 01:12");
        taskManager.newTask("task 9", "Описание task 9", 10, "01.01.1010 01:11");
        taskManager.newTask("task 5", "Описание task 5", 0, "");
        taskManager.newTask("task 10", "Описание task 10", 0, "");

        taskManager.getPrioritizedTasks().forEach(System.out::println);

    }
}
