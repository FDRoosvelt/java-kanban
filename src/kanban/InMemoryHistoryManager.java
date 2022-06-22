package kanban;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    static List<Task> history = new ArrayList<>();

    @Override
    public void addHistory(Task task) {
        if (history.size()>9) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Task task : history) {
            result.append(task).append("\n");
        }
        return result.toString();
    }
}
