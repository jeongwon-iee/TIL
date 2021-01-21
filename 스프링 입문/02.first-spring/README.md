# 🌱 회원 관리 예제

## 비즈니스 요구사항 정리

### 비즈니스 요구사항
 
- 데이터: 회원ID, 이름
- 기능: 회원 등록, 조회
- 아직 데이터 저장소가 선정되지 않음 (가상의 시나리오)

### **일반적인 웹 애플리케이션 계층 구조**

<img width="966" alt="스크린샷 2021-01-20 오후 2 35 04" src="https://user-images.githubusercontent.com/45806836/105131637-b4816680-5b2c-11eb-862d-36398105b78f.png">

- 컨트롤러: 웹 MVC의 컨트롤러 역할
- 서비스: 핵심 비즈니스 로직 구현
- 리포지토리: 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
- 도메인: 비즈니스 도메인 객체, 예) 회원, 주문, 쿠폰 등등 주로 데이터베이스에 저장하고 관리됨

### 클래스 의존관계

<img width="966" alt="스크린샷 2021-01-20 오후 2 36 21" src="https://user-images.githubusercontent.com/45806836/105131744-e1ce1480-5b2c-11eb-8ecd-cb1316fca6e1.png">

- 아직 데이터 저장소가 선정되지 않아서, 우선 인터페이스로 구현 클래스를 변경할 수 있도록 설계
- 데이터 저장소는 RDB, NoSQL 등등 다양한 저장소를 고민중인 상황으로 가정
- 개발을 진행하기 위해서 초기 개발 단계에서는 구현체로 가벼운 메모리 기반의 데이터 저장소 사용

&nbsp;

## 회원 도메인과 리포지토리 만들기

### 회원 객체

```java
public class Member {
      private Long id;
      private String name;
      public Long getId() {
          return id;
}
      public void setId(Long id) {
          this.id = id;
}
      public String getName() {
          return name;
}
      public void setName(String name) {
          this.name = name;
} }
```

### 회원 리포지토리 인터페이스

: 회원 객체를 저장하는 저장소 인터페이스

```java
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(Long id);
    List<Member> findAll();
}
```

Optional: null이 반환될 경우 null을 직접 반환하는 대신 Optional로 감싸 반환

### 회원 리포지토리 메모리 구현체

: 리포지토리 인터페이스의 메모리 구현체

```java
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
}
```

- **save()**
    - (우선 메모리에 저장) Map을 사용, id가 Long 타입이니 `HashMap<Long, Member>`  
    실무에선 공유되는 자원에서 동시성 문제가 있을 수 있으니 `ConcurrentHashMap` 사용
    - store에 저장하기 전에 id값을 증가시켜줌  
    (name은 회원가입 시 user가 등록, id는 시스템이 부여해주는 번호)
- **findById()**
    - null이 반환될 수 있으니 `*Optional.ofNullable()*`로 감싸 반환
- **findByName()**

    ```java
    store.values().stream()
                    .filter(member -> member.getName().equals(name))
                    .findAny();
    ```

    - store 해쉬 맵의 값들을(`.values()`) loop를 돌며(`.stream()`) 검사(`.filter()`), 넘어온 `name`과 값이 같은 경우에만 필터링, 찾으면 반환(`.findAny()`), 없으면 null → Optional로 반환
- **findAll()**
    - HashMap의 value값들만 모아 ArrayList로 반환

&nbsp;

## 회원 리포지토리 테스트 케이스 작성

: 앞서 작성한 리포지토리가 정상 동작하는지 테스트 해야함.

개발한 기능을 실행해서 테스트 할 때 자바의 main 메서드를 통해서 실행하거나, 웹 애플리케이션의 컨트롤러를 통해서 해당 기능을 실행한다. 이러한 방법은 준비하고 실행하는데 오래 걸리고, 반복 실행하기 어렵고 여러 테스트를 한번에 실행하기 어렵다는 단점이 있다. 자바는 JUnit이라는 프레임워크로 테스트를 실행해서 이러한 문제를 해결한다.

### 회원 리포지토리 메모리 구현체 테스트

`src/test/java` 하위 폴더에 동등한 계층 구조로 생성한다.

```java
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
```

**@AfterEach**: 한번에 여러 테스트를 실행하면 메모리 DB에 직전 테스트의 결과가 남을 수 있다. (테스트는 임의의 순서로 실행됨) 이렇게 되면 다음 이전 테스트 때문에 다음 테스트가 실패할 가능성이 있다.

`@AfterEach`를 사용하면 각 테스트가 종료될 때 마다 이 기능이 실행된다. 여기서는 메모리 DB에 저장된 데이터를 삭제한다. 

