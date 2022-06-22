package kanban;

import java.util.HashMap;


public class InMemoryTaskManager implements TaskManager {

    int id = 100000;
    HashMap<Integer, Task> taskStorage = new HashMap<>();
    HashMap<Integer, Epic> epicStorage = new HashMap<>();
    HashMap<Integer, Subtask> subtaskStorage = new HashMap<>();
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    @Override
    public void newTask(Task task) {
        taskStorage.put(id, task);
        id++;
    }

    @Override
    public void newEpic(Epic epic) {
        epicStorage.put(id, epic);
        id++;
    }

    @Override
    public void newSubtask(Subtask subtask) {
        if (epicStorage.containsKey(subtask.epicId)) {
            Epic addId = epicStorage.get(subtask.epicId);
            addId.addSubtask(id);
            subtaskStorage.put(id, subtask);
            id++;
        } else {
            System.out.println("Ошибка при создании подзадачи.");
        }
    }

    @Override
    public void printTasks() {
        for (Integer key : taskStorage.keySet()) {
            System.out.println("id = " + key + "; Задача = " + taskStorage.get(key));
        }
    }

    @Override
    public void printEpics() {
        for (Integer key : epicStorage.keySet()) {
            System.out.println("id = " + key + "; Эпик = " + epicStorage.get(key));
        }
    }

    @Override
    public void printSubtasks() {
        for (Integer key : subtaskStorage.keySet()) {
            System.out.println("id = " + key + "; Подзадача = " + subtaskStorage.get(key));
        }
    }

    @Override
    public void clearTasks() {
        taskStorage.clear();
    }

    @Override
    public void clearEpics() {
        epicStorage.clear();
    }

    @Override
    public void clearSubtasks() {
        subtaskStorage.clear();
    }

    @Override
    public void getTask(int id) {
        if (taskStorage.containsKey(id)) {
            System.out.println("id = " + id + "; Задача = " + taskStorage.get(id));
            inMemoryHistoryManager.addHistory(taskStorage.get(id));
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void getEpic(int id) {
        if (epicStorage.containsKey(id)) {
            System.out.println("id = " + id + "; Задача = " + epicStorage.get(id));
            inMemoryHistoryManager.addHistory(epicStorage.get(id));
        } else {
            System.out.println("Такого эпика нет");
        }
    }

    @Override
    public void getSubtask(int id) {
        if (subtaskStorage.containsKey(id)) {
            System.out.println("id = " + id + "; Задача = " + subtaskStorage.get(id));
            inMemoryHistoryManager.addHistory(subtaskStorage.get(id));
        } else {
            System.out.println("Такой подзадачи нет");
        }
    }

    @Override
    public Integer getEpicId(Epic epic) {
        Integer epicId = 0;
        for (Integer key : epicStorage.keySet()) {
            if (epicStorage.get(key).equals(epic)) {
                epicId = key;
                break;
            }
        }
        return epicId;
    }

    @Override
    public void updateTask(int id, Task task) {
        if (taskStorage.containsKey(id)) {
            taskStorage.put(id, task);
        } else {
            System.out.println("Нет такой задачи");
        }
    }

    @Override
    public void updateEpic(int id, Epic epic) {
        if (epicStorage.containsKey(id)) {
            epicStorage.put(id, epic);
        } else {
            System.out.println("Нет такого эпика");
        }
    }

    @Override
    public void updateSubtask(int id, Subtask subtask) {
        if (subtaskStorage.containsKey(id)) {
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
    public void deleteTask(int id) {
        if (taskStorage.containsKey(id)) {
            taskStorage.remove(id);
            System.out.println("Задача " + id + " удалена");
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void deleteEpic(int id) {
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
    public void deleteSubtask(int id) {
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
            System.out.println("id = " + id + "; Эпик = " + epicStorage.get(id));
            Epic printSubtasks = epicStorage.get(id);
            for (int subtask : printSubtasks.subtaskIdList) {
                System.out.println("id = " + subtask + "; Подзадача = " + subtaskStorage.get(subtask));
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (Integer idTask : taskStorage.keySet()) {
            result.append("id = ").append(idTask).append("; Задача = ").append(taskStorage.get(idTask)).append("\n");
        }
        for (Integer idEpic : epicStorage.keySet()) {
            result.append("id = ").append(idEpic).append("; Задача = ").append(epicStorage.get(idEpic)).append("\n");
            for (Integer idSubtask : subtaskStorage.keySet()) {
                if (idEpic.equals(subtaskStorage.get(idSubtask).epicId)) {
                    result.append("id = ").append(idSubtask).append("; Задача = ").append(subtaskStorage.get(idSubtask)).append("\n");
                }
            }
        }
        return result.toString();
    }
}
