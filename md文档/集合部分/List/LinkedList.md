## 1.综述

> - `LinkedList`底层采用的是双向链表结构，有一个头节点first，一个尾节点last，双向链表意味着我们可以从头开始正向遍历，或者是从尾开始逆向遍历，并且可以针对头部和尾部进行相应的操作。

> - `LinkedList`是基于链表实现的，所以也就具备了链表的优点与缺点，利于插入删除，不利于查询。
> - JDK6 之前为循环链表，JDK7 取消了循环。

## 2.结构关系

![](I:\！文档库\md文档\1555485483(1).png)

1. **`AbstractSequentialList`:**这个类其实可以算简化版的`List`的实现，因为里面只有一些简单的方法，比如`get`，`set`，`add`，`remove`，`addAll`等等，但这些实现是基于迭代器的，也就是说要**遍历**链表中最多一半的数据(如果`i`大于数组大小的一半，则从尾部开始遍历)，所以性能相对于实现随机访问接口`RandomAccess`，肯定是慢的。

2. **`Deque:`**双端队列，那么这就意味着`LinkedList`是双端队列的一种实现方式，所以基于双端队列的操作在`LinkedList`中都是有效的。

   ​	**Deque是一种具有队列和栈的性质的数据结构.双端队列中的元素可以从两端弹出,其限定插入和删除操作在表的两端进行.**

   > 更多参考：[《博客-深入了解双端队列Deque》](https://blog.csdn.net/l540675759/article/details/62893335)——基于JAVA api的解读
   >
   > 更多参考：[《博客-经典算法19--双端队列》](https://blog.csdn.net/xiangxizhishi/article/details/79119501)——基于算法层面解读

   ## 3.属性

   ```java
   /**
    * 链表大小，transient修饰，不需要序列化
    */
   transient int size = 0;
   
   /**
    * 首节点，transient修饰，不需要序列化
    */
   transient Node<E> first;
   
   /**
    * 尾节点，transient修饰，不需要序列化
    */
   transient Node<E> last;
   
   private static class Node<E> {
       E item; // 节点的数据
       Node<E> next;  // 节点的上个节点的地址
       Node<E> prev;  // 节点的下个节点的地址
   
       Node(Node<E> prev, E element, Node<E> next) {
           this.item = element;
           this.next = next;
           this.prev = prev;
       }
   }
   ```

## 4.方法

### 4.1.add()

```java
public boolean add(E e) {
    // 在链表尾部添加
    linkLast(e);
    return true;
}

void linkLast(E e) {
    final Node<E> l = last;
    // 构造一个新的节点，pre指向尾节点，next指向null
    final Node<E> newNode = new Node<>(l, e, null);
    // 重新赋值尾节点
    last = newNode;
    // 如果原先尾节点是null，赋值给头节点
    if (l == null)
        first = newNode;
    // 尾节点不为空，尾节点的next为新生成的节点
    else
        l.next = newNode;
    size++;
    // 结构性修改加1，线程相关
    modCount++;
}
```

### 4.2.addFist()/addLast()

```java
public void addFirst(E e) {
    linkFirst(e);
}

public void addLast(E e) {
    linkLast(e);
}
private void linkFirst(E e) {
    final Node<E> f = first;
    final Node<E> newNode = new Node<>(null, e, f);
    first = newNode;
    if (f == null)
        last = newNode;
    else
        f.prev = newNode;
    size++;
    modCount++;
}
```

### 4.3.addAll()

```java
public boolean addAll(int index, Collection<? extends E> c) {
    // 校验index是否合法
    checkPositionIndex(index);
    // 将集合转为数组
    Object[] a = c.toArray();
    int numNew = a.length;
    // 如果数组为空，直接返回
    if (numNew == 0)
        return false;
    // 构造要插入元素位置的pre与next
    Node<E> pred, succ;
    // 如果要插入的是链表末尾
    if (index == size) {
        succ = null;
        pred = last;
    } else {
        // 不是在链表尾部插入，通过node()获取到这个位置的节点
        succ = node(index); // node(index)方法
        //1.构造两个节点pred(待插入位置的上一个节点)，succ(待插入位置的下一个节点)
		//2.遍历数组，将新节点的pre指向pred, 并将pred的next指向newNode；
		//3.将新节点的next指向succ，并将succ的pre指向新节点；
        // 将这个节点的pre指向要插入的节点
        pred = succ.prev;
    }
    // 遍历数组
    for (Object o : a) {
        @SuppressWarnings("unchecked") E e = (E) o;
        // 构造数组中的元素为Node节点，并且pre指向
        Node<E> newNode = new Node<>(pred, e, null);
        // 表示在第一个元素之前插入
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        pred = newNode;
    }
    // 在最后一个元素之后插入
    if (succ == null) {
        last = pred;
    } else {
        pred.next = succ;
        succ.prev = pred;
    }

    size += numNew;
    modCount++;
    return true;
}
```

####  4.3.1.node() 方法  -需要进行半数的遍历，效率不高了！

```java
Node<E> node(int index) {
    // 如果要查询的节点在前半段，则从前往后遍历
    if (index < (size >> 1)) {
        Node<E> x = first;
        for (int i = 0; i < index; i++)
            x = x.next;
        return x;
    // 否则从后往前遍历
    } else {
        Node<E> x = last;
        for (int i = size - 1; i > index; i--)
            x = x.prev;
        return x;
    }
}
```

## 5.参考

> 1.[《博客-精尽 Java【集合】面试题》](http://svip.iocoder.cn/Java/Collection/Interview/)
>
> 2.[《博客-Java1.8-LinkedList源码解析》](https://www.jianshu.com/p/e48ea8f009e4)
>
> 