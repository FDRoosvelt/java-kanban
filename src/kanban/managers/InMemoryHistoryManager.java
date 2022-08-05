package kanban.managers;

import kanban.HistoryManager;
import kanban.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    class Node<Task>{
        public Task data;
        public Node<Task> prev;
        public Node<Task> next;


        public Node(Node<Task> prev, Task data, Node<Task> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    static Node head;
    static Node tail;
    static HashMap<Integer, Node> nodeMap = new HashMap<>();
    static List<Task> history = new ArrayList<>();

    @Override
    public void linkLast(int id, Task task) {
        Node oldTail = tail;
        Node newNode = new Node<>(tail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        nodeMap.put(id,tail);
    }

    public void getTasks() {
        Node element = head;
        while (element!=null) {
            history.add((Task) element.data);
            element = element.next;
        }
    }

    public void removeNode(Node node) {
        if (node.prev != null && node.next != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        } else if (node.prev == null && node.next != null) {
            head = node.next;
            node.next.prev = null;
        } else if (node.prev == null && node.next == null) {
           head = tail = null;
        } else if (node.prev != null && node.next == null) {
            tail = node.prev;
            node.prev.next = null;
        }
    }

    @Override
    public void addHistory(int id, Task task) {
        if (nodeMap.size() > 10) {
            remove((Task) head.data);
            nodeMap.remove(((Task) head.data).id);
            removeNode(head);
        }
        remove(task);
        linkLast(id, task);
    }

    @Override
    public void remove(Task task) {
        if (nodeMap.containsKey(task.id)) {
            removeNode(nodeMap.get(task.id));
            nodeMap.remove(task.id);
        }
    }

    @Override
    public List<Task> getHistory() {
        history.clear();
        getTasks();
        return history;
    }

    public void loadHistory(List<Task> list) {
        this.history = list;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Task task : history) {
            result.append(task).append("\n");
        }
        return result.toString();
    }
}
