package hello.firstspring.repository;

import hello.firstspring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    /**
     * 동시성 문제가 고려되어 있지 않음,
     * 실무에서는 HashMap 대신 ConcurrentHashMap,
     * long 대신 AtomicLong 사용 고려
     */

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearAll() {
        store.clear();
    }
}
