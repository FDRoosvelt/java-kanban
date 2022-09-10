package kanban;

import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskManager {

    List<Task> getHistory();
    void newTask(String name, String description, int duration, String startTime);
    void newEpic(String name, String description);
    void newSubtask(String name, String description, String epicName, int duration, String startTime);
    Map<Integer, Task> getTasks();
    Map<Integer, Epic> getEpics();
    Map<Integer, Subtask> getSubtasks();
    void clearTasks();
    void clearEpics();
    void clearSubtasks();
    Task getTask(int id);
    Epic getEpic(int id);
    Subtask getSubtask(int id);
    void updateTask(int id, String name, String description, Status status, int duration, String startTime);
    void updateEpic(int id, String name, String description);
    void updateSubtask(int id, String name, String description, String epicName, Status status, int duration, String startTime);
    void deleteTask(int id);
    void deleteEpic(int id);
    void deleteSubtask(int id);
    void printEpicsSubtasks(int id);
    String getEndTime(int id);
    Set<Task> getPrioritizedTasks();
}
