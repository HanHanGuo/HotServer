package com.xianguo.hotserver.servlet.impl;

import javax.servlet.FilterRegistration;

public class HotFilterDynamic extends HotFilterRegistration implements FilterRegistration.Dynamic {
    @Override
    public void setAsyncSupported(boolean isAsyncSupported) {

    }
}