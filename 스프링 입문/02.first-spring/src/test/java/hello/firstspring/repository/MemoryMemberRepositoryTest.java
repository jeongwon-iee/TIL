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
        repository.clearAll();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("jeongwon-iee");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        Member member = new Member();
        member.setName("google");
        repository.save(member);

        Member member1 = new Member();
        member1.setName("clova");
        repository.save(member1);

        Member result = repository.findByName("google").get();

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
