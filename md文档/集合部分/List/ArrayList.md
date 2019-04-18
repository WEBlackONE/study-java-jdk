## 1.综述：

> - 使用数组实现的一个动态扩容的数组

> - 有索引，优点就是读取快，插入需要移动许多数据，插入慢。

## 2.属性:

```java
/**
 * 默认容量
 */
private static final int DEFAULT_CAPACITY = 10;

/**
 * 初始空数组
 */
private static final Object[] EMPTY_ELEMENTDATA = {};

/**
 * 默认初始容量下的空数组，这样我们知道在添加第一个元素的时候，应该扩容多少
 */
private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

/**
 * list实际保存数据的数组
 */
transient Object[] elementData; // non-private to simplify nested class access

/**
 * list的容量大小
 */
private int size;
```

## 3.方法：

#### 3.1.add()

> 1.判断数组容量是否为空
>
> 2.如果是，比较默认容量与实际容量大小，取最大值；
>
> 3.判断实际所需容量如果大于数组容量，扩容；
>
> 4.保存数据，size加1

**源码：**

```java
 public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}

private void ensureCapacityInternal(int minCapacity) {
    // 1.如果是空数组，取默认容量与所需容量的最大值为实际所需容量
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
    }

    ensureExplicitCapacity(minCapacity);
}

private void ensureExplicitCapacity(int minCapacity) {
    modCount++;

    // 2.如果实际所需容量大于数组容量，扩容
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}

private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    // 3.容量扩容为原来的1.5倍
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    // 4.如果扩容后的容量还是小于实际所需容量，则将扩容后的容量设置为实际所需容量
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    // 5.如果扩容后的容量超过了系统预设的最大值：Integer.MAX_VALUE - 8，检测是否溢出
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // 6.使用Arrays的copyof方法将原数组数据拷贝到新的数组，并将新数组赋值给变量elementData
    elementData = Arrays.copyOf(elementData, newCapacity);
}

private static int hugeCapacity(int minCapacity) {
    // 5.如果溢出，提示异常，如果没有溢出，实际所需容量是否超过系统预设的最大值，如果超过，返回Integer的最大值，如果没有超过，返回系统预设的最大值
    if (minCapacity < 0) // overflow
        throw new OutOfMemoryError();
    return (minCapacity > MAX_ARRAY_SIZE) ?
        Integer.MAX_VALUE :
        MAX_ARRAY_SIZE;
}
```

#### 3.2.removeAll()

> 1.先遍历`elementData`，将`elementData`与另一个集合c没有交集的数据，放置在`elementData`的下标的0到w段
>
> 2.然后再清除掉下标w到size-1之间的元素就行了(即设置为null)。

**源码：**

```java
public boolean removeAll(Collection<?> c) {
    // 参数非空校验
    Objects.requireNonNull(c);
    return batchRemove(c, false);
}

private boolean batchRemove(Collection<?> c, boolean complement) {
    final Object[] elementData = this.elementData;
    int r = 0, w = 0;
    boolean modified = false;
    try {
        for (; r < size; r++)
            if (c.contains(elementData[r]) == complement)
                elementData[w++] = elementData[r];
    } finally {
       // 如果c.contains提示异常
       if (r != size) {
            System.arraycopy(elementData, r,
                             elementData, w,
                             size - r);
            w += size - r;
        }
        // 如果有数据被删除
        if (w != size) {
            // clear to let GC do its work
            for (int i = w; i < size; i++)
                elementData[i] = null;
            modCount += size - w;
            size = w;
            modified = true;
        }
    }
    return modified;
}
```

#### 3.3.**TrimToSize**() 

>  固定集合的大小！
>
> 比如内存紧张，或者我们可以确定不会再有元素添加进来时，也可以调用该方法来节省空间。

```java
public void trimToSize() {
    modCount++;
    if (size < elementData.length) {
        elementData = (size == 0)
          ? EMPTY_ELEMENTDATA
          : Arrays.copyOf(elementData, size);
    }
}
```

#### 3.4.**ensureCapacity**()

> 从add()与addAll()方法中可以看出，每当向数组中添加元素时，都要去检查添加元素后的个数是否会超出当前数组的长度，如果超出，数组将会进行扩容，以满足添加数据的需求。
>
> 在JDK8中，JDK提供了一个public的`ensureCapacity`方法让我们可以手动设置`ArrayList`的容量，以减少上面这种递增时调用再重新分配的数量；

