public class testString {
    public static void main(String[] args) {

        String s1 = "abc"; // "abc"是一个String对象，对象的属性是"abc"，存放在常量区
        String s2 = "abc";

        String s3 = new String("abc"); // 创建一个新的String对象，对象的属性是"abc"，存放在堆中
        String s4 = new String("abc");

        System.out.println(s1 == s2); // true
        System.out.println(s1 == s3); // false
        System.out.println(s3 == s4); // false
        System.out.println(s1.equals(s2)); // true
        System.out.println(s1.equals(s3)); // true
        System.out.println(s3.equals(s4)); // true
    }
}
