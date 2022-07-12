package kanban;

import java.util.List;

public interface TaskManager {

    void printHistory();
    void newTask(Task task);
    void newEpic(Epic epic);
    void newSubtask(Subtask subtask);
    void printTasks();
    void printEpics();
    void printSubtasks();
    void clearTasks();
    void clearEpics();
    void clearSubtasks();
    void getTask(int id);
    void getEpic(int id);
    void getSubtask(int id);
    Integer getEpicId(Epic epic);
    void updateTask(int id, Task task);
    void updateEpic(int id, Epic epic);
    void updateSubtask(int id, Subtask subtask);
    void deleteTask(int id);
    void deleteEpic(int id);
    void deleteSubtask(int id);
    void printEpicsSubtasks(int id);
}