```java
public void ensureCapacity(int minCapacity) {
    int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
        // any size if not default element table
        ? 0
        // larger than default for default empty table. It's already
        // supposed to be at default size.
        : DEFAULT_CAPACITY;

    if (minCapacity > minExpand) {
        ensureExplicitCapacity(minCapacity);
    }
}
```

#### 3.5.其他方法[get(),clone(),writeObject()]

> 由于`ArrayList`实现了`RandomAccess`, `Cloneable`, `java.io.Serializable`，所以支持随机**读取**，**复制**，**序列化**操作，对应一些方法：

```java
/**
 * 按下标读取
 */
public E get(int index) {
    rangeCheck(index);

    return elementData(index);
}

/**
 * 复制
 */
public Object clone() {
    try {
        ArrayList<?> v = (ArrayList<?>) super.clone();
        v.elementData = Arrays.copyOf(elementData, size);
        v.modCount = 0;
        return v;
    } catch (CloneNotSupportedException e) {
        // this shouldn't happen, since we are Cloneable
        throw new InternalError(e);
    }
}

/**
 * 序列化
 */
private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException{
    // Write out element count, and any hidden stuff
    int expectedModCount = modCount;
    s.defaultWriteObject();

    // Write out size as capacity for behavioural compatibility with clone()
    s.writeInt(size);

    // Write out all elements in the proper order.
    for (int i=0; i<size; i++) {
        s.writeObject(elementData[i]);
    }

    if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
    }
}

  
private void readObject(java.io.ObjectInputStream s)
    throws java.io.IOException, ClassNotFoundException {
    elementData = EMPTY_ELEMENTDATA;

    // Read in size, and any hidden stuff
    s.defaultReadObject();

    // Read in capacity
    s.readInt(); // ignored

    if (size > 0) {
        // be like clone(), allocate array based upon size not capacity
        ensureCapacityInternal(size);

        Object[] a = elementData;
        // Read in all elements in the proper order.
        for (int i=0; i<size; i++) {
            a[i] = s.readObject();
        }
    }
}
```

## 4.问题汇总：

#### 1. `ArrayList`线程不安全体现在什么地方？

> ArrayList在添加元素的时候，可能分为两步：
>
> 扩容，在该位置设置元素值。如果两个线程同时到这一步，各自扩容之后，各自在该位置设置元素值，这样同一个位置就被set了两次，导致数据的污染或者丢失；

#### 2.如何是一个`ArrayList`线程安全？

> 可以借助`Collections`，`List list = Collections.synchronizedList(new ArrayList());`

#### 3.为什么`ArrayList`没有并发实现呐？

**详细描述：**

JDK 5在`java.util.concurrent`里引入了`ConcurrentHashMap`，在需要支持高并发的场景，我们可以使用它代替`HashMap`。但是为什么没有`ArrayList`的并发实现呢？难道在多线程场景下我们只有`Vector`这一种线程安全的数组实现可以选择么？为什么在`java.util.concurrent` 没有一个类可以代替Vector呢？

> 主要原因：很难去开发一个通用并且没有并发瓶颈的线程安全的List。
>
> 解释：
>
> - 1.像`ConcurrentHashMap`这样的类的真正价值（The real point / value of classes）并不是它们保证了线程安全。而在于它们在保证线程安全的同时不存在并发瓶颈。举个例子，`ConcurrentHashMap`采用了锁分段技术和弱一致性的`Map`迭代器去**规避并发瓶颈**。
>      所以问题在于，像`Array List`这样的数据结构，**你不知道如何去规避并发的瓶颈**。拿`contains() `这样一个操作来说，当你进行搜索的时候如何避免锁住整个`list`？
> - 2.另一方面，`Queue `和`Deque` (基于`Linked List`)有并发的实现是因为他们的接口相比`List`的接口有更多的限制，这些限制使得实现并发成为可能。
>
> 举例`CopyOnWriteArrayList`分析：
>
> 它规避了只读操作（如get/contains）并发的瓶颈，但是它为了做到这点，在修改操作中做了很多工作和修改可见性规则。 此外，修改操作还会锁住整个List，因此这也是一个并发瓶颈。所以从理论上来说，`CopyOnWriteArrayList`**并不算是一个通用的并发List。**

## 参考

> From: [《博客-Java1.8-ArrayList源码解析》](https://www.jianshu.com/p/34406cecbc82) - 整理流程
>
> From:[《博客-为什么java.util.concurrent 包里没有并发的ArrayList实现？》](http://ifeve.com/why-is-there-not-concurrent-arraylist-in-java-util-concurrent-package/) - 针对4.3问题









