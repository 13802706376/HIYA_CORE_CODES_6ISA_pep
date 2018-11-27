package yunnex.pep.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;

@EnableCaching
@SpringBootApplication
@MapperScan("yunnex.pep.biz.**.mapper")
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class BizApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BizApplication.class).web(false).run(args);
    }

}
