package util;

public abstract class AbstractBox implements Box {
    public abstract boolean add(int element);
    public boolean add(int element, int index) {
        // 抛出异常，子类若需要，必须自己重现
        throw new RuntimeException("若需要，必须自己重写");
    }
    public void addAll() {
        // 抛出异常, 子类若需要，必须自己重现
        throw new RuntimeException("若需要，必须自己重写");
    }
    public abstract int get(int index);
    public abstract int remove(int index);
    public abstract int getSize();
}
