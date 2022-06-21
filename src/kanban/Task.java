package kanban;

public class Task {
    String name;
    String description;
    Status status;

    public Task(String name, String description) {
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
        String result = "{ name = " + name +
                "; description = " + description + "; status = " + status + " }";
        return result;
    }
}
