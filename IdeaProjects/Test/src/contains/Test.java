package contains;

public class Test {
    public static void main(String[] args) {
        Wheel wheel = new Wheel("米其林", 200, "黑");
        Car car = new Car("宝马", "Z4", "宝石蓝色", wheel);
        car.showCar();
    }
}
