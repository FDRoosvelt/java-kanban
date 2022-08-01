package kanban.tasks;

import kanban.Status;

public class Subtask extends Task {
    public Integer epicId;

    public Subtask(int id, String name, String description, int epicId, Status status) {
        super(id, name, description, status);
        this.epicId = epicId;
    }
    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

}
