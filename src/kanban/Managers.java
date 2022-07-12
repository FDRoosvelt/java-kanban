package kanban;

public class Managers {

    static TaskManager taskManager = new InMemoryTaskManager();
    static HistoryManager historyManager = new InMemoryHistoryManager();
    static public TaskManager getDefault() {
        return taskManager;
    }
    static public HistoryManager getDefaultHistory() {
        return historyManager;
    }
}
