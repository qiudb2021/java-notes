public class Person {
    public String name;
    public int age;
    public String sex;

    public void eat(String food) {
        System.out.println(this.name + " eating " + food);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }
}
