package contains;

public class Wheel {
    public String brand; // 品牌
    public int size; // 尺寸
    public String color; // 颜色

    public  Wheel(String brand, int size, String color) {
        this.brand = brand;
        this.size = size;
        this.color = color;
    }
    public void turn() {
        System.out.println("车轮子会转");
    }
}
