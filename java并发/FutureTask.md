futureTask是带返回值的线程对象，futureTask的get()方法可以阻塞直到线程执行完毕并返回值。

其内部通过一个state状态维护运行状态

- 0 NEW：初始状态
- 1 COMPLETING：任务执行完成或异常，但结果还未传递，属于中间态
- 2 NORMAL：任务完成态
- 3 EXCEPTIONAL：任务异常态
- 4 CANCELLED：任务取消态，只为开始就取消
- 5 INTERRUPTING：调用cancel()方法取消任务的中间态
- 6 INTERRUNPED：中断任务的最终态

其state转换的方式只有以下几种

- 0 -> 1 -> 2 任务成功的流程
- 0 -> 1 -> 3 任务异常的流程
- 0 -> 4 任务未开始就取消的流程
- 0 -> 5 -> 6 任务中断的流程

在执行run()方法时：

如果异常，那么进入0 -> 1 -> 3d的异常流程

如果成功执行，那么返回outcome返回值，执行的是0 -> 1 -> 2的流程

如果还没开始执行，那么调用awaitDone方法等待返回值，
其内部逻辑如下
````
for (;;) {
    if (Thread.interrupted()) {
        removeWaiter(q);
        throw new InterruptedException();
    }
    int s = state;
    if (s > COMPLETING) {
        if (q != null)
            q.thread = null;
        return s;
    }
    //如果在中间态，表示结果马上可以得到，那么线程短暂让出CPU时间片
    else if (s == COMPLETING) 
        Thread.yield();
    //如果暂时拿不到结果，那么生成一个阻塞节点
    else if (q == null)
        q = new WaitNode();
    //将这个阻塞节点放入等待队列最后
    else if (!queued)
        queued = UNSAFE.compareAndSwapObject(this, waitersOffset,
                                             q.next = waiters, q);
    else if (timed) {
        nanos = deadline - System.nanoTime();
        if (nanos <= 0L) {
            removeWaiter(q);
            return state;
        }
        LockSupport.parkNanos(this, nanos);
    }
     //阻塞节点也生成了，还是没有得到结果，那么挂起自身，等待唤醒
    else
        LockSupport.park(this);
}
