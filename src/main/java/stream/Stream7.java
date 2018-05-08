package stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by daizhao.
 * User: tony
 * Date: 2018-5-8
 * Time: 11:15
 * info: Stream：
 *             mapToObj()：流转换Object对象，用于需要对一个流中的值进行某种形式的转换
 *                 peek()：生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数（Consumer实例），新Stream每个元素被消费的时候都会执行给定的消费函数
 *         map和floatMap()：和map类似，不同的是map将其每个元素转换得到的是Stream对象，会把子Stream中的元素压缩到父集合中
 *                         flatMap会得到一个单一的元素
 */
public class Stream7 {

    static class Foo {
        String name;
        List<Bar> bars = new ArrayList<>();

        Foo(String name) {
            this.name = name;
        }
    }

    static class Bar {
        String name;

        Bar(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
//        test1();
        test2();
        testFlatMapAndMap1();
    }

    static void test2() {
        IntStream.range(1, 4)
                .mapToObj(num -> new Foo("Foo" + num))
                .peek(f -> IntStream.range(1, 4)
                        .mapToObj(num -> new Bar("Bar" + num + " <- " + f.name))
                        .forEach(f.bars::add))
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));
    }

    /***
     * map和flatmap的区别1
     */
    static void testFlatMapAndMap1(){
        List<String> list = Arrays.asList("hello welcome", "world hello", "hello world",
                "hello world welcome");

        list.stream().map(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("---------- ");

        list.stream().flatMap(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList()).forEach(System.out::println);
    }

    static void test1() {
        List<Foo> foos = new ArrayList<>();

        IntStream
                .range(1, 4)
                .forEach(num -> foos.add(new Foo("Foo" + num)));

        foos.forEach(f ->
                IntStream
                        .range(1, 4)
                        .forEach(num -> f.bars.add(new Bar("Bar" + num + " <- " + f.name))));

        foos.stream()
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));

        foos.stream()
                .map(foo -> foo.name + "0")
                .forEach(b -> System.out.println(b));
    }
}
