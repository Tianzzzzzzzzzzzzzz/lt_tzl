package t.lt.job.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import t.lt.cms.dept.DeptApi;


/**
 * feign 接口调用
 * @author
 * @date
 */
@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {
        DeptApi.class
})
public class RpcConfiguration {
}
