package org.example.java.feature;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TaskExecutor {

    private static final Executor THREAD_EXECUTOR = new ForkJoinPool(60);

    public List<Result> execute(List<Execute> executeList, boolean parallel) {
        if (parallel) {
            System.out.println(Instant.now() + " execute list parallel start");
            final List<CompletableFuture<Result>> futures = executeList.stream()
                    .map(execute -> CompletableFuture.supplyAsync(() -> this.execute(execute), THREAD_EXECUTOR)
                            .handle((result, throwable) -> {
                                        if (throwable != null) {
                                            System.out.println(throwable);
                                            System.out.println(Arrays.toString(throwable.getCause().getStackTrace()));
                                            return new Result(-1L);
                                        }
                                        return result;
                                    }
                            )
                    )
                    .collect(Collectors.toList());

            CompletableFuture
                    .allOf(futures.toArray(new CompletableFuture[futures.size()]))
                    .orTimeout(futures.size() * 5L, TimeUnit.MINUTES)
                    .handle((unused, throwable) -> {
                        if (throwable != null) {
                            System.out.println(throwable);
                            throw new RuntimeException(throwable);
                        } else {
                            return unused;
                        }
                    })
                    .join();


            final List<Result> resultList = futures
                    .stream()
                    .map(resultCompletableFuture -> resultCompletableFuture.getNow(new Result(-1L)))
                    .collect(Collectors.toList());
            System.out.println(Instant.now() + " execute list parallel end");
            return resultList;
        } else {
            System.out.println(Instant.now() + " execute list start");
            final List<Result> resultList = executeList
                    .stream()
                    .map(this::execute)
                    .collect(Collectors.toList());
            System.out.println(Instant.now() + " execute list end");
            return resultList;
        }
    }

    public Result execute(Execute execute) {
        try {
            System.out.println(Instant.now() + " execute start " + execute);
            Thread.sleep(execute.getTimeout());
            if (!execute.isSuccessful()) {
                System.out.println(Instant.now() + " execute exceptionally " + execute);
                throw new RuntimeException("Something went wrong");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Instant.now() + " execute end " + execute);
        return new Result(execute.getId());
    }
}
