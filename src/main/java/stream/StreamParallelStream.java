package stream;

import java.util.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;


/**
 * Created by daizhao.
 * User: tony
 * Date: 2018-5-8
 * Time: 14:18
 * info:
 */
public class StreamParallelStream {

    private static List<Integer> list1 = new ArrayList<>();
    private static List<Integer> list2 = new ArrayList<>();
    //private static List<Integer> list2 = Collections.synchronizedList(new ArrayList<>());
    private static List<Integer> list3 = new ArrayList<>();
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        IntStream.range(0, 10000).forEach(list1::add);

        IntStream.range(0, 10000).parallel().forEach(list2::add);//线程不安全

        IntStream.range(0, 10000).forEach(i -> {
            lock.lock();
            try {
                list3.add(i);
            }finally {
                lock.unlock();
            }
        });

        System.out.println("串行执行的大小：" + list1.size());
        System.out.println("并行执行的大小：" + list2.size());
        System.out.println("加锁并行执行的大小：" + list3.size());

        //同时也提出了解决方案：
        //1.修改list2属性为  private volatile static List<Integer> list2 = new ArrayList<>();多线程修改可见
        //2.使用同步集合private static List<Integer> list2 = Collections.synchronizedList(new ArrayList<>());
        IntStream.range(0, 10000).parallel().reduce((x, y)-> x + y).ifPresent(System.out::println);//线程不安全

    }




}
