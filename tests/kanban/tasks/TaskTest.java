package kanban.tasks;

import kanban.Status;
import kanban.Type;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    Task task1 = new Task(100, Type.TASK, "name", "description", Status.NEW, 10, "01.01.1010 01:01");
    Task task2 = new Task(101, Type.TASK, "name", "description", Status.NEW, 10, "");
    Task task3 = new Task(102, Type.TASK, "name", "description", Status.NEW, 0, "null");

    @Test
    void startTimeLogic() {
        assertEquals(LocalDateTime.of(1010, 1, 1, 1, 1), task1.getStartTime());
        assertNull(task2.getStartTime());
        assertNull(task3.getStartTime());
    }

    @Test
    void startTimeToStringLogic() {
        assertEquals("null", task2.startTimeToStringLogic(task2.getStartTime()));
        assertEquals("01.01.1010 01:01", task1.startTimeToStringLogic(task1.getStartTime()));
    }

    @Test
    void getEndTime() {
        assertNull(task3.getEndTime());
        assertNull(task3.getEndTime());
        assertEquals(LocalDateTime.of(1010, 1, 1, 1, 11), task1.getEndTime());
    }

    @Test
    void getStartTime() {
        assertEquals(LocalDateTime.of(1010, 1, 1, 1, 1), task1.getStartTime());
    }

    @Test
    void setStartTime() {
        task2.setStartTime(LocalDateTime.of(1010, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1010, 1, 1, 1, 1), task2.getStartTime());
    }

    @Test
    void getDuration() {
        assertEquals(Duration.ofMinutes(10), task1.getDuration());
    }

    @Test
    void setDuration() {
        task3.setDuration(Duration.ofMinutes(10));
        assertEquals(Duration.ofMinutes(10), task3.getDuration());
    }

    @Test
    void getType() {
        assertEquals(Type.TASK, task1.getType());
    }

    @Test
    void setType() {
        task2.setType(Type.EPIC);
        assertEquals(Type.EPIC, task2.getType());
    }

    @Test
    void getStatus() {
        assertEquals(Status.NEW, task1.getStatus());
    }

    @Test
    void setStatus() {
        task2.setStatus(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, task2.getStatus());
    }

    @Test
    void getDescription() {
        assertEquals("description", task1.getDescription());
    }

    @Test
    void getName() {
        assertEquals("name", task1.getName());
    }

    @Test
    void getId() {
        assertEquals(100, task1.getId());
    }

    @Test
    void setId() {
        task2.setId(200);
        assertEquals(200, task2.getId());
    }

    @Test
    void testToString() {
        assertEquals("id = 100; start = 01.01.1010 01:01; duration(min) = 10; name = name; description = description; status = NEW", task1.toString());
    }
}