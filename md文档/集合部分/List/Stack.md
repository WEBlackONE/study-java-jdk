## 1.综述

> - `Stack`是**栈**。它的特性是：**先进后出**(FILO, First In Last Out)。
> - `Stack`也是线程安全的。

## 2.结构关系

![](assets/1555491861(1).png)

- 继承自`Vector`，没有其他的继承与实现关系。它额外提供了**5**种操作方法：
  `push`（添加元素），`pop`（获取并移除栈顶元素），`peek`（只查看栈顶元素不移除），`empty`（为空判断），`search`（查找元素位置）。

## ~~3.属性~~

## 4.方法

```java
//==============================================================
     // push函数：将元素存入栈顶
	public E push(E item) {
         // 将元素存入栈顶。
         // addElement()的实现在Vector.java中
         addElement(item);
 
         return item;
     }
//==============================================================
     // pop函数：返回栈顶元素，并将其从栈中删除
     public synchronized E pop() {
         E    obj;
         int    len = size();

         obj = peek();
         // 删除栈顶元素，removeElementAt()的实现在Vector.java中
         removeElementAt(len - 1);
 
         return obj;
     }
//============================================================== 
    // peek函数：返回栈顶元素，不执行删除操作
     public synchronized E peek() {
         int    len = size();
 
         if (len == 0)
             throw new EmptyStackException();
         // 返回栈顶元素，elementAt()具体实现在Vector.java中
         return elementAt(len - 1);
     }
//==============================================================  
     // 栈是否为空
     public boolean empty() {
         return size() == 0;
     }
//==============================================================  
     // 查找“元素o”在栈中的位置：由栈底向栈顶方向数
     public synchronized int search(Object o) {
         // 获取元素索引，elementAt()具体实现在Vector.java中
         int i = lastIndexOf(o);
 
         if (i >= 0) {
             return size() - i;
         }
         return -1;
     }
 }
```

**总结**：

- (01) `Stack`实际上也是通过**数组**去实现的。
         执行**push**时(即，**将元素推入栈中**)，是通过将元素追加的数组的末尾中。
         执行**peek**时(即，**取出栈顶元素，不执行删除**)，是返回数组末尾的元素。
         执行**pop**时(即，**取出栈顶元素，并将该元素从栈中删除**)，是取出数组末尾的元素，然后将该元素从数组中删除。

## 5. 其他

- `Stack`继承于`Vector`，意味着`Vector`拥有的属性和功能，`Stack`都拥有。

### 参考：

> 1.[《Java 集合系列07之 Stack详细介绍(源码解析)和使用示例》](https://www.cnblogs.com/skywang12345/p/3308852.html)-很详细
>
> 2.[《Java1.8-Vector和Stack源码解析》](https://www.jianshu.com/p/5defd748f038)-



