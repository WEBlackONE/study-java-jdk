## 1.综述

> -  `TreeSet`默认构造方法就是`new`一个`TreeMap`，`TreeSet`中使用了一个`NavigableMap`变量来保存数据，而`TreeMap`又是实现了`NavigableMap`，这样可以多态的方式使用`TreeMap`来处理`TreeSet`。

## 2.结构关系

![](I:\！文档库\md文档\1555554761(1).png)

## 3.属性

```java
 /**
     * The backing map.
     */
	 //定义NavigableMap接口类型m
    private transient NavigableMap<E,Object> m;
 
	//申请类型为Object的PRESENT，作为key-value值中的value值，将我们需要保存的值放入key中
    // Dummy value to associate with an Object in the backing Map
    private static final Object PRESENT = new Object();
```

## ~~4.方法~~



## 5. 其他

#### 1. `TreeSet`和`TreeMap`

- **相同点：** 
  - `TreeMap`和`TreeSet`都是有序的集合。 
  - `TreeMap`和`TreeSet`都是非同步集合，因此他们不能在多线程之间共享，不过可以使用方法`Collections.synchroinzedMap()`来实现同步。 
  - 运行速度都要比`Hash`集合慢，他们内部对元素的操作时间复杂度为`O(logN)`，而`HashMap/HashSet`则为`O(1)`。

- **不同点：** 
  - 最主要的区别就是`TreeSet`和`TreeMap`分别实现`Set`和`Map`接口 
  - `TreeSet`只存储一个对象，而`TreeMap`存储两个对象`Key`和`Value`（仅仅key对象有序） 
  - `TreeSet`中不能有重复对象，而`TreeMap`中可以存在。

### 参考：

> 1.[《博客-Java1.8-HashSet-LinkedHashSet-TreeSet源码解析》](https://www.jianshu.com/p/7081ba756345)
>
> 2.[《博客-Java语言之TreeSet源码分析（jdk1.8》](https://blog.csdn.net/qq_36441169/article/details/80835438)



