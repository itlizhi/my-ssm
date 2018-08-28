package com.enreach.ssm.utils.concurrent;

import com.google.common.util.concurrent.FutureCallback;
import org.apache.commons.lang3.Validate;

import java.util.concurrent.*;

/**
 * 自定义 future，可以针对 completed 、 failed 、completed 下的处理
 *
 * @param <T>
 */

public abstract class BasicFuture<T> implements Future<T> {

    private volatile boolean completed;
    private volatile boolean cancelled;
    private volatile T result;
    private volatile Exception ex;

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public boolean isDone() {
        return this.completed;
    }

    @Override
    public synchronized T get() throws InterruptedException, ExecutionException {
        while (!this.completed) {
            wait();
        }
        return getResult();
    }

    @Override
    public synchronized T get(final long timeout, final TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        Validate.notNull(unit, "Time unit");
        final long msecs = unit.toMillis(timeout);
        final long startTime = (msecs <= 0) ? 0 : System.currentTimeMillis();
        long waitTime = msecs;
        if (this.completed) {
            return getResult();
        } else if (waitTime <= 0) {
            throw new TimeoutException();
        } else {
            for (; ; ) {
                wait(waitTime);
                if (this.completed) {
                    return getResult();
                } else {
                    waitTime = msecs - (System.currentTimeMillis() - startTime);
                    if (waitTime <= 0) {
                        throw new TimeoutException();
                    }
                }
            }
        }
    }

    private T getResult() {
        //if (this.ex != null) {
        //    throw new ExecutionException(this.ex);
        //}
        //
        //if (cancelled) {
        //    throw new CancellationException();
        //}
        return this.result;
    }

    public boolean completed(final T result) {
        synchronized (this) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.result = result;
            notifyAll();
        }
        onCompleted(result);

        return true;
    }

    /**
     * 异常处理，调用onFailed
     * 注意：如果没有实现onFailed方法，则不会处理
     *
     * @param exception
     * @return
     */
    public boolean failed(final Exception exception) {
        synchronized (this) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.ex = exception;
            notifyAll();
        }

        onFailed(exception);

        return true;
    }

    /**
     * 取消执行，调用onCancelled
     * 注意：如果没有实现onCancelled方法，则不会处理
     *
     * @return
     */
    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        synchronized (this) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.cancelled = true;
            notifyAll();
        }

        onCancelled();

        return true;
    }

    protected abstract void onCompleted(T result);

    protected abstract void onFailed(Exception ex);

    protected abstract void onCancelled();

}
