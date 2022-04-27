package computer;

public class Computer {
    private boolean used = false; //true-开 false-关
    private int number;

    public Computer() {}

    public Computer(int number) {
        this.number = number;
    }

    public boolean isUsed() {
        return used;
    }

    public int getNumber() {
        return number;
    }

    public void beOpen() {
        this.used = true;
        System.out.println(this.number + "号电脑被打开了");
    }

    public void  beClose() {
        this.used = false;
        System.out.println(this.number + "号电脑被关闭了");
    }

    public void beUsing() {
        System.out.println(this.number + "正在被使用中");
    }
}
