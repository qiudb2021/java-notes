package contains;

public class Car {
    public String brand; // 品牌
    public String type; // 型号
    public String color; // 颜色

    // 包含关系
    public Wheel wheel; // 轮子

    public Car(String brand, String type,  String color, Wheel wheel) {
        this.brand = brand;
        this.type = type;
        this.color = color;
        this.wheel = wheel;
    }
    public void showCar() {
        System.out.println("这是一辆" + brand + "牌"+type+"型号"+color+"的小汽车");
        System.out.println("车上搭载着"+wheel.brand+ "牌"+wheel.size+"尺寸"+wheel.color+"颜色的车轮");
    }
}
