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

public class FileBackedTasksManager extends InMemoryTaskManager {
    private static final String HOME = System.getProperty("user.dir");
    static File file = new File(HOME+"/src/kanban/data/data.csv");

    @Override
    public void loadFromFile() {
        try {
            tasksFromString();
            historyFromString();
        } catch (IOException e) {
            e.getMessage();
        }

    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("id,type,name,status,description,epic");
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

    String historyToString(HistoryManager manager) {
        List<String> historyLine = new LinkedList<>();
        for (Task task : manager.getHistory()) {
            if (historyLine.size() > 9) {
                historyLine.remove(0);
            }
            historyLine.add(Integer.toString(task.id));
        }
        return String.join(",", historyLine);
    }

    void historyFromString() throws IOException {
        String lastLine = null;
        int length = 0;
        BufferedReader br = new BufferedReader(new FileReader(file));
        while (br.ready()) {
            lastLine = br.readLine();
            length++;
        }
        if (length > 3) {
            String[] idList = lastLine.split(",");
            for (String idStr : idList) {
                int idInt = Integer.parseInt(idStr);
                if (taskStorage.containsKey(idInt)) {
                    historyManager.addHistory(idInt, taskStorage.get(idInt) );
                } else if (epicStorage.containsKey(idInt)) {
                    historyManager.addHistory(idInt, epicStorage.get(idInt) );
                } else if (subtaskStorage.containsKey(idInt)) {
                    historyManager.addHistory(idInt, subtaskStorage.get(idInt) );
                }
            }
        }
        br.close();
        historyManager.getTasks();
    }

    public void tasksFromString() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int lineCount = 0;
        while (br.ready()) {
            String line = br.readLine();
            lineCount++;
            if (lineCount > 1 && !line.isEmpty()) {
                String[] splitLine = line.split(",");
                if (Integer.parseInt(splitLine[0]) > id) {
                    id = Integer.parseInt(splitLine[0]);
                }
                if (splitLine[1].equals(String.valueOf(Type.TASK))) {
                    Task task = new Task(Integer.parseInt(splitLine[0]), splitLine[2], splitLine[4], Status.valueOf(splitLine[3]));
                    taskStorage.put(task.id, task);
                } else if (splitLine[1].equals(String.valueOf(Type.EPIC))) {
                    Epic epic = new Epic(Integer.parseInt(splitLine[0]), splitLine[2], splitLine[4], Status.valueOf(splitLine[3]));
                    epicStorage.put(epic.id, epic);
                } else if (splitLine[1].equals(String.valueOf(Type.SUBTASK))) {
                    Subtask subtask = new Subtask(Integer.parseInt(splitLine[0]), splitLine[2], splitLine[4], Integer.parseInt(splitLine[5]), Status.valueOf(splitLine[3]));
                    subtaskStorage.put(subtask.id, subtask);
                    epicStorage.get(subtask.epicId).addSubtask(subtask.id);
                }
            }
        }
        br.close();
    }

    public String taskToString(Task task) {
        String taskString = null;
        if (task.getClass().equals(Subtask.class)) {
            taskString = (task.id + "," + Type.valueOf("SUBTASK") + "," + task.name + "," + task.status + "," + task.description +","+ ((Subtask) task).epicId);
        } else if (task.getClass().equals(Epic.class)){
            taskString = (task.id + "," + Type.valueOf("EPIC") + "," + task.name + "," + task.status + "," + task.description);
        } else {
            taskString = (task.id + "," + Type.valueOf("TASK") + "," + task.name + "," + task.status + "," + task.description);
        }
        return taskString;
    }

    @Override
    public void newTask(String name, String description) {
        super.newTask(name, description);
        save();
    }

    @Override
    public void newEpic(String name, String description) {
        super.newEpic(name, description);
        save();
    }

    @Override
    public void newSubtask(String name, String description, int epicId) {
        super.newSubtask(name, description, epicId);
        save();
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Task getEpic(int id) {
        Task task = super.getEpic(id);
        save();
        return task;
    }

    @Override
    public Task getSubtask(int id) {
        Task task = super.getSubtask(id);
        save();
        return task;
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
    public void updateTask(int id, Task task) {
        super.updateTask(id, task);
        save();
    }

    @Override
    public void updateEpic(int id, Epic epic) {
        super.updateEpic(id, epic);
        save();
    }

    @Override
    public void updateSubtask(int id, Subtask subtask) {
        super.updateSubtask(id, subtask);
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


