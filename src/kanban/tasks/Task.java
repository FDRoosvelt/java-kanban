package kanban.tasks;

import kanban.Status;

public class Task {
    public int id;
    public String name;
    public String description;
    public Status status;

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    @Override
    public String toString() {
        String result = "id = " + id + "; name = " + name +
                "; description = " + description + "; status = " + status;
        return result;
    }
}
