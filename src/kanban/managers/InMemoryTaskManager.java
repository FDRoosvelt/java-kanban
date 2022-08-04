package kanban.managers;

import kanban.HistoryManager;
import kanban.Status;
import kanban.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.IOException;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    static int id = 100000;
    static HashMap<Integer, Task> taskStorage = new HashMap<>();
    static HashMap<Integer, Epic> epicStorage = new HashMap<>();
    static HashMap<Integer, Subtask> subtaskStorage = new HashMap<>();
    static HistoryManager historyManager = new InMemoryHistoryManager();

    public void printHistory() {
        for (Task element : historyManager.getHistory()) {
            System.out.println("Задача = "+element);
        }
    }

    @Override
    public void newTask(String name, String description) throws IOException {
        Task task = new Task(id, name, description, Status.NEW);
        taskStorage.put(id, task);
        id++;
    }

    @Override
    public void newEpic(String name, String description) throws IOException {
        Epic epic = new Epic(id, name, description, Status.NEW);
        epicStorage.put(id, epic);
        id++;
    }

    @Override
    public void newSubtask(String name, String description, int epicId) throws IOException {
        Subtask subtask = new Subtask(id, name, description, epicId, Status.NEW);
        if (epicStorage.containsKey(subtask.epicId)) {
            Epic epic = epicStorage.get(subtask.epicId);
            epic.addSubtask(id);
            subtaskStorage.put(id, subtask);
            id++;
        } else {
            System.out.println("Ошибка при создании подзадачи.");
        }
    }

    @Override
    public void printTasks() {
        for (Integer key : taskStorage.keySet()) {
            System.out.println(taskStorage.get(key));
        }
    }

    @Override
    public void printEpics() {
        for (Integer key : epicStorage.keySet()) {
            System.out.println(epicStorage.get(key));
        }
    }

    @Override
    public void printSubtasks() {
        for (Integer key : subtaskStorage.keySet()) {
            System.out.println(subtaskStorage.get(key));
        }
    }

    @Override
    public void clearTasks() throws IOException {
        taskStorage.clear();
    }

    @Override
    public void clearEpics() throws IOException {
        epicStorage.clear();
    }

    @Override
    public void clearSubtasks() throws IOException {
        subtaskStorage.clear();
    }

    @Override
    public Task getTask(int id) throws IOException {
        if (taskStorage.containsKey(id)) {
            historyManager.addHistory(id, taskStorage.get(id));
            return taskStorage.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Task getEpic(int id) throws IOException {
        if (!epicStorage.containsKey(id)) {
            return null;
        }
        historyManager.addHistory(id, epicStorage.get(id));
        return epicStorage.get(id);
    }

    @Override
    public Task getSubtask(int id) throws IOException {
        if (subtaskStorage.containsKey(id)) {
            historyManager.addHistory(id, subtaskStorage.get(id));
            return subtaskStorage.get(id);
        } else {
            return null;
        }
    }

    @Override
    public int getEpicId(String epicName) {
        Integer epicId = 0;
        for (Integer key : epicStorage.keySet()) {
            if (epicStorage.get(key).name.equals(epicName)) {
                epicId = key;
                break;
            }
        }
        return epicId;
    }

    @Override
    public void updateTask(int id, Task task) throws IOException {
        if (taskStorage.containsKey(id)) {
            task.id=id;
            taskStorage.put(id, task);
        } else {
            System.out.println("Нет такой задачи");
        }
    }

    @Override
    public void updateEpic(int id, Epic epic) throws IOException {
        if (epicStorage.containsKey(id)) {
            epic.id=id;
            epicStorage.put(id, epic);
        } else {
            System.out.println("Нет такого эпика");
        }
    }

    @Override
    public void updateSubtask(int id, Subtask subtask) throws IOException {
        if (subtaskStorage.containsKey(id)) {
            subtask.id=id;
            subtaskStorage.put(id, subtask);
            logicStatus(subtask.epicId);
        } else {
            System.out.println("Нет такой подзадачи");
        }
    }

    protected void logicStatus(int epicId) {
        int doneStatus = 0;
        int newStatus = 0;
        Epic epic = epicStorage.get(epicId);
        for (int id : epic.subtaskIdList) {
            Subtask subtask = subtaskStorage.get(id);
            if (subtask.status.equals(Status.NEW)) {
                newStatus++;
            } else if (subtask.status.equals(Status.DONE)) {
                doneStatus++;
            }
        }
        if (newStatus == epic.subtaskIdList.size()) {
            epic.status = Status.NEW;
        } else if (doneStatus == epic.subtaskIdList.size()) {
            epic.status = Status.DONE;
        } else {
            epic.status = Status.IN_PROGRESS;
        }
    }

    @Override
    public void deleteTask(int id) throws IOException {
        if (taskStorage.containsKey(id)) {
            taskStorage.remove(id);
            System.out.println("Задача " + id + " удалена");
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void deleteEpic(int id) throws IOException {
        if (epicStorage.containsKey(id)) {
            Epic epic = epicStorage.get(id);
            for (int index : epic.subtaskIdList) {
                subtaskStorage.remove(index);
            }
            epicStorage.remove(id);
            System.out.println("Эпик " + id + " удален");
        } else {
            System.out.println("Такого эпика нет");
        }
    }

    @Override
    public void deleteSubtask(int id) throws IOException {
        if (subtaskStorage.containsKey(id)) {
            Subtask subtask = subtaskStorage.get(id);
            Epic epic = epicStorage.get(subtask.epicId);
            epic.subtaskIdList.remove((Integer) id);
            subtaskStorage.remove(id);
            System.out.println("Подзадача " + id + " удалена");
        } else {
            System.out.println("Такой подзадачи нет");
        }
    }

    @Override
    public void printEpicsSubtasks(int id) {
        if (epicStorage.containsKey(id)) {
            System.out.println("Эпик = " + epicStorage.get(id));
            Epic printSubtasks = epicStorage.get(id);
            for (int subtask : printSubtasks.subtaskIdList) {
                System.out.println("Подзадача = " + subtaskStorage.get(subtask));
            }
        }
    }

    @Override
    public void loadFromFile() throws IOException {
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (Integer idTask : taskStorage.keySet()) {
            result.append(taskStorage.get(idTask)).append("\n");
        }
        for (Integer idEpic : epicStorage.keySet()) {
            result.append(epicStorage.get(idEpic)).append("\n");
            for (Integer idSubtask : subtaskStorage.keySet()) {
                if (idEpic.equals(subtaskStorage.get(idSubtask).epicId)) {
                    result.append(subtaskStorage.get(idSubtask)).append("\n");
                }
            }
        }

        return result.toString();
    }
}
