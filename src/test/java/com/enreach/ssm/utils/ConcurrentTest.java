package com.enreach.ssm.utils;

import com.enreach.ssm.utils.concurrent.BasicFuture;
import com.enreach.ssm.utils.concurrent.QueuableCachedThreadPool;
import com.enreach.ssm.utils.concurrent.ThreadPoolBuilder;
import com.enreach.ssm.utils.concurrent.ThreadUtil;

import org.junit.Test;

//Assert
import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.*;

public class ConcurrentTest {

    @Test
    public void pool() {

        QueuableCachedThreadPool pool = ThreadPoolBuilder.queuableCachedPool()
                .setMaxSize(6).setQueueSize(5).setDaemon(true).setKeepAliveSecs(5).setThreadNamePrefix("lizhi")
                .setRejectHanlder(new ThreadPoolExecutor.AbortPolicy()).build();

        pool.submit(() -> {

            ThreadUtil.sleep(2, TimeUnit.SECONDS);
            System.out.println("->" + Thread.currentThread().getName());

        });

        System.out.println(pool.getActiveCount());
        System.out.println(pool.getQueue().size());


        try {
            for (int i = 0; i < 12; i++) {
                pool.submit(new LongTask());
            }
        } catch (Exception e) {
            e.printStackTrace();
            assertThat(e).isInstanceOf(RejectedExecutionException.class);

        } finally {
            ThreadPoolBuilder.gracefulShutdown(pool, 2);
        }

        System.out.println(pool.getActiveCount());
        System.out.println(pool.getQueue().size());

        ThreadUtil.sleep(3000);


    }

    public class LongTask implements Runnable {
        @Override
        public void run() {
            ThreadUtil.sleep(1000);
            System.out.println("task:" + Thread.currentThread().getName());
        }
    }

    @Test
    public void baseFuture() throws ExecutionException, InterruptedException, TimeoutException {

        QueuableCachedThreadPool pool = ThreadPoolBuilder.queuableCachedPool()
                .setMaxSize(6).setQueueSize(5).setDaemon(true).setKeepAliveSecs(5).setThreadNamePrefix("lizhi").build();


        BasicFuture<Integer> future = new BasicFuture<Integer>() {
            @Override
            protected void onCompleted(Integer result) {
                System.out.println("onCompleted:" + result);
            }

            @Override
            protected void onFailed(Exception ex) {

            }

            @Override
            protected void onCancelled() {

            }
        };

        pool.submit(() -> {
            System.out.println("th1");
            ThreadUtil.sleep(2000);
            future.completed(1000);
        });


        BasicFuture<Integer> futures = new BasicFuture<Integer>() {
            @Override
            protected void onCompleted(Integer result) {

            }

            @Override
            protected void onFailed(Exception ex) {
                //这里异常后，
                System.out.println("onFailed:" + ex.getMessage());
            }

            @Override
            protected void onCancelled() {
                System.out.println("cancel");
            }
        };

        pool.submit(() -> {
//            futures.cancel(true); return;

            System.out.println("th2");
            ThreadUtil.sleep(1000);

            futures.failed(new RuntimeException("error"));
        });

        Integer result = future.get();
        Integer integer = futures.get();
        System.out.println(integer);

    }


    @Test
    public void taskFuture() {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        FutureTask<Integer> futureTask = new FutureTask<>(new Task());
        executorService.submit(futureTask);
        executorService.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        System.out.println("main");

        try {
            System.out.println("task:" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("done");


    }

    class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("---task----");
            Thread.sleep(3000);

            return 1000;
        }
    }

    @Test
    public void thread() {

        //start 多线程
        //run 在现有线程执行

        new Thread(() -> {
            System.out.println("th1");
            ThreadUtil.sleep(1000);
        }).start();

        new Thread(() -> {
            System.out.println("th2");
            ThreadUtil.sleep(1000);
        }).start();

        System.out.println("main");
        ThreadUtil.sleep(100);


        new Thread(() -> {
            System.out.println("th1");
            ThreadUtil.sleep(1000);
        }).run();

        new Thread(() -> {
            System.out.println("th2");
            ThreadUtil.sleep(1000);
        }).run();

        System.out.println("run main");
        ThreadUtil.sleep(100);
    }

}
