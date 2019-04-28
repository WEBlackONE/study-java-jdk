> @From:[《博客-Java1.8-WeakHashMap源码解析》](https://www.jianshu.com/p/16764bff0a5d)

## 1.综述

> - `WeakHashMap`是一种基于`Java`的弱引用的哈希表实现。它的目的和常规的Map实现有些不同，它主要是用于优化JVM，使JVM在进行垃圾回收的时候能智能的回收那些无用的对象。
> - 和HashMap类似，支持key和value为null；
> - 同样不是线程安全的，可以使用Collections.synchronizedMap来使之线程安全；
> - 没有实现Cloneable, Serializable接口；
> - 目前是在tomcat的`ConcurrentCache`中使用到了它。

## 2.结构关系

![](E:/soft/24.Typora/Typora)

## 3.属性



## 4.方法



## 5. 其他



### 参考：

> 1.[《博客-》]()
>
> 



