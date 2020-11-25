###1.countdownlatch

countdownlatch的设计思路是一个或多个线程等待多个线程全部完成某件事之后进行工作。

其构造函数就是指定一个计数，其中每一个线程完成之后，其计数减1，等到所有计数减到0之后，await()方法（可能有多个）被唤醒执行。

- countDown()方法：被等待线程调用这个方法时，进入到tryRealeaseShared中，将state减1（state在这里的含义又变为了计数器，所以AQS的底层设计是很有弹性的），
表示计数减1，如果state为0，则进入唤醒逻辑。

- await()方法：等待线程调用这个方法，如果state大于0，表示计数未完成，那么加入共享节点到队列后，然后挂起自身。
当被唤醒时，是共享锁的唤醒逻辑，即不断链式唤醒后续的节点，直到所有等待线程被唤醒。

###2.cyclicBarrier

cyclicBarrier的设计思路是多个线程相互等待，如n个线程全部完成某一事后，进行下一个工作，再全部完成某件事后，在进行下一个工作。

- await()：cyclicBarrier只有这一个核心方法，线程会阻塞在await处，直到所有线程都达到await处，然后继续执行。

await是基于reentrantlock的condition实现。

存在一个count属性，记录数量，当某个线程完成时，count减1，如果不为0，表示还有其他线程未执行完，那么调用condition.await()阻塞线程，
当最后一个线程完成时，count=0，那么进入唤醒流程，condition.signalAll会唤醒所有线程继续执行。