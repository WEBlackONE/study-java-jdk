## 1.综述

> - `EnumMap`是一种键是枚举类型的`Map`的实现
> - `EnumMap`的底层实现是通过数组来实现的

## 2.结构关系

![](assets/1556093317(1).png)

## 3.属性

```java
private final Class<K> keyType;

// EnumMap中存储key的数组
private transient K[] keyUniverse;

// 存储EnumMap的数组，允许为null，如果为null，会被替换为内部定义的Object变量NULL
private transient Object[] vals;

// 内部变量，用于代替value中出现的null
private static final Object NULL = new Object() {
    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "java.util.EnumMap.NULL";
    }
};
```

## 4.方法

#### 4.1.put()方法

```java
public V put(K key, V value) {
    typeCheck(key);

    int index = key.ordinal();
    Object oldValue = vals[index];
    vals[index] = maskNull(value);
    if (oldValue == null)
        size++;
    return unmaskNull(oldValue);
}
```

> 1. 检查key是否是枚举类型；
> 2. 使用key的下标作为value数组的下标；
> 3. 校验是否为null；
> 4. 判断原先下标处是否有值，如果有值，即是覆盖，map大小不变；
> 5. 将原值返回；

#### 4.2.get()方法

```java
public V get(Object key) {
    return (isValidKey(key) ?
            unmaskNull(vals[((Enum<?>)key).ordinal()]) : null);
}
```

> 1. 校验key是否是枚举，key为空或类型不匹配返回null；
> 2. 由于value保存的是数组，所以使用key的ordinal也即是下标值，从value数组中直接取出即可；



## 5. 其他

#### 5.1.总结

> 1. 由于`EnumMap`的`key`是**枚举**类型，而枚举的属性`ordinal`是固定的，并且`EnumMap`的`value`存储的是一个数组，所以`key`的`ordinal`与`value`数组的下标的对应关系在构造方法中已经固定了，所以`get`的时候效率自然会很高；
>
> 2. 由于`key`是枚举，所以`key`不允许`null`，而`value`是允许`null`的，并且`null`会被`EnumMap`的NULL实例对象所替换；
>
> 3. 元素的存储顺序是根据枚举的生命的次序来确定的。
>
> 4. `EnumMap`不是线程安全的，如果要线程安全，可以使用`Collections`的`synchronizedMap`方法；

### 参考：

> 1.[《博客-Java1.8-EnumMap源码解析》](https://www.jianshu.com/p/6681e2660b35)



