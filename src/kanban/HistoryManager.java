package kanban;

import kanban.tasks.Task;

import java.util.List;

public interface HistoryManager {

    void addHistory(int id, Task task);
    public void remove(Task task);
    List<Task> getHistory();
    void loadHistory(List<Task> list);
    void linkLast(int id, Task task);
    void getTasks();
}