MemoryMemberRepository에 아래와 같은 clear 코드 생성 → @AfterEach에서 호출

```java
public void clearStore() {
        store.clear();
}
```

테스트는 각각 독립적으로 실행되어야 한다. 테스트 순서에 의존관계가 있는 것은 좋은 테스트가 아니다.

&nbsp;

## 회원 서비스 개발

회원 리파지토리와 도메인을 활용해 실제 비즈니스 로직을 수행

```java
public class MemberService {

    MemberRepository memberRepository = new MemoryMemberRepository();

    public Long join(Member member) {
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
```

- **join()**
    - 회원 가입
    - 같은 이름을 가진 중복된 회원이 없도록 한다.

        `ifPresent()`: Optional 안에 null이 아닌 값이 있다면 동작

    ```java
    Optional<Member> result = memberRepository.findByName(member.getName());
            result.ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    ```

    `Optional`이 안 예쁘니 코드 정리

    ```java
    memberRepository.findByName(member.getName())
                    .ifPresent(m -> {
                        throw new IllegalStateException("이미 존재하는 회원입니다.");
                    });
    ```

    메소드 추출 (cmd + opt + m)

    ```java
    public Long join(Member member) {
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    ```

&nbsp;

## 회원 서비스 테스트

class에서 `cmd` + `shift` + `T` → directory 구조에 맞게 새 test 생성

### test: given - when - then 문법

`// given`, `// when`, `// then` 주석을 달아놓으면 추후 보기 편함

- **given** 뭔가가 주어졌는데
- **when** 이걸 실행했을 때
- **then** 이게 나와야 해

### 정상 플로우 테스트

회원가입 하려는 회원이 제대로 저장되는가 테스트

```java
@Test
    void join() {
        // given
        Member member = new Member();
        member.setName("jeongwon");

        // when
        Long saveId = memberService.join(member);

        // then
        Member foundMember = memberService.findOne(saveId).get();
        Assertions.assertThat(saveId).isEqualTo(foundMember.getId());
    }
```

### 예외 플로우 테스트

중복된 회원이 가입하려고 했을 때 예외를 잘 던지는가 테스트

```java
				// given
        Member member = new Member();
        member.setName("jeongwon");
        
        Member member1 = new Member();
        member1.setName("jeongwon");
        
        // when
        memberService.join(member);
        assertThrows(IllegalStateException.class, () -> memberService.join(member1));
```

코드 복붙 후 동일 변수명 전부 수정 단축키: 변수명 더블클릭하고 `shift` + `f6`

```java
assertThrows(IllegalStateException.class, () -> memberService.join(member1));
```

→ `memberService.join(member1)`을 했을 때 `IllegalStateException`을 던지는지 확인.

```java
memberService.join(member);
IllegalStateException exception = assertThrows(IllegalStateException.class, () -> memberService.join(member1));

assertThat(exception.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
```

반환값이 있을 때 변수로 받는 단축키: `option` + `cmd` + `V`

→ 반환값이 있어 메시지 내용도 확인 가능

### 테스트간 의존관계 삭제

```java
MemoryMemberRepository memberRepository = new MemoryMemberRepository();

@AfterEach
    void afterEach() {
        memberRepository.clearAll();
    }
```

이전에 실행했던 것을 다시 실행하는 단축키: `ctrl` + `R`

### 회원 리포지토리 의존성 주입

**기존의 코드**

```java
MemoryMemberRepository memberRepository = new MemoryMemberRepository();
```

현재는 `store`가 *static*이므로 단 하나 존재하지만, 다른 인스턴스를 생성하는 것은 좋은 방법이 아님.

문제: MemberService와 MemberServiceTest에서 사용하는 repository가 다른 인스턴스  
→ DB가 static이 아니면 바로 문제가 생김, 같은 repository에 대해 test 하는 것이 맞음

**문제 해결**

```java
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

		...
}
```

```java
class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

		...
}
```

각 test가 실행될 때마다, 실행되기 전(@BeforeEach) MemoryMemberRepository를 생성해 주입  
→ 같은 repository에 대해 실행될 것

&nbsp;

# 🌱 스프링 빈과 의존관계
## 컴포넌트 스캔과 자동 의존관계 설정

회원 컨트롤러가 회원 서비스와 회원 리포지토리를 사용할 수 있게 의존관계를 준비하자.

- 한 것

멤버 서비스와 리포지토리, 멤버 객체를 만들었다.   
서비스를 통해 멤버를 가입시킬 수 있고, 리포지토리에 저장이 되고, 꺼내올 수 있고, 테스트까지 한 상황

- 할 것

화면 붙이기 → **controller**와 view template이 필요.

