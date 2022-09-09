package kanban.tasks;

import kanban.Status;
import kanban.Type;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    Epic epic = new Epic(100, Type.TASK, "name", "description", Status.NEW, 10, "01.01.1010 01:01");
    List<Integer> list = new ArrayList<>();

    @Test
    void addSubtask() {
        list.add(100);
        epic.addSubtask(100);
        assertEquals(list, epic.getSubtaskIdList());
    }

    @Test
    void getSubtaskIdList() {
        assertEquals(list, epic.getSubtaskIdList());
    }
}