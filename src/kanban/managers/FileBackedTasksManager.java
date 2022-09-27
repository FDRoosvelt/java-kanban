package kanban.managers;

import kanban.HistoryManager;
import kanban.Status;
import kanban.Type;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static kanban.Managers.file;
import static kanban.tasks.Task.FORMATTER;

public class FileBackedTasksManager extends InMemoryTaskManager {

    public FileBackedTasksManager() {
        try {
            tasksFromString(file);
            historyFromString(file);
        } catch (IOException e) {
            System.out.println("Ошибка при считывании из файла");
        }
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("start,duration,id,type,name,status,description,epic");
            for (int id : taskStorage.keySet()) {
                bw.write("\n" + taskToString(taskStorage.get(id)));
            }
            for (int id : epicStorage.keySet()) {
                bw.write("\n" + taskToString(epicStorage.get(id)));
            }
            for (int id : subtaskStorage.keySet()) {
                bw.write("\n" + taskToString(subtaskStorage.get(id)));
            }
            bw.write("\n\n");
            bw.write(historyToString(historyManager));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private String historyToString(HistoryManager manager) {
        List<String> historyLine = new LinkedList<>();
        for (Task task : manager.getHistory()) {
            if (historyLine.size() >= 10) {
                historyLine.remove(0);
            }
            historyLine.add(Integer.toString(task.getId()));
        }
        return String.join(",", historyLine);
    }

    private static void historyFromString(File file) throws IOException {
        String lastLine = "";
        int length = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                lastLine = br.readLine();
                length++;
            }
            if (!lastLine.isEmpty() && length > 3) {
                String[] idList = lastLine.split(",");
                for (String idStr : idList) {
                    int idInt = Integer.parseInt(idStr);
                    if (taskStorage.containsKey(idInt)) {
                        historyManager.addHistory(idInt, taskStorage.get(idInt));
                    } else if (epicStorage.containsKey(idInt)) {
                        historyManager.addHistory(idInt, epicStorage.get(idInt));
                    } else if (subtaskStorage.containsKey(idInt)) {
                        historyManager.addHistory(idInt, subtaskStorage.get(idInt));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Не удалось восстановить историю просмотров");
        }
        historyManager.getTasks();
    }

    private static void tasksFromString(File file) throws IOException {
        int lineCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                String line = br.readLine();
                lineCount++;
                if (lineCount > 1 && !line.isEmpty()) {
                    String[] splitLine = line.split(",");
                    if (Integer.parseInt(splitLine[2]) > id) {
                        id = Integer.parseInt(splitLine[2]);
                    }
                    if (splitLine[3].equals(String.valueOf(Type.TASK))) {
                        Task task = new Task(Integer.parseInt(splitLine[2]), Type.TASK, splitLine[4], splitLine[6], Status.valueOf(splitLine[5]), Integer.parseInt(splitLine[1]), splitLine[0]);
                        taskStorage.put(task.getId(), task);
                    } else if (splitLine[3].equals(String.valueOf(Type.EPIC))) {
                        Epic epic = new Epic(Integer.parseInt(splitLine[2]), Type.EPIC, splitLine[4], splitLine[6], Status.valueOf(splitLine[5]), Integer.parseInt(splitLine[1]), splitLine[0]);
                        epicStorage.put(epic.getId(), epic);
                    } else if (splitLine[3].equals(String.valueOf(Type.SUBTASK))) {
                        Subtask subtask = new Subtask(Integer.parseInt(splitLine[2]), Type.SUBTASK, splitLine[4], splitLine[6], Integer.parseInt(splitLine[7]), Status.valueOf(splitLine[5]), Integer.parseInt(splitLine[1]), splitLine[0]);
                        subtaskStorage.put(subtask.getId(), subtask);
                        epicStorage.get(subtask.getEpicId()).addSubtask(subtask.getId());
                    }
                } else if (line.isEmpty()) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Не удалось ыосстановить список задач");
        }
    }

    private String taskToString(Task task) {
        String start;
        if (task.getStartTime()==null) {
            start = "null";
        } else {
            start = task.getStartTime().format(FORMATTER);
        }
        if (task.getType().equals(Type.SUBTASK)) {
            return (start + "," + task.getDuration().toMinutes() + "," + task.getId() + "," + Type.valueOf("SUBTASK") + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription() +","+ ((Subtask) task).getEpicId());
        } else if (task.getType().equals(Type.EPIC)){
            return (start + "," + task.getDuration().toMinutes() + "," + task.getId() + "," + Type.valueOf("EPIC") + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription());
        } else {
            return (start + "," + task.getDuration().toMinutes() + "," + task.getId() + "," + Type.valueOf("TASK") + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription());
        }
    }

    @Override
    public void newTask(String name, String description, int duration, String startTime) {
        super.newTask(name, description, duration, startTime);
        save();
    }

    @Override
    public void newEpic(String name, String description) {
        super.newEpic(name, description);
        save();
    }

    @Override
    public void newSubtask(String name, String description, String epicName, int duration, String startTime) {
        super.newSubtask(name, description, epicName, duration, startTime);
        save();
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
        save();
        return subtask;
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        save();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        save();
    }

    @Override
    public void clearSubtasks() {
        super.clearSubtasks();
        save();
    }

    @Override
    public void updateTask(int id, String name, String description, Status status, int duration, String startTime) {
        super.updateTask(id, name, description, status, duration, startTime);
        save();
    }

    @Override
    public void updateEpic(int id, String name, String description) {
        super.updateEpic(id, name, description);
        save();
    }

    @Override
    public void updateSubtask(int id, String name, String description, String epicName, Status status, int duration, String startTime) {
        super.updateSubtask(id, name, description, epicName, status, duration, startTime);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

}


