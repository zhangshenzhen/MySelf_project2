package com.shenhen.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
线程池管理类
* */
public class ThreadPoolManager {
    //单例
    private static ThreadPoolManager mThreadPoolManager = new ThreadPoolManager();
    private final ThreadPoolExecutor mthreadPoolExecutor;

    public static ThreadPoolManager getInstance() {
        return mThreadPoolManager;
    }
    //构造
    private ThreadPoolManager() {
        //创建线程池
        //线程任务处理异常的时候 重新添加到队列中
        mthreadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
                //线程任务处理异常的时候 重新添加到队列中
                adTask(runnable);
            }
        });
        //线程池需要吹核心线程
        mthreadPoolExecutor.execute(coreThreadable);
    }

    //队列
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    //添加任务到队列
    public void adTask(Runnable runnable) {
        if (runnable != null) {
            mQueue.offer(runnable); //offer 添加不会阻塞   put添加可能会阻塞
        }
    }



    //创建线程，去队列中取
    public Runnable coreThreadable = new Runnable() {
        Runnable run;

        @Override
        public void run() {
            while (true) {
                try {
                    //从队列中去除任务
                    run = mQueue.take();
                    //取出的任务交给线池去执行
                    mthreadPoolExecutor.execute(run);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
