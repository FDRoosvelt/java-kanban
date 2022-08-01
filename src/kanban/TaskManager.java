package kanban;

import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.IOException;

public interface TaskManager {

    void printHistory();
    void newTask(String name, String description) throws IOException;
    void newEpic(String name, String description) throws IOException;
    void newSubtask(String name, String description, int epicId) throws IOException;
    void printTasks();
    void printEpics();
    void printSubtasks();
    void clearTasks() throws IOException;
    void clearEpics() throws IOException;
    void clearSubtasks() throws IOException;
    Task getTask(int id) throws IOException;
    Task getEpic(int id) throws IOException;
    Task getSubtask(int id) throws IOException;
    int getEpicId(String epicName);
    void updateTask(int id, Task task) throws IOException;
    void updateEpic(int id, Epic epic) throws IOException;
    void updateSubtask(int id, Subtask subtask) throws IOException;
    void deleteTask(int id) throws IOException;
    void deleteEpic(int id) throws IOException;
    void deleteSubtask(int id) throws IOException;
    void printEpicsSubtasks(int id);
    void loadFromFile() throws IOException;
}
