package com.sliu.util;

import org.springframework.stereotype.Component;


import java.util.concurrent.*;

@Component
public class GlobalSingleThreadPool {
    private GlobalSingleThreadPool(){}
    private static ThreadPoolExecutor pool;

    public static ThreadPoolExecutor getInstance(){
        if(pool == null){
            pool = new ThreadPoolExecutor(200, 400, 0,
                    TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100),
                    new ThreadPoolExecutor.AbortPolicy());
        }
        return pool;
    }
}
