package computer;

public class MachingRoom {
    public MachingRoom() {
        this.init(5);
    }
    // 机房-电脑 聚合关系：机房内有电脑
    private final Computer[] computers = new Computer[5];

    public void init(int count) {
        for (int i = 0; i < count; i++) {
            computers[i] = new Computer(i+1);
        }
    }

    // 机房-学生 依赖关系
    public void welcomeStudent(Student student) {
        System.out.println("机房欢迎学生" + student.getName());
        // 找一个可以用的电脑
        Computer computer = this.find();
        if (computer != null) {
            student.useComputer(computer);
        } else {
            System.out.println("没有找到可用的电脑");
        }
    }

    public Computer find() {
        for (Computer computer : this.computers) {
            if (!computer.isUsed()) {
                return computer;
            }
        }
        return null;
    }
}
