package shop.mtcoding.blog._core.error;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component // Ioc에 추가
@Aspect // 이 어노테이션이 붙어야 proxy로 동작함    aop ?
public class GlobalValidationHandler {
    @Before("@annotation(shop.mtcoding.blog._core.error.anno.MyBefore)")
    // 인터셉터는 invoke 밖에 있어서 req, resp 에만 접근 가능하지만. 이 어노테이션은 Component이기 때문에 리플렉션이 작동함
    public void beforeAdvice(JoinPoint jp) { // 리플렉션으로 분석한 내용이 jp안에 다 들어감
        String name = jp.getSignature().getName();
        System.out.println("Before Advice : " + name);
    }

    @After("@annotation(shop.mtcoding.blog._core.error.anno.MyAfter)")
    public void afterAdvice(JoinPoint jp) {
        String name = jp.getSignature().getName();
        System.out.println("After Advice : " + name);
    }

    @Around("@annotation(shop.mtcoding.blog._core.error.anno.MyAround)")
    public Object aroundAdvice(ProceedingJoinPoint jp) {
        String name = jp.getSignature().getName();
        System.out.println("Around Advice 직전 : " + name);

        try {
            Object result = jp.proceed(); // 컨트롤러 함수가 호출됨(@Controller)
            System.out.println("Around Advice 직후 : " + name);
            System.out.println("result : " + result);
            return result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
