package concurrent;

/**
 * Created by daizhao.
 * User: tony
 * Date: 2018-5-7
 * Time: 10:59
 * info:
 */
public class TestLoop {

    public static void main(String[] args) {
        int length = 101;
        int splitNum = 20;
        int loop = length/splitNum + (length%splitNum > 0 ? 1 : 0);

        System.out.println("loop = " + loop);

    }
}
