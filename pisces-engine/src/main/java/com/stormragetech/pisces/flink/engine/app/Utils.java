package com.stormragetech.pisces.flink.engine.app;

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

public class Utils {

    public static AssignerWithPeriodicWatermarks<String> assignerWithPeriodicWatermarks(){
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

}
