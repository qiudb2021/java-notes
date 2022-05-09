package util;

public class LinkedBox implements Box {
    // 头节点
    private Node first;
    // 尾节点
    private Node last;
    // 元素个数
    private int size;

    public LinkedBox() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public boolean add(int element) {
        this.linkLast(element);
        return true;
    }

    @Override
    public int get(int index) {
        this.rangetCheck(index);
        Node target = this.getNode(index);
        return target.item;
    }

    @Override
    public int remove(int index) {
        this.rangetCheck(index);
        Node target = this.getNode(index);
        int oldValue = this.unlink(target);
        return oldValue;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    private void linkLast(int element) {
        Node l = this.last;
        Node newNode = new Node(this.last, null, element);
        this.last = newNode;

        if (l == null) {
            this.first = newNode;
        } else {
            l.next = newNode;
        }

        this.size++;
    }

    private void rangetCheck(int index) {
        if (index > this.size) {
            throw new BoxIndexOutOfBoundsException("");
        }
    }

    private Node getNode(int index) {
        Node target;
        int half = this.size >> 1;
        if (index < half) {
            target = first;
            for (int i = 0; i < index; i++) {
                target = target.next;
            }
        } else {
            target = last;
            for (int i = this.size - 1; i > index ; i--) {
                target = target.prev;
            }
        }
        return target;
    }

    private int unlink(Node node) {
        int oldValue = node.item;
        Node prev = node.prev;
        Node next = node.next;

        // 删除的是第1个元素
        if (prev == null) {
            first = next;
            first.prev = null;
        } else {
            prev.next = next;
            node.prev = null;
        }

        // 删除的是最后1个元素
        if (node.next == null) {
            last = prev;
            last.next = null;
        } else {
            next.prev = prev;
            node.next = null;
        }

        this.size--;
        return oldValue;
    }
}
