package kanban;

import kanban.managers.FileBackedTasksManager;
import kanban.managers.InMemoryHistoryManager;

import java.io.IOException;

public class Managers {

    static TaskManager taskManager = new FileBackedTasksManager();
    static HistoryManager historyManager = new InMemoryHistoryManager();
    static public TaskManager getDefault() throws IOException {
        taskManager.loadFromFile();
        return taskManager;
    }
    static public HistoryManager getDefaultHistory() {
        return historyManager;
    }
}
