import java.util.ArrayList;

public class Epic extends Task {
    ArrayList <Integer> subtaskIdList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }
    public Epic(String name, String description, String status) {
        super(name, description, status);
    }

    void addSubtask(int subtaskId) {
        subtaskIdList.add(subtaskId);
    }


}
