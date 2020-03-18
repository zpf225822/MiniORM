package cn.cherry.orm.core;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装存储映射信息
 */
@Data
public class Mapper {


    //类名
    private String className;

    //表名
    private String tableName;

    //主键信息
    private Map<String, String> idMaps = new HashMap<>(10);

    //属性表
    private Map<String, String> propMaps = new HashMap<>(10);
}
