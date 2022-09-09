package kanban.tasks;

import kanban.Status;
import kanban.Type;

public class Subtask extends Task {
    private Integer epicId;

    public Subtask(int id, Type type, String name, String description, int epicId, Status status, int duration, String startTime) {
        super(id, type, name, description, status, duration, startTime);
        this.epicId = epicId;
        this.setType(Type.SUBTASK);
    }
    public Subtask(String name, String description, Status status, int epicId, int duration, String startTime) {
        super(name, description, status, duration, startTime);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }
}
