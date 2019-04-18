###  1.sort()排序算法

> 在使用List时根据List中存储对象的某一字段进行排序，那么我们要用到Collections.sort方法对list排序，用Collections.sort方法对list排序有两种方法：

- 1.1.第一种是`list`中的对象实现`Comparable`接口；

- 1.2.第二种方法是根据`Collections.sort`重载方法来实现;



#### 1.1.第一种是`list`中的对象实现`Comparable`接口；

```java
@SuppressWarnings("unchecked")
public static <T extends Comparable<? super T>> void sort(List<T> list) {
        list.sort(null);
    }
```




#### 1.2.第二种方法是根据`Collections.sort`重载方法来实现;

```java
@SuppressWarnings({"unchecked", "rawtypes"})
public static <T> void sort(List<T> list, Comparator<? super T> c) {
        list.sort(c);  
    }
```

> @参考:[《博客-Collections类常用方法总结》](https://www.cnblogs.com/Eason-S/p/5786066.html)

### 2.shuffle() 随机排序（洗牌）

### 3.binarySearch()   折半查找法

和`sort（）`类似

3.1.`extends Comparable`

```java
public static <T>
int binarySearch(List<? extends Comparable<? super T>> list, T key) {
    if (list instanceof RandomAccess || list.size()<BINARYSEARCH_THRESHOLD)
        return Collections.indexedBinarySearch(list, key);
    else
        return Collections.iteratorBinarySearch(list, key);
}
```

3.2.`Comparator`

```java
   @SuppressWarnings("unchecked")
    public static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c) {
        if (c==null)
            return binarySearch((List<? extends Comparable<? super T>>) list, key);

        if (list instanceof RandomAccess || list.size()<BINARYSEARCH_THRESHOLD)
            return Collections.indexedBinarySearch(list, key, c);
        else
            return Collections.iteratorBinarySearch(list, key, c);
    }
```

- 必须根据列表元素的自然顺序对列表进行升序排序（通过 sort(List) 方法）。
- 如果没有对列表进行排序，则结果是不确定的。如果列表包含多个等于指定对象的元素，则无法保证找到的是哪一个。

> @参考：[《博客-Collections框架中sort自然排序binarySearch二分查找详解》](https://blog.csdn.net/yaomingyang/article/details/80618663)
>
> @参考：[《博客-Java基础之集合框架--Collections.binarySearch()》](https://blog.csdn.net/ljh_learn_from_base/article/details/78006787)-  提供了对源码的解释

### 4.max()/min()

4.1.MAX/MIN

```JAVA
 public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll) {
        Iterator<? extends T> i = coll.iterator();
        T candidate = i.next();

        while (i.hasNext()) {
            T next = i.next();
            if (next.compareTo(candidate) > 0)
                candidate = next;
        }
        return candidate;
    }
```

```java
public static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp) {
    if (comp==null)
        return (T)max((Collection) coll);

    Iterator<? extends T> i = coll.iterator();
    T candidate = i.next();

    while (i.hasNext()) {
        T next = i.next();
        if (comp.compare(next, candidate) > 0)
            candidate = next;
    }
    return candidate;
}
```

- min()/max()都使用的是迭代器查找
- 可以利用`Comparator`来自定义比较规则                                                                                       

> @参考：[《博客-Collections中min和max工具方法详解》](https://blog.csdn.net/yaomingyang/article/details/80711193)

- 

- ~~1.5.min()~~

- 1.6.indexOfSubList() -查找subList在list中首次出现位置的索引

- 1.7.lastIndexOfSubList()

- 1.8.replaceAll() -替换批定元素为某元素,若要替换的值存在刚返回true,反之返回false

  ### 1.9.reverse() -列表反转方法

  - 如果列表支持随机访问或者列表大小小于要反转的阈值18，则直接采用交换操作；
  - 否则采用双迭代操作，一个从头遍历，一个从尾遍历，然后交换。

  ```java
      public static void reverse(List<?> list) {
          int size = list.size();
          if (size < REVERSE_THRESHOLD || list instanceof RandomAccess) {
              for (int i=0, mid=size>>1, j=size-1; i<mid; i++, j--)
                  swap(list, i, j);
          } else {
              // 迭代器遍历从头开始（forward）
              ListIterator fwd = list.listIterator();
              // 迭代器遍历从尾部开始（reverse）
              ListIterator rev = list.listIterator(size);
              for (int i=0, mid=list.size()>>1; i<mid; i++) {
                  // 从头开始
                  Object tmp = fwd.next();
                  // 设置fwd下一个元素为rev前一个元素，交换
                  fwd.set(rev.previous());
                  rev.set(tmp);
              }
          }
      }
  ```

  其中执行直接交换操作的swap方法很精妙，借助list的set方法返回旧值的属性，使用双重set方式来实现交换：

  **swap方法**

  ```java
  public static void swap(List<?> list, int i, int j) {
      final List l = list;
      l.set(i, l.set(j, l.get(i)));
  }
  ```

  `l.set(j, l.get(i))` 这里在设置j处为新的值的同时，会返回索引`j`处原来的值，然后再次`set`，很巧妙的实现了交换操作。

  > @ 参考：[《博客-Java1.8-Collections源码解析》](https://www.jianshu.com/p/51ce612db017)

- 1.10.rotate() -对集合进行旋转操作

- 1.11.copy() - 将原集合中元素拷贝到另一个集合中。-原列表的大小不能大于目标列表的大小

- 1.12.swap() 

- 1.13.fill()

- 1.14.nCopies()

> ***** * * * * @参考：[Java1.8-Collections源码解析](https://www.jianshu.com/p/51ce612db017)

