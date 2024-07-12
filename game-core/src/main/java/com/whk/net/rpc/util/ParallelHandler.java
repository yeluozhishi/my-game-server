package com.whk.net.rpc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ParallelHandler<T> {

    private final List<CompletableFuture<T>> futures;

    ParallelHandler () {
        this(10);
    }

    ParallelHandler (int size) {
        futures = new ArrayList<>(size);
    }

    public ParallelHandler addResTask (Supplier<T> supplier) {
        futures.add(CompletableFuture.supplyAsync(supplier));
        return this;
    }

    public List<CompletableFuture<T>> OK () {
        waits();
        return futures;
    }

    public void waits () {
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{})).join();
    }

    public void clear () {
        futures.clear();
    }

    public static void main(String[] args) throws InterruptedException {
        ParallelHandler handler = new ParallelHandler();

        handler.addResTask(()->"done")
                .addResTask(()->{
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "finally";
                });

        List<CompletableFuture<String>> futures = handler.OK();

        futures.forEach(item -> System.out.println(item.getNow("no result")));
    }
}
