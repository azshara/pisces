package com.stormragetech.pisces.flink.engine.app;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;
import java.util.Map;

public class Utils {

    public static AssignerWithPeriodicWatermarks<String> assignerWithPeriodicWatermarks() {
        return new AssignerWithPeriodicWatermarks<String>() {
            private static final long serialVersionUID = -8514720127897865702L;
            private long max = 2000;
            private long currentTime;

            @Nullable
            @Override
            public Watermark getCurrentWatermark() {
                return new Watermark(currentTime - max);
            }

            @Override
            public long extractTimestamp(String s, long l) {
                long timestamp = l;
                currentTime = Math.max(timestamp, currentTime);
                return currentTime;
            }
        };
    }

    public static String culcultate(Map<String, Object> map, String express) throws Exception {

        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();

        map.forEach((k, v) -> context.put(k, v));

//        context.put("a", 1);
//        context.put("b", 2);
//        context.put("c", 3);
//        String express = "a+b*c";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);

        return String.valueOf(r);
    }

}
