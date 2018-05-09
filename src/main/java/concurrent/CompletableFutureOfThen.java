package concurrent;

import java.util.Comparator;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * thenApply拿到上面的输出结果，在做其他操作，例子如下：
 * @author tuonioooo
 */
public class CompletableFutureOfThen {

    public static void init(){
        //方式一
        CompletableFuture<String> completableFuture1 = new CompletableFuture();
        //方式二
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.completedFuture(111);
        CompletableFuture<String>  completableFuture3 = CompletableFuture.completedFuture("123");
        //方式三
        CompletableFuture<String>  completableFuture4 = CompletableFuture.supplyAsync(()->"123");
        CompletableFuture<Integer>  completableFuture5 = CompletableFuture.supplyAsync(()->123);
        CompletableFuture  completableFuture6 = CompletableFuture.supplyAsync(()->123);
        CompletableFuture  completableFuture7 = CompletableFuture.supplyAsync(()->"123");

        //方式四  创建自定义的executor
        ExecutorService executor = Executors.newFixedThreadPool(1);
        CompletableFuture.supplyAsync(()->"123", executor);
        try {
            executor.shutdownNow();
            executor.awaitTermination(5, TimeUnit.SECONDS);//在指定时间内，终止任务
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void thenAccept(){
        CompletableFuture.supplyAsync(()->"hello")
                .thenAccept(s -> System.out.println(s + " world"))
                .thenAccept(v-> System.out.println("done"));

        //hello world
        //done
    }

    public static void thenAcceptAsync(){//默认使用ForkJoinPool.commonPool()
        CompletableFuture.supplyAsync(()->"hello").thenAcceptAsync(s -> System.out.println(s + " world "));
        System.out.println("异步任务执行");

        //异步任务执行
        //hello world
    }

    public static void thenAcceptAsyncOfExecutor(){//创建自定义的executor
        ExecutorService executor = Executors.newFixedThreadPool(1);
        CompletableFuture.supplyAsync(()->"hello").thenAcceptAsync(s -> System.out.println(s + " world "), executor);
        try {
            executor.shutdownNow();
            executor.awaitTermination(5, TimeUnit.SECONDS);//在指定时间内，终止任务
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("异步任务执行");
    }

    public static void thenApply(){
        String result = CompletableFuture.supplyAsync(()->"hello")
                .thenApply(s -> s + " world")
                .thenApply(v-> v + " done").join();
        System.out.println("result = " + result);
        //hello world done
    }

    public static void thenApplyAsync(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        String result = CompletableFuture.supplyAsync(()->"hello")
                .thenApplyAsync(s -> s + " world")
                .thenApplyAsync(v-> v + ", 异步线程名：" + Thread.currentThread().getName()).join();
        System.out.println("result = " + result);
        System.out.println("主线程名：" + Thread.currentThread().getName());
        //result = hello world, 异步线程名：ForkJoinPool.commonPool-worker-1
        //主线程名：main
    }

    public static void thenApplyOfExecutor(){
        String result = CompletableFuture.supplyAsync(()->"hello")
                .thenApplyAsync(s -> s + " world")
                .thenApplyAsync(v-> v + ", 异步线程名：" + Thread.currentThread().getName()).join();
        System.out.println("result = " + result);
        System.out.println("主线程名：" + Thread.currentThread().getName());
        //result = hello world, 异步线程名：ForkJoinPool.commonPool-worker-1
        //主线程名：main
    }

    public static void thenRun(){
        IntStream stream = IntStream.of(10,9,8,7,6,5,4,3,2,1);
        Runnable runnable = ()->{
            stream.forEach(i->{
                try {
                    Thread.sleep(1000);
                    System.out.format("线程名称：%s, 倒计时：%d\n", Thread.currentThread().getName(), i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        };

        CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(3000);
                System.out.format("线程名称：%s\n" , Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRun(runnable);
        while (true){}

    }

    public static void thenRunAsync(){
        IntStream stream = IntStream.of(10,9,8,7,6,5,4,3,2,1);
        CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(3000);
                System.out.format("线程名称：%s\n" , Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRunAsync(()->{
            stream.forEach(i->{
                try {
                    Thread.sleep(1000);
                    System.out.format("线程名称：%s, 倒计时：%d\n", Thread.currentThread().getName(), i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        while (true){}

    }

    public static void thenRunAsyncOfExecutor(){
        ExecutorService executor = Executors.newCachedThreadPool();
        IntStream stream = IntStream.of(10,9,8,7,6,5,4,3,2,1);
        CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(3000);
                System.out.format("线程名称：%s\n" , Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRunAsync(()->{
            stream.forEach(i->{
                try {
                    Thread.sleep(1000);
                    System.out.format("线程名称：%s, 倒计时：%d\n", Thread.currentThread().getName(), i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            executor.shutdown();

        }, executor);

        while(true){
            if(executor.isTerminated()){
                System.out.println("线程任务都已经完成");
                break;
            }
        }

    }

    public static void thenCombine() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("线程名称：" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程名称：" + Thread.currentThread().getName());
            return "world";
        }), (s1, s2) -> s1 + " " + s2).join();
        System.out.println(result);
    }


    public static void thenCombineAsync() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程名称：" + Thread.currentThread().getName());
            return "hello";
        }).thenCombineAsync(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程名称：" + Thread.currentThread().getName());
            return "world";
        }), (s1, s2) -> s1 + " " + s2, executor).join();
        executor.shutdown();
        System.out.println(result);
    }


    public static void thenAcceptBoth() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenAcceptBothAsync(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "world";
        }), (s1, s2) ->{
            System.out.println(s1 + " " + s2);
            executor.shutdown();
        }, executor);

        while(true){
            if(executor.isTerminated()){
                System.out.println("线程任务都已经完成");
                break;
            }
        }
    }


    public static void runAfterBoth(){
        ExecutorService executor = Executors.newFixedThreadPool(1);
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> {
            System.out.println("hello world");
            executor.shutdown();
        }, executor);

        while(true){
            if(executor.isTerminated()){
                System.out.println("线程任务都已经完成");
                break;
            }
        }
    }

    public static void acceptEither() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        }), System.out::println);
        while (true){}
    }


    public static void runAfterEither() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println("hello world"));
        while (true) {
        }
    }

    public static void exceptionally() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }

    public static void whenComplete() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).whenComplete((s, t) -> {
            System.out.println("s：" + s);
            System.out.println(t.getMessage());
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }

    public static void handle() {
        //出现异常时
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //出现异常
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).handle((s, t) -> {
            if (t != null) {
                return "hello world";
            }
            return s;
        }).join();
        System.out.println(result);
    }

