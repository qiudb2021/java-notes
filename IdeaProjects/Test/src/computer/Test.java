package computer;

public class Test {
    public static void main(String args[]) {
        Student student = new Student("Test_001");
        MachingRoom machingRoom = new MachingRoom();
        machingRoom.welcomeStudent(student);
    }
}
