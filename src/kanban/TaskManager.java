package kanban;

import java.util.List;

public interface TaskManager {

    void printHistory();
    void newTask(String name, String description);
    void newEpic(String name, String description);
    void newSubtask(String name, String description, int epicId);
    void printTasks();
    void printEpics();
    void printSubtasks();
    void clearTasks();
    void clearEpics();
    void clearSubtasks();
    Task getTask(int id);
    Task getEpic(int id);
    Task getSubtask(int id);
    int getEpicId(String epicName);
    void updateTask(int id, Task task);
    void updateEpic(int id, Epic epic);
    void updateSubtask(int id, Subtask subtask);
    void deleteTask(int id);
    void deleteEpic(int id);
    void deleteSubtask(int id);
    void printEpicsSubtasks(int id);
}
