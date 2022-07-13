package kanban;

import java.util.ArrayList;

public class Epic extends Task {
    ArrayList <Integer> subtaskIdList = new ArrayList<>();

    protected Epic(int id, String name, String description) {
        super(id, name, description);
    }
    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    void addSubtask(int subtaskId) {
        subtaskIdList.add(subtaskId);
    }


}
