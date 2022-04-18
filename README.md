# java-notes
## Java历史
1. Java创始人 James-Glsling
2. Java语言公司 SUN Standford University NetWork ---> SUN Microsystems
3. Java语言的几个重大事件
   1995 正式发布
   1996 正式发布了可以下载的JDK工具包 JDK1.0
   1999 发布第二代Java平台 简称Java2 细化三个不同的版本
    标准版  Standard Edition    J2SE
    企业版  Enterprise Edition  J2EE
    微型版  Micro Edition   J2ME
   2004 JDK1.5版本  添加了很多新的特性  Java5
   2005 Java6版本
   2009 Oracle公司收购SUN
## JVM(Java Virtual Machine) java虚拟机
 源文件.java -> 字节码.class
## JRE(Java Runtime Environment)运行环境
JRE包含JVM
## JDK(Java Development Kit)开发工具包
JDK包含JRE
## 配置环境变量
![图1](images/1.png "配置环境变量")
## 数据类型
### 8个基本类型
4个整型：

1. byte(1 byte = 8 bit(0 0000000))     -128 ~ +127
2. short(2 byte)            -32768 ~ 32767
3. int(4 byte)
4. long(8 byte)
   
2个浮点型
1. float(4 byte)
2. double(8 byte)

1个字符型
1. char(1 byte) _中文两个字节，unicode编码(java默认编码:0-65535)_

1个布尔型
1. boolean(1 bit)
### 引用数据类型
1. 数组([])
2. 类(class)
3. 接口(interface)
4. 枚举(enum)
5. 注解(@interface)
## 常量
**常量**是程序运行过程中，不能两次改变的**值(基本数据类型)**

eg:
```java
final int UP = 1;
```
## 变量
**变量**是程序运行过程中可以被改变的。

变量是一个内存空间（小容器）

变量空间在创建（声明）的时候，必须指定数据类型和变量空间的名字

变量空间里面只有存储一个值或引用。

变量声明：数据类型 变量名字

eg: 
```java
    int age;
```
## 注释
1. 单行注释
   ```java
   // 
   ```
2. 多行注释
    ```java
   /*

   */
   ```
3. 文档注释
   ```java
   /**

   */
   ```
## 代码执行流程与内存变化
![](images/2.png)