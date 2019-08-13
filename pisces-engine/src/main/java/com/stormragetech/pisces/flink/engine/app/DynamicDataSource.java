package com.stormragetech.pisces.flink.engine.app;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class DynamicDataSource implements SourceFunction<Integer> {

    private Integer count = 1;

    private boolean isRunning = true;

    /**
     * 主要的方法
     * 启动一个source
     * 大部分情况下，都需要在这个run方法中实现一个循环，这样就可以循环产生数据了
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void run(SourceContext<Integer> ctx) throws Exception {
        while (isRunning) {
            ctx.collect(count);
            count++;
            if (count > 8) {
                count = 1;
            }
            //每秒产生一条数据
            Thread.sleep(1000);
        }
    }

    /**
     * 取消一个cancel的时候会调用的方法
     */
    @Override
    public void cancel() {
        isRunning = false;
    }

}
