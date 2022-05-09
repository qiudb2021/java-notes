package util;

public interface Box {
    public boolean add(int element);
    public boolean add(int element, int index);
    public  void addAll();
    public int get(int index);
    public int remove(int index);
    public int getSize();

}
