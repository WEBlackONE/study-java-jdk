- 控制并发安全的途径之一：
  - 1.把竞争访问的资源标识为private；
  - 2.同步哪些修改变量的代码，使用synchronized关键字同步方法或代码。

> synchronized关键字使用说明：synchronized只能标记非抽象的方法，不能标识成员变量。



##  一.同步方法



## 二.同步块

> 对于同步，除了同步方法外，还可以使用同步代码块，有时候同步代码块会带来比同步方法更好的效果。