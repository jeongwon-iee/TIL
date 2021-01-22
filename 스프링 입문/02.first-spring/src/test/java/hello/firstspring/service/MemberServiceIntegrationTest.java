package hello.firstspring.service;

import hello.firstspring.domain.Member;
import hello.firstspring.repository.MemberRepository;
import hello.firstspring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("jeongwon");

        // when
        Long saveId = memberService.join(member);

        // then
        Member foundMember = memberService.findOne(saveId).get();
        assertThat(saveId).isEqualTo(foundMember.getId());
    }

    @Test
    void duplicateMemberException() {
        // given
        Member member = new Member();
        member.setName("jeongwon");
        
        Member member1 = new Member();
        member1.setName("jeongwon");
        
        // when
        memberService.join(member);
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> memberService.join(member1));

        assertThat(illegalStateException.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}