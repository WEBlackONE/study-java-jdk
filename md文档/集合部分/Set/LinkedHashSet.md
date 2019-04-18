## 1.综述

> - `LinkedHashSet`内部使用的是`LinkHashMap`。
> - `LinkedHashSet`中的元素是有序的。

## 2.结构关系

![](I:\！文档库\md文档\1555552504(1).png)

> `LinkedHashSet`继承自`HashSet`，所有的构造方法都会调用父类`HashSet`的一个构造方法，使用底层的`LinkedHashMap`去实现功能。

## ~~3.属性~~



## ~~4.方法~~



## 5. 其他

> 对`LinkedHashSet`来讲，和`HashSet`与`HashMap`的关系类似，`LinkedHashSet`底层是通过`LinkedHashMap`来实现的，只要搞懂了`LinkedHashMap`，自然就懂了`LinkedHashSet`。

### 参考：

> 1.[《博客-Java1.8-HashSet-LinkedHashSet-TreeSet源码解析》](https://www.jianshu.com/p/7081ba756345)
>
> 2.[《博客-搞懂 HashSet & LinkedHashSet 源码 以及集合常见面试题目》](https://blog.csdn.net/learningcoding/article/details/79983248)
>
> 3[《博客-【Java集合系列四】HashSet和LinkedHashSet解析》](https://www.cnblogs.com/wlrhnh/p/7256969.html)



