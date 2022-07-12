package kanban;

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

    Node head;
    Node tail;
    int size = 0;
    HashMap<Integer, Node> nodeMap = new HashMap<>();
    List<Task> history = new ArrayList<>();

    public void linkLast(Integer id, Task element) {
        Node oldTail = tail;
        Node newNode = new Node<>(tail, element, null);
        tail = newNode;
        if (oldTail == null) {
            head = tail = newNode;
        } else {
            oldTail.next = newNode;
        }
        nodeMap.put(id,newNode);
    }

    public void getTasks() {
        Node element = head;
        while (true) {
            if (element!=null) {
                history.add((Task) element.data);
                element = element.next;
            } else {
                break;
            }
        }
    }

    public void removeNode(Node node) {
        if (node.prev != null && node.next != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        } else if (node.prev == null && node.next != null) {
            head = node.next;
            node.next.prev = null;
            node.next = null;
        } else if (node.prev == null && node.next == null) {
            head = tail = null;
        } else if (node.prev != null && node.next == null) {
            tail = node.prev;
            node.prev.next = null;
            node.prev = null;
        }
    }

    @Override
    public void addHistory(int id, Task task) {
        remove(id);
        linkLast(id, task);
    }

    @Override
    public void remove(int id) {
        if (nodeMap.containsKey(id)) {
            removeNode(nodeMap.get(id));
            nodeMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        history.clear();
        getTasks();
        return history;
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
