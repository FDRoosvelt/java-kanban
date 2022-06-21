package kanban;

public interface TaskManager {


    public void newTask(Task task);
    public void newEpic(Epic epic);
    public void newSubtask(Subtask subtask);
    public void printTasks();
    public void printEpics();
    public void printSubtasks();
    public void clearTasks();
    public void clearEpics();
    public void clearSubtasks();
    public void getTask(int id);
    public void getEpic(int id);
    public void getSubtask(int id);
    public void updateTask(int id, Task task);
    public void updateEpic(int id, Epic epic);
    public void updateSubtask(int id, Subtask subtask);
    public void deleteTask(int id);
    public void deleteEpic(int id);
    public void deleteSubtask(int id);
    public void printEpicsSubtasks(int id);
}