### Controller

MemberController는 MemberService를 통해 회원가입하고, 데이터를 조회할 수 있어야 함.  
→ MemberController와 MemberService는 의존관계가 있다.

**스프링 컨테이너에 빈 등록**

```java
@Controller
public class MemberController {
    
}
```

`**@Controller**` 어노테이션: 스프링 컨테이너가 MemberController 객체를 생성해 스프링에 넣고 관리

스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다. (유일하게 하나만 등록해서 공유한다) 따라서 같은 스프링 빈이면 모두 같은 인스턴스다.   
설정으로 싱글톤이 아니게 설정할 수 있지만, 특별한 경우를 제외하면 대부분 싱글톤을 사용한다.

**MemberService 의존성 주입 (DI)**

생성자에 *@Autowired*가 있으면 객체 생성 시점에 스프링 컨테이너에서 해당 스프링 빈을 찾아 주입한다.  
이렇게 객체 의존관계를 외부에서 넣어주는 것을 ***DI (Dependency Injection), 의존성 주입*** 이라 한다.
이전 테스트에서는 개발자가 직접 주입했고, 여기서는 @Autowired에 의해 스프링이 주입해준다.

```java
@Controller
public class MemberController {

    private final MemberService memberService;

		@Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
```

- `MemberService memberService = new MemberService();`

→ **스프링 컨테이너에 객체를 딱 하나 등록해놓고, 받아서 사용하도록 해야 한다.** 

MemberService를 사용하는 다른 컨트롤러들이 `new`로 여러 개 인스턴스를 생성할 필요 없이 딱 하나 만들어놓고 공용으로 사용하면 된다.

- `*@Autowired` - Dependency Injection*

`cmd` + `N`으로 생성자 생성, *@Autowired* 어노테이션 추가

스프링 컨테이너가 MemberService를 관리하게 하려면 *@Controller*와 마찬가지로 *@Service*를 달아준다.

스프링 컨테이너가 실행될 때 MemberController 인스턴스를 생성하며 생성자를 호출하는데, *@Autowired*가 되어있으면 스프링 컨테이너에 빈으로 등록되어 있는 MemberService를 가져다 연결을 시켜준다.

Repository도 마찬가지로 Service에서 *@Autowired* 시켜주고 구현체에 *@Repository*를 등록해준다.

마찬가지로 스프링 컨테이너가 @Service로 인해 서비스를 스프링 컨테이너에 등록하면서 MemberService 인스턴스를 생성할 때 생성자를 호출, @Autowired가 되어 있으면 @Repository로 컨테이너에 등록되어 있는 MemoryMemberRepository를 주입해준다.

→ 스프링 컨테이너가 Controller, Service, Repository를 관리할 수 있게 된다.

### 스프링 빈을 등록하는 두 가지 방법

- 컴포넌트 스캔과 자동 의존관계 설정
- 자바 코드로 직접 스프링 빈 등록하기

### 컴포넌트 스캔

*@Component* 애노테이션이 있으면 스프링이 자동으로 인식해 스프링 빈으로 자동 등록해준다.  
*@Controller*를 붙인 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문이다.

내부에 *@Component*를 포함하는 @Controller, @Service, @Repository 애노테이션도 스프링 빈으로 자동 등록된다.

**그 외의 부가 기능**

- *@Controller*  
스프링 MVC가 컨트롤러로 인식해 컨트롤러 기능(애플리케이션의 진입점)을 제공한다.
- *@Service*  
부가 기능은 없지만, 개발자들이 비즈니스 로직이 있는 계층이라는 것을 쉽게 이해할 수 있다.
- *@Repository*  
데이터베이스 관련 예외를 스프링이 추상화한 일관된 예외로 변환해 준다.

&nbsp;


## 자바 코드로 직접 스프링 빈 등록하기

회원 서비스와 회원 리포지토리의 @Service, @Repository@Autowired 어노테이션을 제거하고 진행한다.

```java
@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
```

### 의존성 주입의 세 가지 방법

1. 생성자 주입

    ```java
    @Autowired
    		public MemberController(MemberService memberService) {
    		    this.memberService = memberService;
    		}
    ```

    의존관계가 실행중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입을 권장한다.

2. 필드 주입

    ```java
    @Autowired
    private  MemberService memberService;
    ```

3. setter 주입

    ```java
    private MemberService memberService;

    @Autowired
        public void setMemberController(MemberService memberService) {
            this.memberService = memberService;
        }
    ```

실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용한다.  
그리고 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다.

주의) *@Autowired*를 통한 DI는 helloConroller, memberService 등과 같이 스프링이 관리하는 객체에서만 동작한다. 스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않는다.

