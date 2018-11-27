package yunnex.pep.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Value("${pep.swagger.enabled:false}")
    private Boolean enabled;
    @Value("${pep.swagger.base-package}")
    private String basePackage;

    @Bean
    @ConditionalOnMissingBean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Admin API")
                .apiInfo(apiInfo())
                // 是否放API文档，生产环境关
                .enable(enabled)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                // 默认请求/根路径开始，如果项目不是部署在根路径下，使用以下方法修改根路
//                .pathMapping("/platform")
                ;
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("政经平台", "https://github.com/abc", "12345678@163.com");
        return new ApiInfoBuilder()
                .title("spring boot swagger!")
                .description("swagger")
                .license("Apache License Version 2.0")
                .contact(contact)
                .version("1.0.0")
                .build();
    }

}
