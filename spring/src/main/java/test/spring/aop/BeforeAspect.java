package com.test;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class BeforeAspect implements Ordered {

    public int getOrder() {
        return 50;
    }

    @Before(value="execution(public * *(..)) && args(str)", argNames="str")
    public void intercept(String str) {
        System.out.println("Before - Param: " + str);
    }

}