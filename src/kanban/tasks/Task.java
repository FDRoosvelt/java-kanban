package kanban.tasks;

import kanban.Status;
import kanban.Type;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private int id;
    private String name;
    private String description;
    private Status status;
    private Type type;
    private Duration duration;
    private LocalDateTime startTime;


    public Task(int id, Type type, String name, String description, Status status, int duration, String startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.duration = Duration.ofMinutes(duration);
        startTimeLogic(startTime);
    }

    public Task(String name, String description, Status status, int duration, String startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = Duration.ofMinutes(duration);
        startTimeLogic(startTime);
    }

    public void startTimeLogic(String startTime) {
        if (startTime.isEmpty() || startTime.equals("null")) {
            this.startTime = null;
        } else {
            this.startTime = LocalDateTime.parse(startTime, FORMATTER);
        }
    }

    public String startTimeToStringLogic(LocalDateTime startTime) {
        if (startTime==null) {
            return "null";
        } else {
            return startTime.format(FORMATTER);
        }
    }

    public LocalDateTime getEndTime() {
        if (startTime!=null && duration.toMinutes()>0) {
            return startTime.plus(duration);
        } else {
            return null;
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public Duration getDuration() {
        return duration;
    }
    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String result = "id = " + id + "; start = " + startTimeToStringLogic(startTime) + "; duration(min) = " + duration.toMinutes() + "; name = " + name +
                "; description = " + description + "; status = " + status;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status && type == task.type && Objects.equals(duration, task.duration) && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, description, status, type, duration, startTime);
        return result*31;
    }
}
