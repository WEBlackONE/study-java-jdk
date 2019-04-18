## 1.综述

> - `1.HashSet` 是一个**没有重复元素的集合**。
>
> - `HashSet` 是由`HashMap`实现的，**无序**，而且**`HashSet`允许使用 `null` 元素**。(仅有一个空值。)
>
> - `HashSet`是**非同步的**。(线程不安全的)
>
>   - 如果多个线程同时访问一个哈希` set`，而其中至少一个线程修改了该 `set`，那么它必须 保持外部同步。这通常是通过对自然封装该 `set` 的对象执行同步操作来完成的。如果不存在这样的对象，则应该使用 `Collections.synchronizedSet` 方法来“包装” `set`。最好在创建时完成这一操作，以防止对该` set` 进行意外的不同步访问：
>
>     ```java
>     Set s = Collections.synchronizedSet(new HashSet(...));  
>     ```

## 2.结构关系

![](assets/1555493024(1).png)

> 参考API文档，`HashSet`继承了`AbstractSet`类，实现了`Set`接口，由`Hash`表支持(实际上就是一个**HashMap**的实例)。`HashSet`不保证该类中`set`的遍历顺序，也并不保证`set`中数据顺序的永久不变。

## 3.属性

```java
// HashSet的底层是通过HashMap来实现的，并且该HashMap字段是transient类型的， 无法序列化
private transient HashMap<E,Object> map;

// 定义该HashMap的value为一个静态无法被修改的虚拟值，因为HashSet中只需要用到key，
// 而HashMap是key-value键值对.所以，向map中添加键值对时，键值对的值固定是PRESENT
private static final Object PRESENT = new Object();

/**
 * 默认构造函数，底层初始化一个HashMap，hashMap的初始容量是16，负载因子是0.75
 */
public HashSet() {
    map = new HashMap<>();
}

/**
   *集合转换为HashSet的构造函数, 分为两步操作：
   * 
   * 1. 返回(c.size()/.75f) + 1 和 16两者比较大的数字，为什么呢？
   * 因为HashMap的默认初始容量是16，而负载因子是0.75，HashMap什么时候会扩容呢，是当HashMap
   * 的阈值即(初始容量*负载因子) 大于HashMap实际大小的时候，HashMap就会扩容，所以(c.size()/.75f) +1即是实际的大小；
   * 
   * 2. 将集合c的元素全部添加到HashSet中；
   * 
   * 如果参数c为null，将会抛出空指针异常；
 */
public HashSet(Collection<? extends E> c) {
    map = new HashMap<>(Math.max((int) (c.size()/.75f) + 1, 16));
    addAll(c);
}

/**
 * 构造一个指定容量和负载因子的hashMap,
 * 如果初始容量或者负载因子小于0，则抛出IllegalArgumentException异常
 */
public HashSet(int initialCapacity, float loadFactor) {
    map = new HashMap<>(initialCapacity, loadFactor);
}

/**
 * 构造一个指定容量的HashMap,默认负载因子是0.75
 * 如果初始容量小于0，则抛出IllegalArgumentException异常
 */
public HashSet(int initialCapacity) {
    map = new HashMap<>(initialCapacity);
}

/**
 * 构造一个指定容量和负载因子的HashSet，使用LinkedHashMap实现，这个方法不对外，只是应用于 LinkedHashSet 。
 * 其中参数dummy无实际意义，只是一个标志而已，请忽略。
 */
HashSet(int initialCapacity, float loadFactor, boolean dummy) {
    map = new LinkedHashMap<>(initialCapacity, loadFactor);
}
```

## 4.方法

`HashSet`中方法的实现基本都是通过`HashMap`来实现的，所以只要了解了`HashMap`，自然就明白了`HashSet`的方法。

> 不过，JDK1.8新增了一个`spliterator`方法：这个方法返回一个迭代器，这种迭代器被成为可分割迭代器，是Java为了**并行**遍历数据元素而设计的。而原先的迭代器`Iterator`是一种顺序迭代器，这两种迭代器可以放在一起理解。

```java
public Spliterator<E> spliterator() {
        return new HashMap.KeySpliterator<E,Object>(map, 0, -1, 0, 0);
    }
```

## 5. 其他



### 参考：

> 1.[《Java1.8-HashSet-LinkedHashSet-TreeSet源码解析》](https://www.jianshu.com/p/7081ba756345)
>
> 2.[《博客-Java 集合系列16之 HashSet详细介绍(源码解析)和使用示例》](https://www.cnblogs.com/skywang12345/p/3311252.html)
>
> 



