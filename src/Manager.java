import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    int id = 100000;
    HashMap<Integer, Object> taskStorage = new HashMap<>();

    void newTask(String name, String description) {
        Task task = new Task(name, description);
        taskStorage.put(id, task);
        id++;
    }

    void newEpic(String name, String description) {
        Epic task = new Epic(name, description);
        taskStorage.put(id, task);
        id++;
    }

    void newSubtask(String name, String description, int epicId) {
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

    void printTasks(String type) {
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

    void clearTasks(String type) {
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

    void findTask(int id) {
        System.out.println("id = " + id + "; Задача = " + taskStorage.get(id));
    }

    void createTask(String type, ExampleObject object) {
        switch (type) {
            case "Задача":
                newTask(object.name, object.description);
                break;
            case "Эпик":
                newEpic(object.name, object.description);
                break;
            case "Подзадача":
                newSubtask(object.name, object.description, object.epicId);
                break;
            default:
                System.out.println("Ошибка при создании задачи.");
        }

    }

    void updateTask(ExampleObject object) {
        if (taskStorage.containsKey(object.id)) {
            if (taskStorage.get(object.id).getClass().equals(Task.class)) {
                Task task = new Task(object.name, object.description, object.status);
                taskStorage.put(object.id, task);
            } else if (taskStorage.get(object.id).getClass().equals(Epic.class)) {
                Epic epic = new Epic(object.name, object.description, object.status);
                taskStorage.put(object.id, epic);
            } else if (taskStorage.get(object.id).getClass().equals(Subtask.class)) {
                Subtask subtask = new Subtask(object.name, object.description, object.status, object.epicId);
                taskStorage.put(object.id, subtask);
                logicStatus(object.epicId);
            } else {
                    System.out.println("Ошибка при изменении задачи.");
            }
        }
    }

    void logicStatus(int epicId) {
        int doneStatus = 0;
        int newStatus = 0;
        Epic epic = (Epic) taskStorage.get(epicId);
        for (int idST : epic.subtaskIdList) {
            Subtask subtask = (Subtask) taskStorage.get(idST);
            switch (subtask.status) {
                case "NEW":
                    newStatus++;
                    break;
                case "DONE":
                    doneStatus++;
                    break;
                default:
                    break;
            }
        }
        if (newStatus == epic.subtaskIdList.size()) {
            epic.status = "NEW";
        } else if (doneStatus == epic.subtaskIdList.size()) {
            epic.status = "DONE";
        } else {
            epic.status = "IN_PROGRESS";
        }
    }

    void deleteTask(int id) {
        if (taskStorage.containsKey(id)) {
            if (taskStorage.get(id).getClass().equals(Task.class)) {
                taskStorage.remove(id);
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

    void printEpicsSubtasks(int id) {
        if (taskStorage.containsKey(id)&&taskStorage.get(id).getClass().equals(Epic.class)) {
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
