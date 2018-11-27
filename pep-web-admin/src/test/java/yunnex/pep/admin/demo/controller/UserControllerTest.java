package yunnex.pep.admin.demo.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import yunnex.common.config.ConfigLoader;
import yunnex.pep.admin.BaseTest;

public class UserControllerTest extends BaseTest {

    @Autowired
    private ConfigLoader loader;
    @Value("${dubbo.registry.protocol}")
    private String zk;


    @Test
    public void t1() {
        String property = loader.getProperty("interactive_activity_share_picture_path");
        System.out.println(property);
        System.out.println(zk);
    }

}
