package yunnex.pep.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@EnableAsync
@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"yunnex.pep.common", "yunnex.pep.admin"},
        excludeName = "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration")
public class AdminApplication  extends SpringBootServletInitializer 
{
   @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
    {
        return builder.sources(AdminApplication.class);
    }

    public static void main(String[] args) 
    {
        SpringApplication.run(AdminApplication.class, args);
    }

}
