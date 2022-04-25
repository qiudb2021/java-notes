public class ArrayBox {
    private static final int DEFAULT_CAPACITY = 10;
    private int[] elementData;
    private int size = 0; // 记录当前元素个数

    public ArrayBox() {
        this.elementData = new int[DEFAULT_CAPACITY];
    }
    public ArrayBox(int capacity) {
        this.elementData = new int[capacity];

    }
    public int[] getElementData() {
        return elementData;
    }

    public int getSize() {
        return size;
    }

    public boolean add(int element) {
        this.ensureCapacityInternal(this.size + 1);
        this.elementData[this.size++] = element;
        return true;
    }

    public int get(int index) {
        this.rangeCheck(index);
        return this.elementData[index];
    }

    public int remove(int index) {
        this.rangeCheck(index);
        int oldValue = this.elementData[index];
        for (int i = 0; i < this.size - 1; i++) {
            this.elementData[i] = this.elementData[i + 1];
        }
        this.elementData[--this.size] = 0;
        return oldValue;
    }

    protected void ensureCapacityInternal(int minCapacity) {
        if(minCapacity - this.elementData.length > 0) {
            this.grow(minCapacity);
        }
    }

    protected void grow(int minCapacity) {
        int oldCapacity = this.elementData.length;
        // 1.5倍扩容
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        System.out.println("size " + this.size + " oldCapacity " + oldCapacity + "new Capacity " + newCapacity);
        // 1.5倍扩容后，所需空间还不够，直接利用minCapacity.
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }

        this.elementData = this.copyOf(this.elementData, newCapacity);
    }

    protected int[] copyOf(int[] oldArray, int newCapacity) {
        int[] newArray = new int[newCapacity];
        for(int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }
        return newArray;
    }

    protected void rangeCheck(int index) {
        if (index < 0 || index >= this.size) {
            throw new ArrayBoxIndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
    }
}
