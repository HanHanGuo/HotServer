package com.xianguo.hotserver.servlet.entity;

import com.xianguo.hotserver.servlet.impl.HotServletDynamic;
import lombok.Data;

import javax.servlet.Servlet;
import javax.servlet.ServletRegistration;

@Data
public class DynamicServlet {

    private ServletRegistration.Dynamic dynamic;

    private Servlet servlet;

    public DynamicServlet(){
        this.dynamic = new HotServletDynamic();
    }

}
