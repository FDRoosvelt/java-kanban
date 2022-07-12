package kanban;

import java.util.List;

public interface HistoryManager {
    void addHistory(int id, Task task);
    void remove(int id);
    List<Task> getHistory();
}
