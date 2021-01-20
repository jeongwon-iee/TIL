package hello.firstspring.repository;

import hello.firstspring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        // testcase 생성
        Member member = new Member();
        member.setName("jeongwon-iee");

        // 수행
        repository.save(member);

        // 검증
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
        // 기대: 저장했던 member가 find 했을 때 반환되어야 함.
    }

    @Test
    public void findByName() {
        // testcase 생성
        Member member = new Member();
        member.setName("google");
        repository.save(member);

        Member member1 = new Member();
        member1.setName("clova");
        repository.save(member1);

        // 수행
        Member result = repository.findByName("google").get();

        // 검증
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findAll() {
        Member member = new Member();
        member.setName("google");
        repository.save(member);

        Member member1 = new Member();
        member1.setName("clova");
        repository.save(member1);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
