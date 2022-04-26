package rely;

public class Test {
    public static void main(String[] args) {
        Farmer farmer = new Farmer();
        Pig pig = farmer.feedPig(5);
        Butcher butcher = new Butcher();
        butcher.killPig(pig);
    }
}
