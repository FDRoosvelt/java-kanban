package kanban;

public class Managers{

    static TaskManager taskManager = new InMemoryTaskManager();

    static public TaskManager getDefault() {
        return taskManager;
    }
}
