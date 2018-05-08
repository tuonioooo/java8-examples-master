package stream;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by daizhao.
 * User: tony
 * Date: 2018-5-7
 * Time: 17:43
 * info: stream：
 *          findFirst()：取流的第一个元素
 *          ifPresent()：如果存在值，则使用该值做事情，否则什么也不做。
 *           mapToInt()：将元素转换成整型
 *           mapToObj()：转换Obj对象
 *            average()：取平均值
 *             filter()：返回此流的匹配元素组成的流
 *             sorted()：排序
 */
public class Stream1 {

    public static void main(String[] args) {

        Arrays.asList("a1", "a2", "a3")//初始化list
                .stream()
                .findFirst()//查找第一个元素
                .ifPresent(System.out::println);//如果存在就输出

        System.out.println("========================分隔线=========================");

        Stream.of("a1", "a2", "a3") //等同于Arrays.stream，定义数组
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)//将元素转换成整型
                .max()//取最大值
                .ifPresent(System.out::println);//如果存在就输出

        System.out.println("========================分隔线=========================");

        IntStream.range(1, 4)//生成1-4的序列
                .mapToObj(i -> "a" + i)//转换Obj对象
                .forEach(System.out::println);

        System.out.println("========================分隔线=========================");

        Arrays.stream(new int[] {1, 2, 3})
                .map(n -> 2 * n + 1)
                .average()//取平均值
                .ifPresent(System.out::println);

        System.out.println("========================分隔线=========================");

        Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)//转换Int
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);

        System.out.println("========================分隔线=========================");

        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .stream()
                .filter(s -> s.startsWith("c"))//取以C开头的值
                .map(String::toUpperCase)
                .sorted()//排序
                .forEach(System.out::println);

    }
}
