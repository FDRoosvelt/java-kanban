package kanban;

import kanban.managers.FileBackedTasksManager;
import kanban.managers.InMemoryHistoryManager;

import java.io.File;
import java.io.IOException;

import static kanban.managers.FileBackedTasksManager.loadFromFile;

public class Managers {
    static File file = new File(System.getProperty("user.dir")+"/src/kanban/data/data.csv");
    static TaskManager taskManager = new FileBackedTasksManager(file);
    static HistoryManager historyManager = new InMemoryHistoryManager();
    static public TaskManager getDefault() throws IOException {
        return taskManager;
    }
    static public HistoryManager getDefaultHistory() {
        return historyManager;
    }
}
