package com.test;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class ThrowingAspect implements Ordered {

    public int getOrder() {
        return 200;
    }

    @AfterThrowing(pointcut = "execution(public * *(..))", throwing = "ex")
    public void intercept(DataAccessException ex) throws Exception {
        //throw DatabaseException
        System.out.println("DAE - " + ex);
        throw new RuntimeException("DAE2"); // based on my testing, this stops second aspect to apply
    }

}