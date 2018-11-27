package yunnex.pep.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import yunnex.pep.common.constant.Constant;

public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip);
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip);
    }

    public static void main(String[] args) {
        // 父包
        String bizParentPkg = "yunnex.pep.biz";
        // eclipse生成路径
        String projectPath = System.getProperty("user.dir");
        // IDEA生成路径
        // String projectPath = System.getProperty("user.dir") + "/pep-generator";

        // 代码生成
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("政经平台");
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setBaseColumnList(true);
        mpg.setGlobalConfig(gc);

        // 数据源配
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://10.10.50.107:3306/pep?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("yunnex6j7");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名："));
        // 存放目录
        pc.setParent(bizParentPkg);
        pc.setServiceImpl("service");
        mpg.setPackageInfo(pc);


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        // 自定义模板
        TemplateConfig tc = new TemplateConfig();
        tc.setEntity("/templates/pep-entity.java");
        tc.setMapper("/templates/pep-mapper.java");
        tc.setServiceImpl("/templates/pep-service-impl.java");
        tc.setController("/templates/pep-controller.java");
        tc.setService(null);
        tc.setXml(null);
        mpg.setTemplate(tc);

        // 自定义文件输出路径
        String modulePath = pc.getModuleName().replaceAll("\\.", "/");
        String parentPath = gc.getOutputDir() + "/" + bizParentPkg.replaceAll("\\.", "/") + "/" + modulePath;
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/pep-mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名
                // return projectPath + "/src/main/resources/mybatis/mapper/" + modulePath + "/"
                //         + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                return parentPath + "/xml/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        focList.add(new FileOutConfig("/templates/pep-service.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String serviceName = tableInfo.getServiceName().substring(1);
                return parentPath + "/service/" + serviceName + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/pep-dto.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return parentPath + "/dto/" + tableInfo.getEntityName() + "Dto" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/pep-dto-page-req.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return parentPath + "/dto/" + tableInfo.getEntityName() + "PageReqDto" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/pep-dto-page-resp.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return parentPath + "/dto/" + tableInfo.getEntityName() + "PageRespDto" + StringPool.DOT_JAVA;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名："));
        strategy.setSuperEntityClass("yunnex.pep.common.base.BaseEntity");
        strategy.setSuperServiceClass("yunnex.pep.common.base.BaseService");
        strategy.setSuperServiceImplClass("yunnex.pep.common.base.BaseServiceImpl");
        strategy.setSuperControllerClass("yunnex.pep.common.base.BaseController");
        strategy.setSuperEntityColumns(Constant.Field.ID, Constant.Field.CREATED_BY, Constant.Field.CREATED_DATE,
            Constant.Field.LAST_MODIFIED_BY, Constant.Field.LAST_MODIFIED_DATE, Constant.Field.DEL_FLAG, Constant.Field.REMARKS);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine() {
            @Override
            public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
                objectMap.put("util", new Util());
                String moduleName = pc.getModuleName().replaceAll("\\.", "").toLowerCase();
                String entity = objectMap.get("entity").toString().toLowerCase();
                if (entity.startsWith(moduleName)) {
                    objectMap.put("moduleNameLength", moduleName.length());
                }
                super.writer(objectMap, templatePath, outputFile);
            }
        });
        mpg.execute();
    }

    public static class Util {
        // 取大写字母
        public static String capWord(String name) {
            String result = "";
            char[] chars = name.toCharArray();
            for (char c : chars) {
                if (Character.isUpperCase(c))
                    result += String.valueOf(c).toLowerCase();
            }
            return result;
        }

        // 骆峰转下划线
        public static String camelToUnderLine(String name) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);
                if (Character.isUpperCase(c) && i > 0) {
                    sb.append("_");
                }
                sb.append(Character.toLowerCase(c));
            }
            return sb.toString();
        }

    }
}
