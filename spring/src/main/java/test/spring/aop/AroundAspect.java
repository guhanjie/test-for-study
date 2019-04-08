package com.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AroundAspect {
    
    @Around(value="execution(public * *(..)) && args(str)", argNames="str")
    public People around(ProceedingJoinPoint pjp, String str) throws Throwable {
        String method = pjp.getSignature().getName();
        People ss = new People("123");
        System.out.println(String.format("Method: [%s], Param: [%s]", method, str));
        try {
            MyService service = (MyService)pjp.getTarget();
            //service.throw2();
            ss = (People)pjp.proceed();
            System.out.println(ss.getName());
        } catch(Exception e) {
            ss.setName("XXX");
            e.printStackTrace();
            ss = new People("Exception happened, oops~");
        }
        System.out.println("obj in Aspect:" + ss + " hascode:" + ss.hashCode());
        return ss;
    }
}
