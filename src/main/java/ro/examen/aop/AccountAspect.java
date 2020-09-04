package ro.examen.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AccountAspect {

    private static final int LIMIT = 100;
    //records of persons who withdraw over limit
    public static Map<Person, Integer> OVER_LIMIT = new HashMap<>();

    @Around("execution(* ro.examen.aop.Account.withdraw(int, ro.examen.aop.Person))")
    public Object checkAmount(ProceedingJoinPoint joinPoint) throws Throwable {
        //check if the amount (first parameter) is over the limit
        if ((int) joinPoint.getArgs()[0] > LIMIT) {
            //check if the person (second parameter) is already registered
            if (OVER_LIMIT.containsKey(joinPoint.getArgs()[1])) {
                int lastRecord = OVER_LIMIT.get(joinPoint.getArgs()[1]);
                OVER_LIMIT.put((Person) joinPoint.getArgs()[1], lastRecord + 1);
            } else {
                OVER_LIMIT.put((Person) joinPoint.getArgs()[1], 1);
            }
        }

        return joinPoint.proceed();
    }
}
