package proAndCom;

public class WaitPerson implements Runnable {
    private Restaurant restaurant;

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal == null) {//meal消费光
                        wait();//消费者线程wait，其他线程可以获取waitPerson对象锁
                    }
                }
                //消费者线程被唤醒后，从第一个while顶部跳出，进入消费步骤
                System.out.println("waitPerson got" + restaurant.meal);

                synchronized (restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("waitPerson interrupt");
        }
    }
}
