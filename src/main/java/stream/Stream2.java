package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by daizhao.
 * User: tony
 * Date: 2018-5-7
 * Time: 18:25
 * info: stream：
 *           anyMatch()：判断任意一个元素是否匹配(谓词)，如果任意一个元素匹配返回true,否则false
 *           allMatch()：判断所有元素是否匹配(谓词)，如果全匹配返回true,否则false
 *          noneMatch()：判断任意元素不匹配(谓词)，如果任意元素不匹配返回true,否则false
 *              count()：统计结果
 *             reduce()：
 *                      1)、聚合操作，方法的处理方式一般是每次都产生新的数据集
 *                      2)、T reduce(T identity, BinaryOperator accumulator)
 *                      它允许用户提供一个循环计算的初始值。
 *                      accumulator：计算的累加器，其方法签名为apply(T t,U u)，
 *                      在该accumulator方法中第一个参数t为上次函数计算的返回值，第二个参数u为Stream中的元素，这个函数把这两个值计算apply，得到的和会被赋值给下次执行这个方法的第一个参数
 *
 *
 *                      reduce还有其它两个重载方法：
                        Optional reduce(BinaryOperatoraccumulator):与上面定义基本一样，无计算初始值，所以他返回的是一个Optional。
                        U reduce(U identity, BiFunction accumulator, BinaryOperator combiner):与前面两个参数的reduce方法几乎一致，你只要注意到BinaryOperator其实实现了BiFunction和BinaryOperator两个接口。
                        3)、示例：reduce1()、reduce2()
 */
public class Stream2 {


    public static void main(String[] args) {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        stringCollection.add("zdd1");

        // matching

        boolean anyStartsWithA = stringCollection
                .stream()
                .anyMatch((s) -> s.startsWith("a"));

        System.out.println(anyStartsWithA);      // true

        boolean allStartsWithA = stringCollection
                .stream()
                .allMatch((s) -> s.startsWith("a"));

        System.out.println(allStartsWithA);      // false

        boolean noneStartsWithZ = stringCollection
                .stream()
                .noneMatch((s) -> s.startsWith("z"));
        System.out.println("noneStartsWithZ = " + noneStartsWithZ);// false


        // counting

        long startsWithB = stringCollection
                .stream()
                .filter((s) -> s.startsWith("b"))
                .count();

        System.out.println(startsWithB);    // 3


        // reducing

        Optional<String> reduced =
                stringCollection
                        .stream()
                        .sorted()
                        .reduce((s1, s2) -> s1 + "#" + s2);

        reduced.ifPresent(System.out::println);
        // "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"

        System.out.println("===============reduce1================");

        reduce1();

        System.out.println("===============reduce2================");

        reduce2();
    }

    public static void reduce1(){
        int value = Stream.of(1, 2, 3, 4).reduce(100, (sum, item) -> sum + item);
        System.out.println("reduce1 ——>  value= " + value);

        int value2 = Stream.of(1, 2, 3, 4).reduce(100, Integer::sum);
        System.out.println("reduce1 ——>  value2= " + value2);
    }

    public static void reduce2(){
        Optional<String> reduce2 =
                Stream.of("1","2","3")
                        .sorted()
                        .reduce((s1, s2) -> s1 + "#" + s2);//无计算初始值（初始值赋值时不需要计算）

        System.out.println("reduce2 = " + reduce2);//Optional[1#2#3]
        System.out.println("reduce2 = " + reduce2.get());//1#2#3
    }


}
