package yunnex.pep.config.web;

import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        // 跳转到Controller处理，以返回json
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");

        registry.addErrorPages(errorPage404);
        registry.addErrorPages(errorPage500);
    }

}
