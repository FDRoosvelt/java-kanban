package kanban.tasks;

import kanban.Status;
import kanban.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    Subtask subtask = new Subtask(100, Type.TASK, "name", "description", 111, Status.NEW, 10, "01.01.1010 01:01");

    @Test
    void getEpicId() {
        assertEquals(111, subtask.getEpicId());
    }
}