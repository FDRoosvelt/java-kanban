package kanban;

public class Managers<T extends TaskManager> {

    T taskManager;

    public T getDefault() {
        return taskManager;
    }
}
