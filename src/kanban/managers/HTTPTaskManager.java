package kanban.managers;


import kanban.Status;
import kanban.http.KVTaskClient;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static kanban.Managers.file;

public class HTTPTaskManager extends FileBackedTasksManager {
    KVTaskClient kvTaskClient;
    String key;
    public HTTPTaskManager(String url, String key) {
        super();
        kvTaskClient = new KVTaskClient(url);
        this.key = key;
    }

    private void saveOnServer () {
        try {
            kvTaskClient.put(key, String.valueOf(Files.readAllLines(Path.of(file.getPath()))));
        } catch (IOException e) {
            System.out.println("Ошибка сохранения на сервер");
        }
    }

    @Override
    public void newTask(String name, String description, int duration, String startTime) {
        super.newTask(name, description, duration, startTime);
        saveOnServer();
    }

    @Override
    public void newEpic(String name, String description) {
        super.newEpic(name, description);
        saveOnServer();
    }

    @Override
    public void newSubtask(String name, String description, String epicName, int duration, String startTime) {
        super.newSubtask(name, description, epicName, duration, startTime);
        saveOnServer();
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        saveOnServer();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        saveOnServer();
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
        saveOnServer();
        return subtask;
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        saveOnServer();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        saveOnServer();
    }

    @Override
    public void clearSubtasks() {
        super.clearSubtasks();
        saveOnServer();
    }

    @Override
    public void updateTask(int id, String name, String description, Status status, int duration, String startTime) {
        super.updateTask(id, name, description, status, duration, startTime);
        saveOnServer();
    }

    @Override
    public void updateEpic(int id, String name, String description) {
        super.updateEpic(id, name, description);
        saveOnServer();
    }

    @Override
    public void updateSubtask(int id, String name, String description, String epicName, Status status, int duration, String startTime) {
        super.updateSubtask(id, name, description, epicName, status, duration, startTime);
        saveOnServer();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        saveOnServer();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        saveOnServer();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        saveOnServer();
    }
}
