>  在Java5中，专门提供了锁对象，利用锁可以方便的实现资源的封锁，用来控制对竞争资源并发访问的控制，这些内容主要集中在java.util.concurrent.locks 包下面，里面有三个重要的接口Condition、Lock、ReadWriteLock。

**Condition**`：Condition` 将 `Object` 监视器方法（`wait`、`notify` 和 `notifyAll`）分解成截然不同的对象，以便通过将这些对象与任意 [`Lock`](mk:@MSITStore:F:\CHM\jdk150.ZH_cn.chm::/jdk150/api/java/util/concurrent/locks/Lock.html)实现组合使用，为每个对象提供多个等待 set （wait-set）。

**Lock**`：Lock` 实现提供了比使用 `synchronized` 方法和语句可获得的更广泛的锁定操作。

**ReadWriteLock**`：ReadWriteLock` 维护了一对相关的[`锁定`](mk:@MSITStore:F:\CHM\jdk150.ZH_cn.chm::/jdk150/api/java/util/concurrent/locks/Lock.html)，一个用于只读操作，另一个用于写入操作。