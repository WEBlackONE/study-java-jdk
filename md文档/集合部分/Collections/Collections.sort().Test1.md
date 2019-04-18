### 1.1.第一种是`list`中的对象实现`Comparable`接口；

1. **源码：**`java.util.Collections.sort()`

```java
@SuppressWarnings("unchecked")
public static <T extends Comparable<? super T>> void sort(List<T> list) {
        list.sort(null);
    }
```

2.   **准备测试实体**

   2.1. `userPo` 实现`Comparable`

   2.2.重写`compareTo()`方法，实现具体的比较项

   ```java
   @Data
   @AllArgsConstructor
   @ToString
   public class UserPo implements Comparable<UserPo>{
   
       private String name;
       //要进行封箱处理 int->不得行
       private Integer age;
   
       public int compareTo(UserPo o) {
           return this.age.compareTo(o.age);
       }
   }
   
   -----------------------------------------------------
       // lombok pom包
       <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok-maven-plugin</artifactId>
               <version>1.18.4.0</version>
               <scope>provided</scope>
           </dependency>
   -----------------------------------------------------    
   ```

   **3.测试**

   ```java
       public static void main(String[] args) {
           //test: situation 1
           List<UserPo> userPos = new ArrayList<UserPo>();
           
           UserPo userPo1 = new UserPo("苏明玉",25);
           UserPo userPo2 = new UserPo("苏明成",35);
           UserPo userPo3 = new UserPo("苏明哲",45);
           UserPo userPo4 = new UserPo("苏大强",55);
           
           userPos.add(userPo2);
           userPos.add(userPo4);
           userPos.add(userPo1);
           userPos.add(userPo3);
           
           Collections.sort(userPos);
           
           System.out.println(userPos);
       }
   }
   ```

   **4.测试结果**

   ```java
   [UserPo(name=苏明玉, age=25), UserPo(name=苏明成, age=35), UserPo(name=苏明哲, age=45), UserPo(name=苏大强, age=55)]
   ```

   