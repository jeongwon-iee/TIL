package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = applicationContext.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = applicationContext.getBean("statefulService", StatefulService.class);

        // Thread A: userA가 10000원을 주문
        statefulService1.order("userA", 10000);
        // Thread B: userB가 20000원을 주문
        statefulService1.order("userB", 20000);

        // Thread A: userA가 주문 금액을 조회
        int priceA = statefulService1.getPrice();
        // ThreadA: 사용자A는 10000원을 기대했지만, 기대와 다르게 20000원 출력
        System.out.println("priceA = " + priceA);
        // Thread B: userB가 주문 금액을 조회
        int priceB = statefulService2.getPrice();
        System.out.println("priceB = " + priceB);

        assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}