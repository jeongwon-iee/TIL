package hello.core.order;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder() {
        Long memberId = 1L;
        Member member = new Member(memberId, "jeongwon", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "Spring", 9000);
        Assertions.assertThat(order.getMemberId()).isEqualTo(memberId);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}