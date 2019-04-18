## 1.综述

> - `Set`体系中有一个不常用的抽象类是`EnumSet`，这个类及对应的子类是专门为**枚举**服务的，所以`EnumSet`中的数据也都是**枚举类型**。
> -  `EnumSet`是一个抽象类，有两个子类：`RegularEnumSe`t，`JumboEnumSet`

## 2.结构关系

![](assets/1555568721(1).png)

- `EnumSet`是专门用于服务枚举类型的，内部存储是通过位向量来实现的，所以性能是很好的。

- `EnumSet`和其他`Set`不太一样的地方，是它是**有序**的并且**不允许插入null**。

- 不是线程同步的，如果要线程同步，可以使用`Collections.synchronizedSet`来同步；

- 所有基本操作都在常量时间内执行。它们很可能(虽然不能保证)比`HashSet`还要快很多，如果它们的参数也是枚举，即使是批量操作也会在常量时间内执行。

- 从JDK1.5开始引入。

- 方法基本上都是静态方法。

## 3.属性

```java
// 保存的数据类型
    final Class<E> elementType;
    // 保存数据的数组
    final Enum<?>[] universe;
```

## 4.方法

```java
public static void main(String[] args) {
    //allOf()-创建一个EnumSet集合，集合元素就是Season枚举类的全部枚举值
    EnumSet<Color> colors1 = EnumSet.allOf(Color.class);
    System.out.println("allOf()======>"+colors1);
    //=================================================================
    //noneof()-创建一个EnumSet空集合，指定其集合元素是Color类的枚举值
    EnumSet<Color> colors2 = EnumSet.noneOf(Color.class);
    System.out.println("noneof()======>"+colors2);
    //=================================================================
    //add()
    colors2.add(Color.BLUE);
    colors2.add(Color.BLACK);
    System.out.println("noneof()+add()======>"+colors2);
    //=================================================================
    //of()-以指定枚举值创建EnumSet集合
    EnumSet<Color> colors3 = EnumSet.of(Color.BLACK, Color.RED);
    System.out.println("of()======>"+colors3);
    //=================================================================
    //range()-创建一个包含两个枚举值范围内所有枚举值的EnumSet集合
    EnumSet<Color> colors4 = EnumSet.range(Color.RED, Color.YELLOW);
    System.out.println("range()======>"+colors4);
    //=================================================================
    //complementOf() 补集
    EnumSet<Color> colors5 = EnumSet.complementOf(colors4);
    System.out.println("complementOf()======>"+colors5);
    //=================================================================
    //copyOf();
    EnumSet<Color> colors6_1 = EnumSet.copyOf(colors4);
    System.out.println("copyOf()======>"+colors6_1);
    //=================================================================
    ArrayList<Color> colors = new ArrayList<Color>();
    colors.add(Color.BLACK);
    colors.add(Color.BLUE);
    EnumSet<Color> colors6_2 = EnumSet.copyOf(colors);
    System.out.println("copyOf()======>"+colors6_2);
    //=================================================================
}
```

## 5. 其他

#### 5.1.noneof方法

因为`EnumSet`的其他大部分方法底层都是通过这个方法实现的，所以我们先看这个方法的源码：

```java
/**
 * 生成一个空的EnumSet，并指定其数据类型
 */
public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
    Enum<?>[] universe = getUniverse(elementType);
    if (universe == null)
        throw new ClassCastException(elementType + " not an enum");

    if (universe.length <= 64)
        return new RegularEnumSet<>(elementType, universe);
    else
        return new JumboEnumSet<>(elementType, universe);
}
```

> 其中上面有一点很明显的就是：如果枚举元素的数量**大于64**，那么实际创建的是`JumboEnumSet`实现类，否则是`RegularEnumSet`实现类。

### 参考：

> 1.[《博客-Java1.8-EnumSet源码分析》](https://www.jianshu.com/p/f73d7066e426)
>
> 2.[《博客-Java之EnumSet源码分析》](https://blog.csdn.net/qq_36336003/article/details/83118487)
>
> 3.[《博客-EnumSet基本用法》](https://www.cnblogs.com/wgl1995/p/9401652.html)



