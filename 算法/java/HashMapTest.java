import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

public class HashMapTest {

    public static AtomicInteger count = new AtomicInteger(0);
    //public static LongAdder add = new LongAdder(0);

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>(4, 1);
        map.put(null, "aaaa");
        map.put("sdsadasd", "ccccccccc");
        map.put("Aa", "momo");
        map.put("fff", "momo");
        map.put("jjjjj", "momo");
        //map.put("BB", "sdsadd");
        //ap.put("BB", "sadsadsa");

        //System.out.println(map.put("BB", "sadsadsa"));
        int h = 1234578974;
        System.out.println(map.get(null));
        //System.out.println((h >>> 16));
        System.out.println("Aa".hashCode());
        System.out.println("BB".hashCode());
        String a = "cccc";
        System.out.println(a.getClass().getGenericInterfaces());
        System.out.println((500787631 & 15));
        ConcurrentHashMap<String, String> con = new ConcurrentHashMap<>();
        con.put("aaa","bbb");

        count.getAndIncrement();


        //4 4
        //7 15
        /*1 1101 1101 1001 0110 1001 1011 0100‬
        0 0000 0000 0000 0000 0000 0000 1000*/
    }
}
//1234578974 18838

//100 1001 1001 0110 0010 1110 0001 1110
//000 0000 0000 0000 0100 1001 1001 0110‬

//100 1001 1001 0110 0110 0111 1000 1000