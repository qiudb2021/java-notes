package rely;

public class Pig {
    private String name; // 名字
    private int weight = 20; // 体重

    public  Pig() {}
    public Pig(String name) {
        this.name = name;
    }

    public void beKilled() {
        System.out.println(this.name + "被杀了");
    }

    public void growUp(int month) {
        this.weight = this.weight * (int)Math.pow(2, month) ;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
}
