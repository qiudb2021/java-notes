package util;

public class Node {
    public Node prev;
    public Node next;
    public int item;

    public Node(Node prev, Node next, int item) {
        this.prev = prev;
        this.next = next;
        this.item = item;
    }
}
