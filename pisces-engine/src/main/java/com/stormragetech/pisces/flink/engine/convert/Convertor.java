package com.stormragetech.pisces.flink.engine.convert;

import java.util.Map;

public interface Convertor {

    String marshal(Map<String, Object> map);

    Map<String, Object> unmarshal(String s);

}
