package kanban;

public class Task {
    int id;
    String name;
    String description;
    Status status;

    protected Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
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
