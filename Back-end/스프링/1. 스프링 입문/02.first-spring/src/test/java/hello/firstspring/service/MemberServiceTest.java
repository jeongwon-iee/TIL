package hello.firstspring.service;

import hello.firstspring.domain.Member;
import hello.firstspring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    void afterEach() {
        memoryMemberRepository.clearAll();
    }

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