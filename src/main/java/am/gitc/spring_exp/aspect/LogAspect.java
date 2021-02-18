package am.gitc.spring_exp.aspect;

import am.gitc.spring_exp.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(* am.gitc.spring_exp.services.Impl.*.*(..))")
    public void callServicesPublic() {
    }

    @After("callServicesPublic()")
    public void afterCallMethods(JoinPoint joinPoint) {
        String args = Arrays.stream(joinPoint.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        log.info("after : {-" + joinPoint.getTarget().toString() + "-" + joinPoint.getSignature().getName()  + "-} args=[" + args + "]");
    }

    @AfterReturning(pointcut = "callServicesPublic()",returning="retVal")
    public void afterReturningMethods(JoinPoint joinPoint,Object  retVal) {
        if(retVal != null) {
            log.info("returning : -" + retVal);
        }
        else {
            log.info("returning : return try void");
        }
    }

    @Around("callServicesPublic()")
    public Object methodLifeTime(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        log.info("Around Execution of method {-" + pjp.getTarget() + "-"  + pjp.getSignature().getName() + "-} took " + (end - start) + " ms");
        return result;
    }


}
