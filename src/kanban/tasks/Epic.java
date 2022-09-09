package kanban.tasks;

import kanban.Status;
import kanban.Type;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtaskIdList = new ArrayList<>();

    public Epic(int id, Type type, String name, String description, Status status) {
        super(id, type, name, description, status, 0, "");
    }

    public Epic(int id, Type type, String name, String description, Status status, int duration, String startTime) {
        super(id, type, name, description, status, duration, startTime);
    }

    public void addSubtask(int subtaskId) {
        subtaskIdList.add(subtaskId);
    }

    public List<Integer> getSubtaskIdList() {
        return subtaskIdList;
    }

}
