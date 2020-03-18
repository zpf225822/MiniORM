package cn.cherry.orm.core;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Mapper {


    private String className;


    private String tableName;


    //主键信息
    private Map<String ,String> idMaps= new HashMap<>(10);

    //属性表
    private Map<String ,String> propMaps= new HashMap<>(10);
}
