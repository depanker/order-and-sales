package com.depanker.orderandsales.config;

import com.depanker.orderandsales.bean.ResponseWrapper;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;

//@Aspect
//@Component
public class ResponseWrappingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void anyControllerPointcut() {}

    @Pointcut("execution(* *(..))")
    public void anyMethodPointcut() {}

    @AfterReturning(
            value = "anyControllerPointcut() && anyMethodPointcut()",
            returning = "response")
    public Object wrapResponse(Object response) {

        // Do whatever logic needs to be done to wrap it correctly.
        return new ResponseWrapper(response);
    }

}
