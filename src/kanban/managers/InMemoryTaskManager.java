package kanban.managers;

import kanban.HistoryManager;
import kanban.Status;
import kanban.TaskManager;
import kanban.Type;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.IOException;
import java.util.*;

import static kanban.tasks.Task.FORMATTER;


public class InMemoryTaskManager implements TaskManager {

    static int id = 100000;
    static Map<Integer, Task> taskStorage = new HashMap<>();
    static Map<Integer, Epic> epicStorage = new HashMap<>();
    static Map<Integer, Subtask> subtaskStorage = new HashMap<>();
    HistoryManager historyManager = new InMemoryHistoryManager();

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void newTask(String name, String description, int duration, String startTime) {
        Task task = new Task(id, Type.TASK, name, description, Status.NEW, duration, startTime);
        if (crosstimeLogic(task)) {
            taskStorage.put(id, task);
            id++;
        } else {
            System.out.println("Время пересекается с другой задачей!");
        }
    }

    @Override
    public void newEpic(String name, String description) {
        Epic epic = new Epic(id, Type.EPIC, name, description, Status.NEW);
        epicStorage.put(id, epic);
        id++;
    }

    @Override
    public void newSubtask(String name, String description, String epicName, int duration, String startTime) {
        Subtask subtask = new Subtask(id, Type.SUBTASK, name, description, getEpicId(epicName), Status.NEW, duration, startTime);
        if (crosstimeLogic(subtask)) {
            if (epicStorage.containsKey(subtask.getEpicId())) {
                epicTimeLogic(subtask);
                subtaskStorage.put(id, subtask);
                id++;
            } else {
                System.out.println("Ошибка при создании подзадачи.");
            }
        } else {
            System.out.println("Время пересекается с другой задачей!");
        }

    }

    private void epicTimeLogic(Subtask subtask) {
        Epic epic = epicStorage.get(subtask.getEpicId());
        epic.addSubtask(id);
        if (epic.getDuration() != null) {
            epic.setDuration(epic.getDuration().plus(subtask.getDuration()));
        } else {
            epic.setDuration(subtask.getDuration());
        }
        if (subtask.getStartTime() == null) {
            return;
        }else if (epic.getStartTime() == null || epic.getStartTime().isAfter(subtask.getStartTime())) {
            epic.setStartTime(subtask.getStartTime());
        }
    }

    @Override
    public Map<Integer, Task> getTasks() {
            return taskStorage;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epicStorage;
    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {
        return subtaskStorage;
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
    public Task getTask(int id) {
        if (taskStorage.containsKey(id)) {
            historyManager.addHistory(id, taskStorage.get(id));
            return taskStorage.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Epic getEpic(int id) {
        if (epicStorage.containsKey(id)) {
            historyManager.addHistory(id, epicStorage.get(id));
            return epicStorage.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Subtask getSubtask(int id) {
        if (subtaskStorage.containsKey(id)) {
            historyManager.addHistory(id, subtaskStorage.get(id));
            return subtaskStorage.get(id);
        } else {
            return null;
        }
    }

    private int getEpicId(String epicName) {
        Integer epicId = 0;
        for (Integer key : epicStorage.keySet()) {
            if (epicStorage.get(key).getName().equals(epicName)) {
                epicId = key;
                break;
            }
        }
        return epicId;
    }

    @Override
    public void updateTask(int id, String name, String description, Status status, int duration, String startTime) {
        if (taskStorage.containsKey(id)) {
            Task task = new Task(id, Type.TASK, name, description, status, duration, startTime);
            taskStorage.put(id, task);
        } else {
            System.out.println("Такой задачи не существует");
        }
    }

    @Override
    public void updateEpic(int id, String name, String description) {
        if (epicStorage.containsKey(id)) {
            Epic epic = epicStorage.get(id);
            epic.setName(name);
            epic.setDescription(description);
            epicStorage.put(id, epic);
        } else {
            System.out.println("Нет такого эпика");
        }
    }

    @Override
    public void updateSubtask(int id, String name, String description, String epicName, Status status, int duration, String startTime) {
        if (subtaskStorage.containsKey(id)) {
            Subtask subtask = new Subtask(id, Type.SUBTASK, name, description, getEpicId(epicName), status, duration, startTime);
            subtaskStorage.put(id, subtask);
            logicStatus(subtask.getEpicId());
        } else {
            System.out.println("Нет такой подзадачи");
        }
    }

    private void logicStatus(int epicId) {
        int doneStatus = 0;
        int newStatus = 0;
        Epic epic = epicStorage.get(epicId);
        for (int id : epic.getSubtaskIdList()) {
            Subtask subtask = subtaskStorage.get(id);
            if (subtask.getStatus().equals(Status.NEW)) {
                newStatus++;
            } else if (subtask.getStatus().equals(Status.DONE)) {
                doneStatus++;
            }
        }
        if (newStatus == epic.getSubtaskIdList().size()) {
            epic.setStatus(Status.NEW);
        } else if (doneStatus == epic.getSubtaskIdList().size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
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
            for (int index : epic.getSubtaskIdList()) {
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
            Epic epic = epicStorage.get(subtask.getEpicId());
            epic.getSubtaskIdList().remove((Integer) id);
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
            for (int subtask : epicStorage.get(id).getSubtaskIdList()) {
                System.out.println("Подзадача = " + subtaskStorage.get(subtask));
            }
        }
    }

    @Override
    public void loadFromFile() throws IOException {
    }

    @Override
    public String getEndTime(int id) {
        if (taskStorage.containsKey(id)) {
            return "Время завершения задачи - " + taskStorage.get(id).getEndTime().format(FORMATTER);
        } else if (epicStorage.containsKey(id)) {
            return "Время завершения задачи - " + epicStorage.get(id).getEndTime().format(FORMATTER);
        } else if (subtaskStorage.containsKey(id)) {
            return "Время завершения задачи - " + subtaskStorage.get(id).getEndTime().format(FORMATTER);
        } else {
            return "Такой задачи не существует";
        }
    }

    public Set<Task> getPrioritizedTasks() {
        Set<Task> tasks = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Task::getId));
        tasks.addAll(taskStorage.values());
        tasks.addAll(subtaskStorage.values());
        return tasks;
    }

    private boolean crosstimeLogic(Task newTask) {
        for (Task task : getPrioritizedTasks()) {
            if (task.getStartTime()==null || newTask.getStartTime()==null) {
                return true;
            }
            if (newTask.getStartTime().isBefore(task.getStartTime()) &&
                    newTask.getEndTime().isAfter(task.getStartTime()) ||
                    newTask.getStartTime().isAfter(task.getStartTime()) &&
                    newTask.getEndTime().isBefore(task.getEndTime()) ||
                    newTask.getStartTime().isBefore(task.getEndTime()) &&
                    newTask.getEndTime().isAfter(task.getEndTime()) ||
                    newTask.getStartTime().equals(task.getStartTime())) {
                return false;
            }
        }
        return true;
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
                if (idEpic.equals(subtaskStorage.get(idSubtask).getEpicId())) {
                    result.append(subtaskStorage.get(idSubtask)).append("\n");
                }
            }
        }

        return result.toString();
    }
}
