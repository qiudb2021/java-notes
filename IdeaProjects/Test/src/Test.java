public class Test {
    public static void main(String[] args) {
        System.out.println("This is a Test Project.");

        Person p = new Person();
        p.name = "Test_001";
        p.eat("米饭");
        System.out.println("My name is " + p.getName() + ", my age is " + p.getAge() + ", my sex is " + p.getSex());
    }
}
