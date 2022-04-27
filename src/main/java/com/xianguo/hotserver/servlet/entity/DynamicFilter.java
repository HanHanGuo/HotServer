package com.xianguo.hotserver.servlet.entity;

import com.xianguo.hotserver.servlet.impl.HotFilterDynamic;
import lombok.Data;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Data
public class DynamicFilter {

    private FilterRegistration.Dynamic dynamic;

    private Filter filter;

    public DynamicFilter(Filter filter){
        this.dynamic = new HotFilterDynamic();
        this.filter = filter;
    }

}
