package com.stormragetech.pisces.flink.engine.calculate;

import com.stormragetech.pisces.flink.engine.common.ReturnInfo;

import java.util.Map;

public interface Calculate {

    void calculate(Map<String, Object> in, ReturnInfo out, String expression);

}
