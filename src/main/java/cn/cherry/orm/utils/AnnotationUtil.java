package cn.cherry.orm.utils;


import cn.cherry.orm.annotation.ORMColumn;
import cn.cherry.orm.annotation.ORMId;
import cn.cherry.orm.annotation.ORMTable;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
    使用反射解析实体类中注解的工具类
 */
@Slf4j
public class AnnotationUtil {


    //得到的类名
    public static String getClassName(Class clz) {
        return clz.getName();
    }

    /*
    得到ORMTable注解中的表名
     */
    public static String getTableName(Class clz) {
        if (clz.isAnnotationPresent(ORMTable.class)) {
            ORMTable ormTable = (ORMTable) clz.getAnnotation(ORMTable.class);
            return ormTable.name();
        } else {
            System.err.println("缺少ORMTable注解");
            return null;
        }
    }

    /*
    得到主键属性和对应的字段
     */
    public static Map<String, String> getIdMapper(Class clz) {
        boolean flag = true;
        Map<String, String> map = new HashMap<>(10);
        Field[] fields = clz.getDeclaredFields();
        //所有属性
        for (Field field : fields) {
            if (field.isAnnotationPresent(ORMId.class)) {
                flag = false;
                String fieldName = field.getName();
                if (field.isAnnotationPresent(ORMColumn.class)) {
                    ORMColumn ormColumn = field.getAnnotation(ORMColumn.class);
                    String columnName = ormColumn.name();
                    map.put(fieldName, columnName);
                    break;
                } else {
                    System.err.println("缺少ORMColumn注解");
                }
            }
        }
        if (flag) {
            System.err.println("缺少ORMId注解");
        }
        return map;
    }

    /*
    得到类中所有属性和对应的字段
     */
    public static Map<String, String> getPropMapping(Class clz) {
        Map<String, String> map = new HashMap<>();
        map.putAll(getIdMapper(clz));
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ORMColumn.class)) {
                ORMColumn ormColumn = field.getAnnotation(ORMColumn.class);
                String fieldName = field.getName();
                String columnName = ormColumn.name();
                map.put(fieldName, columnName);
            }
        }
        return map;
    }

    /*
    获得某包下面的所有类名
     */
    public static Set<String> getClassNameByPackage(String packagePath) {
        Set<String> names = new HashSet<>();
        String packageFile = packagePath.replace(".", "/");
        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        if (classpath == null) {
            classpath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        }
        try {
            //设置编码格式
            classpath = java.net.URLDecoder.decode(classpath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File dir = new File(classpath + packageFile);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File f : files) {
                String name = f.getName();
                //确定是配置文件
                if (f.isFile() && name.endsWith(".class")) {
                    name = packagePath + "." + name.substring(0, name.lastIndexOf("."));
                    names.add(name);
                }
            }
        } else {
            System.err.println("包路径不存在");
        }
        return names;
    }
}
