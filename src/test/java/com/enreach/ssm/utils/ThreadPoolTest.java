package com.enreach.ssm.utils;

import com.enreach.ssm.utils.thread.QueuableCachedThreadPool;
import com.enreach.ssm.utils.thread.ThreadPoolBuilder;
import com.enreach.ssm.utils.thread.ThreadUtil;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    @Test
    public void run() {

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

}
