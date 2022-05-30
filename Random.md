## Random类
### import
```java
import java.util
```
### 常用方法
```java
Random r = new Random();

// 随机产生一个整数[-2147483648, 2147483647]
int value = r.nextInt();
// 随机产生一个整数[0, 10)
int result = r.nextInt(10); 

// 随机产生一个小数[0.0, 1.0)
float f = r.nextFloat();

 boolean b = r.nextBoolean();

```