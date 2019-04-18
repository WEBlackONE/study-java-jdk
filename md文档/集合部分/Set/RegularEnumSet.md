## 1.综述

> - `RegularEnumSet`是枚举类型的私有实现类，我们无法直接调用，我们只能使用`EnumSet`，而`EnumSet`则会根据`length`(64)相应的调用`RegularEnumSet`实现类；
> - `RegularEnumSet`保存数据和常规的Set不同，`RegularEnumSet`中只有一个`long`类型的`elements`变量，这是因为保存的时候保存的并不是实际的元素，而是保存的是`bit`，**0和1**；
> - 其实`RegularEnumSet`中进行的操作就是围绕长整型`elements`的二进制位上的1和0进行的。添加元素，设置为1，删除元素，设置为0，清空，直接将该长整型置为0。

## 2.结构关系

![](I:\！文档库\md文档\1555571800(1).png)

## 3.属性

```java
/**
 * Private implementation class for EnumSet, for "regular sized" enum types
 * (i.e., those with 64 or fewer enum constants).
 */
class RegularEnumSet<E extends Enum<E>> extends EnumSet<E> {
    // 使用位向量保存
    private long elements = 0L;
    // 构造方法也是调用抽象类的构造方法来实现
    RegularEnumSet(Class<E>elementType, Enum<?>[] universe) {
        super(elementType, universe);
    }
}
```

## 4.方法

####  4.1. add()

```java
/**
 * 如果元素不存在，添加
 */
public boolean add(E e) {
    // 校验枚举类型
    typeCheck(e);

    long oldElements = elements;
    elements |= (1L << ((Enum<?>)e).ordinal());
    return elements != oldElements;
}

/**
 * 用于校验枚举类型，位于EnumSet中
 */
final void typeCheck(E e) {
    Class<?> eClass = e.getClass();
    if (eClass != elementType && eClass.getSuperclass() != elementType)
        throw new ClassCastException(eClass + " != " + elementType);
}
```

> - add之后，elements二进制对应的ordinal位设置为了1。其实，add操作就是设置long类型的elements对应下标位置的值是0或者是1，也就是将每一个枚举元素在elements的二进制中占用一位。因为long是64位，所以RegularEnumSet的长度自然是不能大于64的。
> - 判断元素是否添加成功，直接通过判断添加前后elements的值有没有变化来判断。

#### 4.2size()

```java
public int size() {
        return Long.bitCount(elements);
    }
	//java.lang.Long#bitCount
     public static int bitCount(long i) {
        // HD, Figure 5-14
        i = i - ((i >>> 1) & 0x5555555555555555L);
        i = (i & 0x3333333333333333L) + ((i >>> 2) & 0x3333333333333333L);
        i = (i + (i >>> 4)) & 0x0f0f0f0f0f0f0f0fL;
        i = i + (i >>> 8);
        i = i + (i >>> 16);
        i = i + (i >>> 32);
        return (int)i & 0x7f;
     }
```

> 其实`size`方法的实现是通过`Long`类型的`bitCount`来实现的，=>就是统计`long`类型二进制中**1**的个数。

#### 4.3.addAll()

```java
void addAll() {
    if (universe.length != 0)
        elements = -1L >>> -universe.length;
}
```

> `addAll`方法就是将`elements`上，从低位到枚举长度上的下标值置为**1**。比如某一个枚举类型共5个元素，而`addAll`就是将`elements`的二进制的低5位置为1。

> 更多信息参考：@From:[《博客-Java位运算学习》](https://www.jianshu.com/p/a691419bdce0)

#### 4.4.addRange()

```java
void addRange(E from, E to) {
    elements = (-1L >>>  (from.ordinal() - to.ordinal() - 1)) << from.ordinal();
}
```

> 该方法是添加枚举中某一段范围内的元素。这个方法同样设计的也很精巧，先右无符号位移，将最低位置为0，然后左移对应的位置即可。

#### 4.5.complement()

```java
void complement() {
    if (universe.length != 0) {
        elements = ~elements;
        elements &= -1L >>> -universe.length;  // Mask unused bits
    }
}
```

> 这个方法其实和上面类似，只不过多了一步按位非的操作。

#### 番外：EnumSetIterator#next()

```java
private class EnumSetIterator<E extends Enum<E>> implements Iterator<E> {
    /**
     *  elements的值
     */
    long unseen;

    /**
     * elements二进制对应的1的位置
     */
    long lastReturned = 0;

    EnumSetIterator() {
        unseen = elements;
    }

    @SuppressWarnings("unchecked")
    public E next() {
        if (unseen == 0)
            throw new NoSuchElementException();
        lastReturned = unseen & -unseen;
        unseen -= lastReturned;
        return (E) universe[Long.numberOfTrailingZeros(lastReturned)];
    }
}
```

>- `lastReturned = unseen & -unseen;` 计算的是`unseen`的二进制最低位第一个非0位代表的十进制数。如果`unseen`是0111，那返回的就是0001，十进制是1，如果unseen是0100，那返回的就是0100，十进制是4。
>     而 `Long.numberOfTrailingZeros(lastReturned)` 计算的是`lastReturned`从最低位开始，第一位为1的下标值（或者换一种说法，就是从最低位开始，到第一位为1这中间0的个数）。比如`lastReturned`是4，二进制是0100，那这里返回的就是2；如果`lastReturned`是0101，那这里返回的就是0。

## ~~5. 其他~~



### 参考：

> 1.[《博客-Java1.8-RegularEnumSet和JumboEnumSet源码解析》](https://www.jianshu.com/p/f7035c5816b1)
>
>  

