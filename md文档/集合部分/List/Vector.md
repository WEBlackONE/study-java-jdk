## 1.综述

> - **Vector中的操作是线程安全的**! 一个很老的类，出现在JDK1.0。
>
> - 它常拿来和`ArrayList`做比较！**一般也不介意使用！**
>
>   1. 因为`vector`是线程安全的，所以效率低，这容易理解，类似`StringBuffer`
>   2. `Vector`空间满了之后，扩容是一倍，而`ArrayList`仅仅是一半
>   3. `Vector`分配内存的时候需要连续的存储空间，如果数据太多，容易分配内存失败
>   4. 只能在尾部进行插入和删除操作，效率低
>
>   @From：[《博客-为什么java不推荐使用vector》](http://blog.itpub.net/30046312/viewspace-2143667/)



## 2.结构关系

![](assets/1555490410(1).png)

- `Vector` 继承了`AbstractList`，实现了`List`；所以，**它是一个队列，支持相关的添加、删除、修改、遍历等功能**。
- `Vector `实现了`RandmoAccess`接口，即**提供了随机访问功能**。`RandmoAcces`是`java`中用来被`List`实现，为`List`提供快速访问功能的。在`Vector`中，我们即可以通过元素的序号快速获取元素对象；这就是快速随机访问。
- `Vector` 实现了`Cloneable`接口，即实现`clone()`函数。它能被**克隆**。

> @From:[《博客-ava 集合系列06之 Vector详细介绍(源码解析)和使用示例》](https://www.cnblogs.com/skywang12345/p/3308833.html)

## 3.属性

```java
 // 保存数据的数组
    protected Object[] elementData;
    // 数组存储的元素个数
    protected int elementCount;
    // 要扩容的长度
    protected int capacityIncrement;
```

- `elementData` 是"`Object[]`类型的数组"，它保存了添加到`Vector`中的元素。`elementData`是个动态数组，如果初始化`Vector`时，没指定动态数组的>大小，则使用默认大小**10**。随着`Vector`中元素的增加，`Vector`的容量也会动态增长，`capacityIncrement`是与容量增长相关的增长系数，具体的增长方式，请参考源码分析中的`ensureCapacity()`函数。
- `elementCount` 是动态数组的实际大小。
- `capacityIncrement `是动态数组的**增长系数**。如果在创建`Vector`时，指定了`capacityIncrement`的大小；则，每次当`Vector`中动态数组容量增加时>，增加的大小都是`capacityIncrement`。

## 4.方法

### 4.1.add()&grow()

```java
public synchronized boolean add(E e) {
    modCount++;
    ensureCapacityHelper(elementCount + 1);
    elementData[elementCount++] = e;
    return true;
}

private void ensureCapacityHelper(int minCapacity) {
    // 如果当前所需容量大于数组大小，扩容
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}

private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    // 如果传入了增长量，按照增长量来扩容
    // 如果增长量不大于0，就扩容为原来的2倍
    int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                                     capacityIncrement : oldCapacity);
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    elementData = Arrays.copyOf(elementData, newCapacity);
}

private static int hugeCapacity(int minCapacity) {
    if (minCapacity < 0) // overflow
        throw new OutOfMemoryError();
    return (minCapacity > MAX_ARRAY_SIZE) ?
        Integer.MAX_VALUE :
        MAX_ARRAY_SIZE;
}
```

- `add`方法通过使用关键字`synchronized`保证了线程安全；大部分的方法也是被`synchronized`修饰

- 它的扩容规则和`ArrayList`稍有不同，它的扩容有两个因素决定，1：是数组原始容量，2：是增长量，增长量可以通过构造方法指定。如果指定了增长量，则按照增长量来扩容，如果不指定或指定容量不大于0的话，那就扩容2倍，而`ArrayList`则是扩容1.5倍；

## 5. 其他

### 5.1.ArrayList和Vector的比较

- `Vector`底层实现和`ArrayList`一样，都是用**可变数组**来实现的；

- `Vector`扩容是根据原始容量和增长量两个因素决定，如果有有增长量且大于0，根据增长量来扩容，如果没有增长量或不大于0，扩容**2倍**，而`ArrayList`是扩容**1.5倍**；

- `Vector`通过给大部分方法添加`synchronized`关键字来实现线程同步，而`ArrayList`不是线程同步的。

- `Vector`的遍历方式还支持老的迭代器`Enumeration`这种方式，而`ArrayList`是不支持的。

  ```java
  //Vector的Enumeration遍历
  Integer value = null;
  Enumeration enu = vec.elements();
  while (enu.hasMoreElements()) {
      value = (Integer)enu.nextElement();
  }
  ```

### 参考：

> [1.《Java 集合系列06之 Vector详细介绍(源码解析)和使用示例》](https://www.cnblogs.com/skywang12345/p/3308833.html) -解释的相当全面
>
> 2.[《博客-Java1.8-Vector和Stack源码解析》](https://www.jianshu.com/p/5defd748f038) - 对结构思路理的非常清楚

