package com.xianguo.hotserver.servlet.entity;

import com.xianguo.hotserver.servlet.impl.HotFilterDynamic;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FilterMap extends HashMap<String, DynamicFilter> {

    public void putFilter(String name, DynamicFilter dynamicFilter){
        put(name, dynamicFilter);
    }

    public List<Filter> getFilter(String mapping){
        return this.entrySet().stream().map(e->e.getValue().getFilter()).collect(Collectors.toList());
    }

}
