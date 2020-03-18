package cn.cherry.orm.core;


import cn.cherry.orm.utils.Dom4jUtil;
import lombok.Data;
import org.dom4j.Document;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
            //解析配置文件
            Document document  = Dom4jUtil.getXMLByFilePath(cfgFile.getPath());
            propConfigMaps = Dom4jUtil.Elements2Map(document, "property", "name");
            mappingSet = Dom4jUtil.Elements2Set(document, "mapping", "resource");
            entitySet = Dom4jUtil.Elements2Set(document, "entity", "package ");

        }else {
            cfgFile=null;
            System.err.println("未找到核心配置文件miniORM.cfg.xml");

        }
    }


    //获取数据库连接

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        String url = propConfigMaps.get("connection.url");
        String username = propConfigMaps.get("connection.username");
        String password = propConfigMaps.get("connection.password");
        String driverClass = propConfigMaps.get("connection.driverClass");
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url,username,password);
        //设置自动提交事务
        connection.setAutoCommit(true);
        return connection;
    }
    //获取键值对对应关系
    private void dealMappingOrAnnotation(){
        mapperList = new ArrayList<>(15);

        for (String xmlPath : mappingSet) {
            Document document = Dom4jUtil.getXMLByFilePath(classPath + xmlPath);
            String name = Dom4jUtil.getPropValue(document, "class", "name");
            String table = Dom4jUtil.getPropValue(document, "class", "table");
            Map<String, String> id2Map = Dom4jUtil.ElementsID2Map(document);
            Map<String, String> propMaps = Dom4jUtil.Elements2Map(document);

            Mapper buildMapper = Mapper.builder().className(name)
                    .tableName(table)
                    .idMaps(id2Map)
                    .propMaps(propMaps).build();
            mapperList.add(buildMapper);

        }

    }
}
