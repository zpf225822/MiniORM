package cn.cherry.orm.core;


import lombok.Data;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 解析核心配置文件中的数据
 * 数据库连接信息
 * 实体类包路径
 * 映射文件信息等
 */
@Data
public class ORMConfig {


    //路径
    private  static String classPath;

    //核心配置文件
    private static File cfgFile;


    //标签<property>
    private static Map<String,String> propConfigMaps;


    //映射配置文件<mapping>
    private static Set<String> mappingSet;

    //实体类映射文件
    private static Set<String> entitySet;

    //映射信息
    private static List<Mapper> mapperList;

    static {
        //从当前线程中读取到配置文件路径
        classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        cfgFile = new File(classPath+"miniORM.cfg.xml");

        //判断核心配置文件是否存在
        if (cfgFile.exists()) {

        }else {
            cfgFile=null;
            System.err.println("未找到核心配置文件miniORM.cfg.xml");

        }
    }

}
