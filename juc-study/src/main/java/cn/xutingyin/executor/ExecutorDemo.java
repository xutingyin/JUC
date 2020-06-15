package cn.xutingyin.executor;

import java.time.LocalDateTime;
import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ExecutorDemo {
    public static void main(String[] args) throws Exception {
        // 这三种都是使用Executors 工厂类的静态方法创建ExecutorService 执行器
        /*
         * 使用的LinkedBlockingQueue[无界队列]
         * */
        ExecutorService fixedService = Executors.newFixedThreadPool(2);
        /*使用的LinkedBlockingQueue[无界队列]
         * */
        ExecutorService singleService = Executors.newSingleThreadExecutor();
        /*使用的SynchronousQueue[没有容量],但是CachedThreadPool 的maximumPool  = Integer.MAX_SIZE = 0x7fffffff
            根据需要创建新线程的线程池
         * */
        ExecutorService cacheService = Executors.newCachedThreadPool();
        cacheService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
            }
        });
        /**
         * 自定义线程名，方便出问题时快速定位
         */
        ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("test-pool-%d").build();
        /**
         * 阿里巴巴规范手册建议自定义线程池执行器
         */
        ExecutorService shelfService = new ThreadPoolExecutor(4, 40, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(1024), nameThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        shelfService.execute(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                do {
                    System.out.println(LocalDateTime.now());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                } while (i < 10);
            }
        });
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                int sum = 0;
                for (int i = 0; i < 101; i++) {
                    sum += i;
                }
                return sum;
            }
        };
        Future callableSum = shelfService.submit(callable);
        Object sum = callableSum.get();
        System.out.println("sum :" + sum);
        shelfService.shutdown();
    }
}
