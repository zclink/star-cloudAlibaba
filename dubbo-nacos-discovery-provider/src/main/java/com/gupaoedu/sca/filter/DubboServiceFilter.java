package com.gupaoedu.sca.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.HashMap;

@Activate("dubboServiceFilter")
public class DubboServiceFilter implements Filter {
    /**
     * Make sure call invoker.invoke() in your implementation.
     * <p>
     *     利用dubbo spi扩展 记录调用情况，
     *     包括客户端的ip、host，服务端的方法，耗时等信息
     * </p>
     * @param invoker
     * @param invocation
     */
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        HashMap<String, Object> logContent = new HashMap<>();
        logContent.put("methodName", invocation.getMethodName());
        logContent.put("args", invocation.getArguments());
        logContent.put("client", "{" + RpcContext.getContext().getRemoteAddressString()
                + " , " + RpcContext.getContext().getRemoteHostName() + "}");
        logContent.put("host", RpcContext.getContext().getLocalAddressString());
        Result result = null;
        try {
            long start = System.currentTimeMillis();
            result = invoker.invoke(invocation);
            long end = System.currentTimeMillis();
            logContent.put("cost", end - start);
            if (result.getException() != null) {
                logContent.put("exception", result.getException());
//                log.error("remote throw an exception | request url = {} | param = {}",
//                        invoker.getUrl().getAbsolutePath(), new JSONObject(logContent));
            } else {
                logContent.put("result",result.getValue().toString());
//                log.info("remote request url = {} | param = {}", invoker.getUrl().getAbsolutePath(),
//                        new JSONObject(logContent));
            }
        } catch (Exception ex) {
//            log.error("remote throw an exception | request url = {} | param = {}.", invoker.getUrl().getAddress(),
//                    ex.getCause());
            throw ex;
        }
        return result;

    }
}
