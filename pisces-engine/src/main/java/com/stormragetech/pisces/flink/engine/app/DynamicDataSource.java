package com.stormragetech.pisces.flink.engine.app;

import com.alibaba.fastjson.JSON;
import com.stormragetech.pisces.flink.engine.common.EventBase;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;

public class DynamicDataSource implements SourceFunction<String> {

    private Integer count = 1;

    private boolean isRunning = true;

    /**
     * 主要的方法
     * 启动一个source
     * 大部分情况下，都需要在这个run方法中实现一个循环，这样就可以循环产生数据了
     *
     * @param context
     * @throws Exception
     */
    @Override
    public void run(SourceContext<String> context) throws Exception {
        while (isRunning) {
            EventBase event = init();
            event.setEventId(String.valueOf(100000 + count));

            context.collect(JSON.toJSONString(event));
            count++;
            //每秒产生一条数据
            Thread.sleep(500);
        }
    }

    /**
     * 取消一个cancel的时候会调用的方法
     */
    @Override
    public void cancel() {
        isRunning = false;
    }

    private EventBase init() {
        EventBase event = new EventBase();
        event.setRptTime("20190101");
        Random random = new Random();
        event.setEventTypeId(String.valueOf(random.nextInt(10)));
        return event;
    }

}
