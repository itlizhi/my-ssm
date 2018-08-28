package com.enreach.ssm.utils.concurrent;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 构建线程池
 */
public class ThreadPoolBuilder {
    /**
     * 默认拒绝策略
     */

    private static RejectedExecutionHandler defaultRejectHandler = new ThreadPoolExecutor.AbortPolicy();

    /**
     *(1) 默认的ThreadPoolExecutor.AbortPolicy   处理程序遭到拒绝将抛出运行时RejectedExecutionException;
     *(2) ThreadPoolExecutor.CallerRunsPolicy 线程调用运行该任务的 execute 本身。此策略提供简单的反馈控制机制，能够减缓新任务的提交速度
     *(3) ThreadPoolExecutor.DiscardPolicy  不能执行的任务将被删除;
     *(4) ThreadPoolExecutor.DiscardOldestPolicy  如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）。
     */

    /**
     * 可用Queue缓存任务的ThreadPool,建议pool都使用这种方式
     *
     * @see QueuableCachedThreadPoolBuilder
     */
    public static QueuableCachedThreadPoolBuilder queuableCachedPool() {
        return new QueuableCachedThreadPoolBuilder();
    }


    /**
     * 关闭线程池
     * 先使用shutdown, 停止接收新任务并尝试完成所有已存在任务.
     * <p>
     * 如果1/2超时时间后, 则调用shutdownNow,取消在workQueue中Pending的任务,并中断所有阻塞函数.
     * <p>
     * 如果1/2超时仍然超時，則強制退出.
     * <p>
     * 另对在shutdown时线程本身被调用中断做了处理.
     * <p>
     * 返回线程最后是否被中断.
     * <p>
     * 使用了Guava的工具类
     *
     * @see MoreExecutors#shutdownAndAwaitTermination(ExecutorService, long, TimeUnit)
     */
    public static boolean gracefulShutdown(ExecutorService threadPool, int shutdownTimeoutSec) {
        return threadPool == null
                || MoreExecutors.shutdownAndAwaitTermination(threadPool, shutdownTimeoutSec, TimeUnit.SECONDS);
    }

    /**
     * @see gracefulShutdown
     */
    public static boolean gracefulShutdown(ExecutorService threadPool, int shutdownTimeout,
                                           TimeUnit timeUnit) {
        return threadPool == null || MoreExecutors.shutdownAndAwaitTermination(threadPool, shutdownTimeout, timeUnit);
    }


    /**
     * 从Tomcat移植过来的可扩展可用Queue缓存任务的ThreadPool
     *
     * @see QueuableCachedThreadPool
     */
    public static class QueuableCachedThreadPoolBuilder {

        private int minSize = 0;
        private int maxSize = Integer.MAX_VALUE;
        private int keepAliveSecs = 10;
        private int queueSize = 100;

        private ThreadFactory threadFactory;
        private String threadNamePrefix;
        private Boolean daemon;

        private RejectedExecutionHandler rejectHandler;

        public QueuableCachedThreadPoolBuilder setMinSize(int minSize) {
            this.minSize = minSize;
            return this;
        }

        public QueuableCachedThreadPoolBuilder setMaxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        /**
         * LinkedQueue长度, 默认100
         */
        public QueuableCachedThreadPoolBuilder setQueueSize(int queueSize) {
            this.queueSize = queueSize;
            return this;
        }

        public QueuableCachedThreadPoolBuilder setKeepAliveSecs(int keepAliveSecs) {
            this.keepAliveSecs = keepAliveSecs;
            return this;
        }

        /**
         * 与threadNamePrefix互斥, 优先使用ThreadFactory
         */
        public QueuableCachedThreadPoolBuilder setThreadFactory(ThreadFactory threadFactory) {
            this.threadFactory = threadFactory;
            return this;
        }

        /**
         * 与threadFactory互斥, 优先使用ThreadFactory
         */
        public QueuableCachedThreadPoolBuilder setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
            return this;
        }

        /**
         * 与threadFactory互斥, 优先使用ThreadFactory
         * <p>
         * 默认为NULL，不进行设置，使用JDK的默认值.
         */
        public QueuableCachedThreadPoolBuilder setDaemon(Boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        public QueuableCachedThreadPoolBuilder setRejectHanlder(RejectedExecutionHandler rejectHandler) {
            this.rejectHandler = rejectHandler;
            return this;
        }

        public QueuableCachedThreadPool build() {

            threadFactory = createThreadFactory(threadFactory, threadNamePrefix, daemon);

            if (rejectHandler == null) {
                rejectHandler = defaultRejectHandler;
            }

            return new QueuableCachedThreadPool(minSize, maxSize, keepAliveSecs, TimeUnit.SECONDS,
                    new QueuableCachedThreadPool.ControllableQueue(queueSize), threadFactory, rejectHandler);
        }
    }

    /**
     * 优先使用threadFactory，否则如果threadNamePrefix不为空则使用自建ThreadFactory，否则使用defaultThreadFactory
     */
    private static ThreadFactory createThreadFactory(ThreadFactory threadFactory, String threadNamePrefix,
                                                     Boolean daemon) {
        if (threadFactory != null) {
            return threadFactory;
        }

        if (threadNamePrefix != null) {
            if (daemon != null) {
                //可设定是否daemon, daemon线程在主线程已执行完毕时, 不会阻塞应用不退出, 而非daemon线程则会阻塞.
                return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").setDaemon(daemon).build();

            } else {
                //创建ThreadFactory，使得创建的线程有自己的名字而不是默认的"pool-x-concurrent-y"
                return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
            }
        }

        return Executors.defaultThreadFactory();
    }

}
