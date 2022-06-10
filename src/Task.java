public class Task {
    String name;
    String description;
    String status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = "NEW";
    }

    public Task(String name, String description, String status) {
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
