package com.whk.close;

import com.whk.threadpool.ThreadPoolManager;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class CloseManager {

    private final List<Runnable> close = new LinkedList<>();

    @PreDestroy
    public void run() {
        close.forEach(Runnable::run);
    }

    public void add(Runnable runnable){
        close.add(runnable);
    }
}
