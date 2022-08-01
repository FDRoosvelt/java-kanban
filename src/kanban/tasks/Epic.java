package kanban.tasks;

import kanban.Status;

import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList <Integer> subtaskIdList = new ArrayList<>();

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }
    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public void addSubtask(int subtaskId) {
        subtaskIdList.add(subtaskId);
    }


}
