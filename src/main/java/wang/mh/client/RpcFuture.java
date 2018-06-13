package wang.mh.client;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
@SuppressWarnings("unchecked")
public class RpcFuture<T> implements Future<T> {

    private CountDownLatch latch;

    private Object response;

    private Throwable error;

    private boolean isDone;

    public RpcFuture() {
        this.latch = new CountDownLatch(1);
    }

    public void success(Object response) {
        this.response = response;
        latch.countDown();
        isDone = true;
    }


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        latch.await();
        if (error != null) {
            log.error("error msg : {}", error.getMessage());
            return null;
        }
        return (T) response;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
