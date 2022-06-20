package hello.core.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService state1 = ac.getBean(StatefulService.class);
        StatefulService state2 = ac.getBean(StatefulService.class);

        //ThreadA: A 사용자가 10000원 주문
        state1.order("userA", 10000);
        //ThreadB: B 사용자가 20000원 주문
        state2.order("userB", 20000);

        //ThreadA: A 사용자가 주문 금액 조회
        int price = state1.getPrice();
        System.out.println("price = " + price);

        org.assertj.core.api.Assertions.assertThat(state1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig{

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }

}