package com.gupaoedu.sca.sentinelspi;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class RuleInitFunc implements InitFunc {
    /**
     * https://github.com/alibaba/Sentinel/wiki/%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        //限流
        /*
            Field	        说明	                                                        默认值
            resource	    资源名，资源名是限流规则的作用对象
            count	        限流阈值
            grade	        限流阈值类型，QPS 模式（1）或并发线程数模式（0）	                QPS 模式
            limitApp	    流控针对的调用来源	                                            default，代表不区分调用来源
            strategy	    调用关系限流策略：直接、链路、关联	                            根据资源本身（直接）
            controlBehavior	流控效果（直接拒绝 / 排队等待 / 慢启动模式），不支持按调用关系限流	    直接拒绝
            clusterMode	    是否集群限流	                                                否
         */
        List<FlowRule> ruleList = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setCount(1);
        rule.setResource("helloFlow");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        ruleList.add(rule);
        FlowRuleManager.loadRules(ruleList);
        System.out.println("load FlowRule \n");

        //熔断
        /*
            Field	            说明	                                                                    默认值
            resource	        资源名，即限流规则的作用对象
            count	            阈值
            grade	            熔断策略，支持秒级 RT/秒级异常比例/分钟级异常数	                                秒级平均 RT
            timeWindow	        降级的时间，单位为 s
            rtSlowRequestAmount	RT 模式下 1 秒内连续多少个请求的平均 RT 超出阈值方可触发熔断（1.7.0 引入）       	5
            minRequestAmount	异常熔断的触发最小请求数，请求数小于该值时即使异常比率超出阈值也不会熔断（1.7.0 引入）	5
         */
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource("helloDegrade");
        degradeRule.setCount(10);
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        degradeRule.setTimeWindow(10);
        degradeRule.setMinRequestAmount(1);
        degradeRule.setRtSlowRequestAmount(1);
        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);
        System.out.println("load DegradeRule\n");
    }
}
