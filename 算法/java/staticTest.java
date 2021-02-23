import java.util.ArrayList;
import java.util.List;

public class staticTest {
    private static List<staticTest> list = new ArrayList<>();

    private final int id;

    //不管new多少个对象，list始终只有一个，并且在jvm加载时完成
    public staticTest(int id) {
        this.id = id;
        list.add(this);
    }

    public static List getList() {
        return list;
    }

    public static void main(String[] args) {
        for (int i = 0 ; i < 5 ; i++) {
            new staticTest(i);
        }

        List<staticTest> list = staticTest.getList();
        System.out.println(list.size());
        String test = "*.";
        System.out.println(test.toCharArray()[0] == '.');
    }
}
