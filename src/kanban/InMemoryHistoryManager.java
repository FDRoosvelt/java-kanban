package kanban;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    static List<Task> history = new ArrayList<>();

    @Override
    public void addHistory(Task task) {
        if (history.size()<10) {
            history.add(task);
        } else {
            history.remove(0);
            history.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (Task task : history) {
            result.append(task).append("\n");
        }
        return result.toString();
    }
}
