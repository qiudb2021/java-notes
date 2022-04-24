public class Test2 {
    public int changeNum(int x) {
        System.out.println("方法执行开始：" + x); // 1
        x = 10; // 修改传递进来的值
        System.out.println("方法执行最终："+x); // 10
        return x; // 返回值，将x临时变量空间内的值(引用)返回
    }

    public void changeArray(int[] x) {
        System.out.println("方法执行开始：" + x[0]); // 1
        x[0] = 10; // 修改数组x的第一个位置元素
        System.out.println("方法执行最终："+x[0]); // 10
    }

    // 每一个类中不是必须包含主方法的
    // 主方法不属性任何一个类--属于虚拟机
    public static void main(String[] args) {
        // 创建一个对象（前提是有一个类模板）
        // 0. 加载类模板的过程
        Test2 test = new Test2(); // 堆内存中开辟空间
        int a = 1;
        a = test.changeNum(a); // 需要一个Int的条件    int x = a;
        // 调用方法，让方法执行一遍
        // 1. 方法存储在哪里？ 堆内存的对象空间内
        // 2. 方法在哪里执行？栈内存中开辟一块临时的方法执行空间
        System.out.println("方法执行完毕, main方法中a的值: " + a); // 10

        // ====================================================================
        Test2 test2 = new Test2();
        int[] aArr = {1,2,3};
        test2.changeArray(aArr); // int[] x = aArr;
        System.out.println("方法执行完毕，main方法中a数组的第一个值：" + aArr[0]);
    }
}
