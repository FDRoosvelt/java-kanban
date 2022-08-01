package kanban.managers;

import kanban.HistoryManager;
import kanban.Status;
import kanban.Type;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private static final String HOME = System.getProperty("user.home") + "\\dev\\java-kanban\\src\\kanban";
    @Override
    public void loadFromFile() throws IOException {
        try {
            tasksFromString();
            historyFromString();
        } catch (NullPointerException e) {
        }
    }

    public void save() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(HOME + "\\data\\data.csv"));
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
        bw.close();
    }

    static String historyToString(HistoryManager manager) {
        String historyLine = "";
        for (Task task : manager.getHistory()) {
            historyLine += Integer.toString(task.id) + ",";
        }
        return historyLine;
    }

    static void historyFromString() throws IOException {
        String lastLine = null;
        int length = 0;
        List<Task> historyList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(HOME + "\\data\\data.csv"));
        while (br.ready()) {
            lastLine = br.readLine();
        }
        String[] idList = lastLine.split(",");
        if (length > 3) {
            for (int i = 0; i < idList.length; i++) {
                if (taskStorage.containsKey(Integer.parseInt(idList[i]))) {
                    historyList.add(taskStorage.get(Integer.parseInt(idList[i])));
                } else if (epicStorage.containsKey(Integer.parseInt(idList[i]))) {
                    historyList.add(epicStorage.get(Integer.parseInt(idList[i])));
                } else if (subtaskStorage.containsKey(Integer.parseInt(idList[i]))) {
                    historyList.add(subtaskStorage.get(Integer.parseInt(idList[i])));
                }
            }
        }
        br.close();
        historyManager.loadHistory(historyList);
    }

    public void tasksFromString() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(HOME + "\\data\\data.csv"));
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
            taskString = (Integer.toString(task.id) + "," + Type.valueOf("SUBTASK") + "," + task.name + "," + String.valueOf(task.status) + "," + task.description +","+ ((Subtask) task).epicId);
        } else if (task.getClass().equals(Epic.class)){
            taskString = (Integer.toString(task.id) + "," + Type.valueOf("EPIC") + "," + task.name + "," + String.valueOf(task.status) + "," + task.description);
        } else {
            taskString = (Integer.toString(task.id) + "," + Type.valueOf("TASK") + "," + task.name + "," + String.valueOf(task.status) + "," + task.description);
        }
        return taskString;
    }

    @Override
    public void newTask(String name, String description) throws IOException {
        super.newTask(name, description);
        save();
    }

    @Override
    public void newEpic(String name, String description) throws IOException {
        super.newEpic(name, description);
        save();
    }

    @Override
    public void newSubtask(String name, String description, int epicId) throws IOException {
        super.newSubtask(name, description, epicId);
        save();
    }

    @Override
    public Task getTask(int id) throws IOException {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Task getEpic(int id) throws IOException {
        Task task = super.getEpic(id);
        save();
        return task;
    }

    @Override
    public Task getSubtask(int id) throws IOException {
        Task task = super.getSubtask(id);
        save();
        return task;
    }

    @Override
    public void clearTasks() throws IOException {
        super.clearTasks();
        save();
    }

    @Override
    public void clearEpics() throws IOException {
        super.clearEpics();
        save();
    }

    @Override
    public void clearSubtasks() throws IOException {
        super.clearSubtasks();
        save();
    }

    @Override
    public void updateTask(int id, Task task) throws IOException {
        super.updateTask(id, task);
        save();
    }

    @Override
    public void updateEpic(int id, Epic epic) throws IOException {
        super.updateEpic(id, epic);
        save();
    }

    @Override
    public void updateSubtask(int id, Subtask subtask) throws IOException {
        super.updateSubtask(id, subtask);
        save();
    }

    @Override
    public void deleteTask(int id) throws IOException {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) throws IOException {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) throws IOException {
        super.deleteSubtask(id);
        save();
    }

}


