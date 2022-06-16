package kanban;

import java.util.HashMap;

public class Manager {
    String[] statusList = {"NEW", "IN_PROGRESS", "DONE"};
    int id = 100000;
    HashMap<Integer, Task> taskStorage = new HashMap<>();
    HashMap<Integer, Epic> epicStorage = new HashMap<>();
    HashMap<Integer, Subtask> subtaskStorage = new HashMap<>();

    public void newTask(Task task) {
        taskStorage.put(id, task);
        id++;
    }

    public void newEpic(Epic epic) {
        epicStorage.put(id, epic);
        id++;
    }

    public void newSubtask(Subtask subtask) {
        if (epicStorage.containsKey(subtask.epicId)) {
            Epic addId = epicStorage.get(subtask.epicId);
            addId.addSubtask(id);
            subtaskStorage.put(id, subtask);
            id++;
        } else {
            System.out.println("Ошибка при создании задачи.");
        }
    }

    public void printTasks() {
        for (Integer key : taskStorage.keySet()) {
            System.out.println("id = " + key + "; Задача = " + taskStorage.get(key));
        }
    }

    public void printEpics() {
        for (Integer key : epicStorage.keySet()) {
            System.out.println("id = " + key + "; Эпик = " + epicStorage.get(key));
        }
    }

    public void printSubtasks() {
        for (Integer key : subtaskStorage.keySet()) {
            System.out.println("id = " + key + "; Подзадача = " + subtaskStorage.get(key));
        }
    }

    public void clearTasks() {
        taskStorage.clear();
    }

    public void clearEpics() {
        epicStorage.clear();
    }

    public void clearSubtasks() {
        subtaskStorage.clear();
    }

    public void findTask(int id) {
        if (taskStorage.containsKey(id)) {
            System.out.println("id = " + id + "; Задача = " + taskStorage.get(id));
        } else {
            System.out.println("Такой задачи нет");
        }
    }
    public void findEpic(int id) {
        if (epicStorage.containsKey(id)) {
            System.out.println("id = " + id + "; Задача = " + epicStorage.get(id));
        } else {
            System.out.println("Такого эпика нет");
        }
    }
    public void findSubtask(int id) {
        if (subtaskStorage.containsKey(id)) {
            System.out.println("id = " + id + "; Задача = " + subtaskStorage.get(id));
        } else {
            System.out.println("Такой подзадачи нет");
        }
    }

    public void updateTask(int id, Task task) {
        if (taskStorage.containsKey(id)) {
            taskStorage.put(id, task);
        } else {
            System.out.println("Нет такой задачи");
        }
    }

    public void updateEpic(int id, Epic epic) {
        if (epicStorage.containsKey(id)) {
            epicStorage.put(id, epic);
        } else {
            System.out.println("Нет такого эпика");
        }
    }

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
            if (statusList[0].equals(subtask.status)) {
                newStatus++;
            } else if (statusList[2].equals(subtask.status)) {
                doneStatus++;
            }
        }
        if (newStatus == epic.subtaskIdList.size()) {
            epic.status = statusList[0];
        } else if (doneStatus == epic.subtaskIdList.size()) {
            epic.status = statusList[2];
        } else {
            epic.status = statusList[1];
        }
    }

    public void deleteTask(int id) {
        if (taskStorage.containsKey(id)) {
            taskStorage.remove(id);
            System.out.println("Задача " + id + " удалена");
        } else {
            System.out.println("Такой задачи нет");
        }
    }

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