    public static void handle1() {
        //未出现异常时
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).handle((s, t) -> {
            if (t != null) {
                return "hello world";
            }
            return s;
        }).join();
        System.out.println(result);
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("=============thenAccept==============");
        thenAccept();
        System.out.println("=============thenAcceptAsync==============");
        thenAcceptAsync();
        System.out.println("=============thenAcceptAsyncOfExecutor==============");
        thenAcceptAsyncOfExecutor();

        System.out.println("=============thenApply==============");
        thenApply();
        System.out.println("=============thenApplyAsync==============");
        thenApplyAsync();
        System.out.println("=============thenApplyOfExecutor==============");
        thenApplyOfExecutor();


        System.out.println("=============thenRun==============");
        //thenRun();
        System.out.println("=============thenRunAsync==============");
        //thenRunAsync();
        System.out.println("=============thenRunAsyncOfExecutor==============");
        //thenRunAsyncOfExecutor();

        System.out.println("=============thenCombine==============");
        //thenCombine();
        System.out.println("=============thenCombineAsync==============");
        //thenCombineAsync();

        System.out.println("=============thenAcceptBoth==============");
        //thenAcceptBoth();

        System.out.println("=============runAfterBoth==============");
        //runAfterBoth();


        System.out.println("=============acceptEither==============");
        //acceptEither();

        System.out.println("=============runAfterEither==============");
        //runAfterEither();

        System.out.println("=============exceptionally==============");
        //exceptionally();

        System.out.println("=============whenComplete==============");
        //whenComplete();


        System.out.println("=============handle==============");
        handle();

        System.out.println("=============handle1==============");
        handle1();

    }

}
