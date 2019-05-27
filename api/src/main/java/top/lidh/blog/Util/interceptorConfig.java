package top.lidh.blog.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class interceptorConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        List<String> includePath=new ArrayList<>();
        includePath.add("/blog/add");
        includePath.add("/blog/delete");
        includePath.add("/blog/upload");
        includePath.add("/comment/delete");
        includePath.add("/tag/update");
        includePath.add("/tag/delete");
        includePath.add("/visit/getdata");
        registry.addInterceptor(new interceptor()).addPathPatterns(includePath);
        super.addInterceptors(registry);
    }
}
