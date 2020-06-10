package com.gupaoedu.sca.config;

import com.alibaba.cloud.dubbo.metadata.repository.DubboServiceMetadataRepository;
import com.alibaba.cloud.dubbo.registry.event.ServiceInstancesChangedEvent;
import com.alibaba.cloud.dubbo.service.DubboGenericServiceFactory;
import com.alibaba.cloud.dubbo.service.DubboMetadataServiceProxy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.registry.client.metadata.ServiceInstanceMetadataUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 当使用nacos注册中心在k8s环境下或者端口使用随机端口时，可能会遇见provider重启后，consumer始终不能连接新的provider，一直尝试连接旧的provider地址端口，
 * 导致不得不重启consumer才能解决这个问题。这个问题的原因是在这种情况下，元数据没有被修改，invoker内的信息还是旧的，需要通过这个类去把他们删除掉，
 * 从而从新到注册中心获取数据，但是日志里还会报去连旧的ip端口的日志，这不用管。
 * 这个类只是个临时解决方案，等alibaba后续更新修复吧，当前有问题的版本为sca2.2.1
 */
@Order(Integer.MIN_VALUE + 100)
@Configuration
public class MyDubboRegistryInvokerRebuildListener implements ApplicationListener<ServiceInstancesChangedEvent>
{
    private ObjectProvider<DubboServiceMetadataRepository> metadataRepositoryObjectProvider;
    private ObjectProvider<DubboMetadataServiceProxy> serviceProxyObjectProvider;
    private ObjectProvider<DubboGenericServiceFactory> genericServiceFactoryObjectProvider;

    @Override
    public void onApplicationEvent(ServiceInstancesChangedEvent event) {
        String serviceName = event.getServiceName();
        if(serviceName == null){
            return;
        }
        if(metadataRepositoryObjectProvider != null) {
            metadataRepositoryObjectProvider.ifAvailable(o -> {
                List<ServiceInstance> instanceList = new ArrayList<>(event.getServiceInstances());
                if (instanceList != null && instanceList.size() > 0) {
                    ServiceInstance instance = instanceList.get(0);
                    Map<String, String> metadata = instance.getMetadata();

                    //获取 dubbo.metadata-service.urls
                    String metadataServiceUrls = metadata.get(ServiceInstanceMetadataUtils.METADATA_SERVICE_URLS_PROPERTY_NAME);
                    String metadataServiceUrl = "";
                    JSONArray jsonArray = JSON.parseArray(metadataServiceUrls);
                    if (jsonArray != null && jsonArray.size() > 0) {
                        metadataServiceUrl = jsonArray.getString(0);
                        //转换成dubbo的URL 为了拿某些参数去找这个service的URLs
                        URL url = URL.valueOf(metadataServiceUrl);
                        String path = url.getPath();
                        String protocol = url.getProtocol();
                        String group = url.getParameter(CommonConstants.GROUP_KEY);
                        String version = url.getParameter(CommonConstants.VERSION_KEY);

                        List<URL> urlList = o.findSubscribedDubboMetadataServiceURLs(path, group, version, protocol);
                        if (urlList != null && urlList.size() > 0) {
                            //调一次就够，是把整个key干掉
                            o.removeMetadataAndInitializedService(serviceName, urlList.get(0));
                        }
                    }
                }

            });
        }
        if(genericServiceFactoryObjectProvider != null) {
            genericServiceFactoryObjectProvider.ifAvailable(o -> o.destroy(serviceName));
        }
        if(serviceProxyObjectProvider != null) {
            serviceProxyObjectProvider.ifAvailable(o -> o.removeProxy(serviceName));
        }
    }

    @Autowired(required = false)
    public void setMetadataRepositoryObjectProvider(ObjectProvider<DubboServiceMetadataRepository> metadataRepositoryObjectProvider) {
        this.metadataRepositoryObjectProvider = metadataRepositoryObjectProvider;
    }

    @Autowired(required = false)
    public void setServiceProxyObjectProvider(ObjectProvider<DubboMetadataServiceProxy> serviceProxyObjectProvider) {
        this.serviceProxyObjectProvider = serviceProxyObjectProvider;
    }

    @Autowired(required = false)
    public void setGenericServiceFactoryObjectProvider(ObjectProvider<DubboGenericServiceFactory> genericServiceFactoryObjectProvider) {
        this.genericServiceFactoryObjectProvider = genericServiceFactoryObjectProvider;
    }

    @Bean
    public MyDubboRegistryInvokerRebuildListener dubboRegistryInvokerRebuildListener(){
        return new MyDubboRegistryInvokerRebuildListener();
    }

}
