package stream;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by daizhao.
 * User: tony
 * Date: 2018-5-8
 * Time: 13:52
 * info:
 *       Stream：
 *              map专项练习
 *
 */
public class StreamMap {

    public static void main(String[] args) {

        System.out.println("===========================类型转换=============================");

        mapToLong();
        mapToDouble();
        mapToInt();
        map();

        System.out.println("===========================求和=============================");

        reduce1();
        reduce2();
        intStreamToSum();

        System.out.println("===========================listToMap=============================");

    }



    private static void mapToLong() {
        Stream.of(new BigDecimal("1.2"), new BigDecimal("3.7"))
                .mapToLong(BigDecimal::longValue)
                .average()
                .ifPresent(System.out::println);
    }

    private static void mapToDouble() {
        Stream.of(new BigDecimal("1.2"), new BigDecimal("3.7"))
                .mapToDouble(BigDecimal::doubleValue)//转换double
                .average()
                .ifPresent(System.out::println);
    }

    private static void mapToInt() {
        Stream.of(new BigDecimal("1.2"), new BigDecimal("3.7"))
                .mapToInt(BigDecimal::intValue)
                .average()
                .ifPresent(System.out::println);
    }

    private static void map(){
        Stream.of("a1","b1","c1","d1")
                .map(s->s.toUpperCase())
                .forEach(s -> System.out.println(s));
    }

    //reduce求和方式1
    private static void reduce1(){
        List<Integer> strs = Arrays.asList(1,2,3,4,5,6);
        //Stream.of(1,2,3,4,5,6).reduce(( sum, e ) -> sum + e).ifPresent(System.out::println);
        Optional<Integer> sum = strs.stream().reduce((x, y) -> x + y);
        System.out.println("reduce 求和方式1:"+sum.get());
    }
    //reduce求和方式2
    private static void reduce2(){
        Integer sum2 = Stream.of(1,2,3,4,5,6).reduce(0, ( sum, e ) -> sum + e);
        System.out.println("sum2 = " + sum2);
    }
    //IntStream.sum()
    private static void intStreamToSum(){
        Integer intStreamToSum = Stream.of(1,2,3,4,5,6).mapToInt( x-> x ).sum();
        System.out.println("intStreamToSum = " + intStreamToSum);
    }

}
