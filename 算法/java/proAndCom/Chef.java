package proAndCom;

import java.util.concurrent.TimeUnit;

public class Chef implements Runnable{
    private Restaurant restaurant;

    private int count = 0;

    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal != null) {
                        wait();//已生产，生产者线程堵塞
                    }
                }

                if (++count == 10) {
                    System.out.println("out of food");
                    restaurant.exec.shutdownNow();
                }

                synchronized (restaurant.waitPerson) {//消费者wait之后，这里就可以获取到对象锁
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();//唤醒消费者
                }

                //shutdownNow()是在本线程内执行，如果没有sleep调用，那么程序从顶部的while跳出，并不会抛出异常
                //如果有sleep调用，程序在进入这个试图阻塞的操作将抛出异常
                System.out.println("order up");

                //TimeUnit.MILLISECONDS.sleep(100);

            }
        } catch (InterruptedException e) {
            System.out.println("chef interrupt");
        }
    }
}
