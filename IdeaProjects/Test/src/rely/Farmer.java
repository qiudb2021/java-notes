package rely;

// 农夫养猪
public class Farmer {
    public Pig feedPig(int month) {
        Pig pig = new Pig("1234");
        pig.growUp(month);
        System.out.println("猪的体重" + pig.getWeight());
        return pig;
    }
}
