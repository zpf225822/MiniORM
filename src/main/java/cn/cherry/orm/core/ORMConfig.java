package cn.cherry.orm.core;


import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * 解析配置文件
 */
public class ORMConfig {

    //路径
    private String classPath;

    //核心配置文件
    private File cfgFile;


    private Map<String,String> propConfig;


    private Set<String> mappingSet;
}
