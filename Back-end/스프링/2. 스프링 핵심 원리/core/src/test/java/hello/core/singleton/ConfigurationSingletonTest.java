package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = applicationContext.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = applicationContext.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = applicationContext.getBean("memberRepository", MemberRepository.class);

        // 모두 같은 인스턴스를 참고하고 있다.
        assertThat(memberService.getMemberRepository()).isSameAs(orderService.getMemberRepository());
        assertThat(memberRepository).isSameAs(orderService.getMemberRepository());
    }

    @Test
    void configurationDeep() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // AppConfig도 스프링 빈으로 등록된다.
        AppConfig appConfig = applicationContext.getBean("appConfig", AppConfig.class);

        System.out.println("appConfig.getClass() = " + appConfig.getClass());
        // 출력: appConfig.getClass() = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$1b53d1be
    }
}
