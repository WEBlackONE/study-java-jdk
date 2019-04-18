## 1.综述

> - 当枚举元素的个数超过了64之后，就将使用`JumboEnumSet`来进行操作。
> - `JumboEnumSet`中大部分操作和`RegularEnumSet`都差不多，有一点不太一样的就是`JumboEnumSet`里的elements是个**long**类型的数组。(`RegularEnumSet`是个long类型的对象)

## 2.结构关系

![](assets/1555573180(1).png)

## 3.属性&构造方法

```java
private long elements[];
```

```java
JumboEnumSet(Class<E>elementType, Enum<?>[] universe) {
    super(elementType, universe);
    elements = new long[(universe.length + 63) >>> 6];
}
```

>  这里 `new long[(universe.length + 63) >>> 6];`，无符号右移可以大致认为除以64，计算的就是数组的容量。

## 4.方法

#### eg:addAll方法()

```java
void addAll() {
    for (int i = 0; i < elements.length; i++)
        elements[i] = -1;
    elements[elements.length - 1] >>>= -universe.length;
    size = universe.length;
}
```

- 循环设置数组里的long是-1，-1的二进制是1111....1111，所以 `elements[elements.length - 1] >>>= -universe.length;` 这一步，就是计算long数组中最后一个long元素二进制位上的1和0；

  > 比如说，枚举元素是68个，那么`elements`数组的第一个long元素已经在循环的时候设置为了-1，也就是1111....1111，进行这一步进行的就是将第二个long元素进行位移运算，结果为0000....0000 1111。

## ~~5. 其他~~



### 参考：

> 1.[《博客-Java1.8-RegularEnumSet和JumboEnumSet源码解析》](https://www.jianshu.com/p/f7035c5816b1)



