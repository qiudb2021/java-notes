## 枚举
1. **JDK1.5版本**之后可以直接定义```enum```类型
2. 我们自己定义的```enum```类型直接默认继承```Enum```(java.lang包)，不能再使用```extends```
3. ```java
    private final String name; // 枚举对象的名字
    private final int ordinal; // 枚举对象中类中的顺序(类似Array的index)，从0开始
    
    // 通过给定的name获取对应的枚举对象
    public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {}
    // 获取全部枚举对象
    public final values() E[];
    // 比较两个枚举对象
    public final int compareTo(E o) {}

    // 没有final修饰，可以重写
    public String toString() {
        return name;
    }
   ```
4. ```switch```中```enum```的使用
5. 也可以在```enum```中描述自己的属性或方法