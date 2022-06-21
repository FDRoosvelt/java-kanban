package kanban;

import java.util.List;

public interface HistoryManager {
    public void addHistory(Task task);
    public List<Task> getHistory();
}
