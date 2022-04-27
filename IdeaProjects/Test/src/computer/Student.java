package computer;

public class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // 依赖关系: 学生使用电脑
    public void useComputer(Computer computer) {
        System.out.println(this.name + "使用电脑");
        computer.beOpen();
        computer.beUsing();
        computer.beClose();
    }
}
