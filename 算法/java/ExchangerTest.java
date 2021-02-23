import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        //另外需要注意的是，Exchanger类仅可用作两个线程的信息交换，当超过两个线程调用同一个exchanger对象时，
        //得到的结果是随机的，exchanger对象仅关心其包含的两个“格子”是否已被填充数据，当两个格子都填充数据完成时，该对象就认为线程之间已经配对成功，然后开始执行数据交换操作。
        //————————————————

        final Exchanger exchanger = new Exchanger();
        executor.execute(new Runnable() {
            String data1 = "克拉克森，小拉里南斯";


            @Override
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });


        executor.execute(new Runnable() {
            String data1 = "格里芬";

            @Override
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });

        executor.execute(new Runnable() {
            String data1 = "哈里斯";

            @Override
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });

        executor.execute(new Runnable() {
            String data1 = "以赛亚托马斯，弗莱";

            @Override
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });

        executor.shutdown();
    }

    private static void nbaTrade(String data1, Exchanger exchanger) {
        try {
            System.out.println(Thread.currentThread().getName() + "在交易截止之前把 " + data1 + " 交易出去");
            Thread.sleep((long) (Math.random() * 1000));

            String data2 = (String) exchanger.exchange(data1);
            System.out.println(Thread.currentThread().getName() + "交易得到" + data2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
