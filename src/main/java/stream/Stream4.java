package stream;

import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * Created by daizhao.
 * User: tony
 * Date: 2018-5-8
 * Time: 10:46
 * info: Stream：由for循环逐渐演变使用lambda表达式替换
 */
public class Stream4 {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 1) {
                System.out.println(i);
            }
        }

        IntStream.range(0, 10)
                .forEach(i -> {
                    if (i % 2 == 1) System.out.println(i);
                });

        IntStream.range(0, 10)
                .filter(i -> i % 2 == 1)
                .forEach(System.out::println);

        OptionalInt reduced1 =
                IntStream.range(0, 10)
                        .reduce((a, b) -> a + b);
        System.out.println(reduced1.getAsInt());

        int reduced2 =
                IntStream.range(0, 10)
                        .reduce(7, (a, b) -> a + b);
        System.out.println(reduced2);
    }
}
