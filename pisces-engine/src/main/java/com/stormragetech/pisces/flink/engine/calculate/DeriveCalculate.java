package com.stormragetech.pisces.flink.engine.calculate;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.stormragetech.pisces.flink.engine.common.ReturnInfo;

import java.io.Serializable;
import java.util.Map;

public class DeriveCalculate implements Calculate, Serializable {

    private static final long serialVersionUID = 7861513230981159223L;

    @Override
    public void calculate(Map<String, Object> map, ReturnInfo out, String expression) {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();

        map.forEach(context::put);

        try {
            Object r = runner.execute(expression, context, null, true, false);
            out.setCode("0000");
            out.setMessage("计算成功");
            out.setData(String.valueOf(r));
        } catch (Exception e) {
            e.printStackTrace();
            out.setCode("9999");
            out.setMessage("计算失败");
        }
    }

}
