# Scanner类
## import
```java
import java.util.Scanner;
```
## 方法
```java
Scanner input = new Scanner(System.in);
int x = input.nextInt(); // 输入一个数字
```
```java
Scanner input = new Scanner(System.in);
String name = Scanner.nextLine(); // 读取一行 "test"
int age = Scanner.nextInt(); // 18
//以上输入能正常接收 name == "test" age == 18

Scanner input = new Scanner(System.in);
int age = Scanner.nextInt(); // 18
String name = Scanner.nextLine(); // 读取一行 "test"
// 以上输入不能正常接收 age == 18 name == ""

// 解决方法1
Scanner input = new Scanner(System.in);
int age = Scanner.nextInt(); // 18
Scanner.nextLine(); // 读取空的回车符
String name = Scanner.nextLine(); // 读取一行 "test"

// 解决方法2
Scanner input = new Scanner(System.in);
String name = Scanner.next(); // next也不读取回车符
int age = Scanner.nextInt(); // 18

// 解决方法3
Scanner input = new Scanner(System.in);
// 字符串转换成整数(当遇到不可转成整数时(eg: "abc")会抛出异常:NumberFormatException.)
int age = Integer.parseInt(Scanner.nextLine()); // 
String name = Scanner.nextLine();
```
## nextLine
```nextLine```方法会以回车符作为截止，将回车符之前的所有字符都读取出来，将**回车符扔掉**，把之前的所有字符组合成一个完整的字符串。
## nextInt
```nextInt```方法会以回车符作为截止，将回车符之前的所有字符都读取出来，回车符将留在队列中
## next
## 总结
1. 从读取方式上来讲，大家都是以回车符作为结束符号，除了nextLine以外其余的方法都不读取回车符

2. 从读取的返回结果来讲
    ```java
    nextInt();   // int
    nextFloat(); // float
    next()       // string  next看到回车符或空格都认为结束(eg: abc def g => abc)
    nextLine()   // string  nextLine只认为回车符结束(eg: abc def g => abc def g)
    ```
3. 利用包装类做String与基本类型的转化问题
   ```java
   int value = Integer.parseInt("1,2,3"); // 如果字符串不是一个整数形式就会产生异常
   float f = Float.parseFloat("123.13");
   ```



