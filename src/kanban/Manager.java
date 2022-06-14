package kanban;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    String[] statusList = {"NEW", "IN_PROGRESS", "DONE"};
    int id = 100000;
    HashMap<Integer, Object> taskStorage = new HashMap<>();

    public void newTask(String name, String description) {
        Task task = new Task(name, description);
        taskStorage.put(id, task);
        id++;
    }

    public void newEpic(String name, String description) {
        Epic task = new Epic(name, description);
        taskStorage.put(id, task);
        id++;
    }

    public void newSubtask(String name, String description, int epicId) {
        if (taskStorage.containsKey(epicId)) {
            Subtask subTask = new Subtask(name, description, epicId);
            Epic addId = (Epic) taskStorage.get(epicId);
            addId.addSubtask(id);
            taskStorage.put(id, subTask);
            id++;
        } else {
            System.out.println("Ошибка при создании задачи.");
        }
    }

    public void printTasks(String type) {
        switch (type) {
            case "Задача":
                for (Integer key : taskStorage.keySet()) {
                    Object object = taskStorage.get(key);
                    if (object.getClass().equals(Task.class)) {
                        System.out.println("id = " + key + "; Задача = " + object);
                    }
                }
                break;
            case "Эпик":
                for (Integer key : taskStorage.keySet()) {
                    Object object = taskStorage.get(key);
                    if (object.getClass().equals(Epic.class)) {
                        System.out.println("id = " + key + "; Эпик = " + object);
                    }
                }
                break;
            case "Подзадача":
                for (Integer key : taskStorage.keySet()) {
                    Object object = taskStorage.get(key);
                    if (object.getClass().equals(Subtask.class)) {
                        System.out.println("id = " + key + "; Подзадача = " + object);
                    }
                }
                break;
            default:
                System.out.println("Таких задач нет");
        }
    }

    public void clearTasks(String type) {
        ArrayList<Integer> removeId = new ArrayList<>();
        switch (type) {
            case "Задача":
                for (Integer key : taskStorage.keySet()) {
                    Object object = taskStorage.get(key);
                    if (object.getClass().equals(Task.class)) {
                        removeId.add(key);
                    }
                }
                for (Integer id : removeId) {
                    taskStorage.remove(id);
                }
                System.out.println("Задачи удалены");
                break;
            case "Эпик":
                for (Integer key : taskStorage.keySet()) {
                    Object object = taskStorage.get(key);
                    if (object.getClass().equals(Epic.class) || object.getClass().equals(Subtask.class)) {
                        removeId.add(key);
                    }
                }
                for (Integer id : removeId) {
                    taskStorage.remove(id);
                }
                System.out.println("Эпики удалены");
                break;
            case "Подзадача":
                for (Integer key : taskStorage.keySet()) {
                    Object object = taskStorage.get(key);
                    if (object.getClass().equals(Subtask.class)) {
                        removeId.add(key);
                    }
                }
                for (Integer id : removeId) {
                    taskStorage.remove(id);
                }
                System.out.println("Подзадачи удалены");
                break;
            default:
                System.out.println("Таких задач нет");
        }
    }

    public void findTask(int id) {
        System.out.println("id = " + id + "; Задача = " + taskStorage.get(id));
    }

    public void updateTask(int id, Task task) {
        if (taskStorage.containsKey(id)) {
            taskStorage.put(id, task);
        } else {
            System.out.println("Нет такой задачи");
        }
    }

    public void updateEpic(int id, Epic epic) {
        if (taskStorage.containsKey(id)) {
            taskStorage.put(id, epic);
        } else {
            System.out.println("Нет такого эпика");
        }
    }


    public void updateSubtask(int id, Subtask subtask) {
        if (taskStorage.containsKey(id)) {
            taskStorage.put(id, subtask);
            logicStatus(subtask.epicId);
        } else {
            System.out.println("Нет такой подзадачи");
        }
    }


    protected void logicStatus(int epicId) {
        int doneStatus = 0;
        int newStatus = 0;
        Epic epic = (Epic) taskStorage.get(epicId);
        for (int idST : epic.subtaskIdList) {
            Subtask subtask = (Subtask) taskStorage.get(idST);
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
            if (taskStorage.get(id).getClass().equals(Task.class)) { //при использовании instanceof не работает с наследованием
                taskStorage.remove(id);                              //всегда выполняется первая проверка
            } else if (taskStorage.get(id).getClass().equals(Epic.class)) {
                Epic epic = (Epic) taskStorage.get(id);
                for (int index : epic.subtaskIdList) {
                    taskStorage.remove(index);
                }
                taskStorage.remove(id);
            } else if (taskStorage.get(id).getClass().equals(Subtask.class)) {
                Subtask subtask = (Subtask) taskStorage.get(id);
                Epic epic = (Epic) taskStorage.get(subtask.epicId);
                epic.subtaskIdList.remove((Integer) id);
                taskStorage.remove(id);
            } else {
                System.out.println("Ошибка при изменении задачи.");
            }
        }
    }

    public void printEpicsSubtasks(int id) {
        if (taskStorage.containsKey(id) && taskStorage.get(id).getClass().equals(Epic.class)) {
            System.out.println("id = " + id + "; Эпик = " + taskStorage.get(id));
            Epic printEpic = (Epic) taskStorage.get(id);
            for (int subtask : printEpic.subtaskIdList) {
                System.out.println("id = " + subtask + "; Подзадача = " + taskStorage.get(subtask));
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (Integer sdf : taskStorage.keySet()) {
            result.append("id = ").append(sdf).append("; Задача = ").append(taskStorage.get(sdf)).append("\n");
        }
        return result.toString();
    }
}
