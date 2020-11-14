# 지난 시간 배운 내용

`Pop Quiz` 지지난 시간 배운 내용

Web Service를 하기 위한 http protocol을 사용하는 경우, 이 프로토콜이 동작하는 system의 OS가 한 process당 동시에 열 수 있는 TCP connection 6개만 허용. (3가지 option 중 `non-persistent with parallel`), base-html을 받았더니 text말고 여러 가지로 encoding 된 object들이 10개가 들어 있음. 이 경우 Web Page를 완전히 display 해주는 데 걸리는 RTT는? 

⇒ 2(TCP conn.+base html)+2(TCP conn.+6 Objects)*2 = 6RTTs

SMTP와 HTTP 모두 loss-sensitive하기 때문에 TCP protocol을 사용

connection을 먼저 여는 것이 Client (Server는 항상 listen)

Client가 msg를 보내려고 여는 protocol이 Push, 받으려고 여는 protocol은 Pull

차이1) ***HTTP는 Pull Protocol 이며 SMTP는 Push Protocol***

차이2) ***HTTP는 Obj들이 각각 다른 인코딩, 나눠져서 오며 SMTP는 Obj가 7-bit ASCII로 인코딩된 하나로 옴***

---

## SMTP: final words

**comparison with HTTP:**

- HTTP: pull
- SMTP: push
- both have ASCII command/response interaction, status codes
- HTTP: each object encapsulated in its own response message
- SMTP: multiple objects sent in multipart message

SMTP uses ***persistent connections***

SMTP requires message (header & body) to be in 7-bit ASCII

SMTP server uses CRLF.CRLF to determine end of message

---

# Mail access protocols

<img width="692" alt="스크린샷 2020-11-12 오전 9 29 54" src="https://user-images.githubusercontent.com/45806836/98879773-a2020580-24c9-11eb-8087-04817a9647ff.png">


Alice가 Bob에게 mail을 보내는 상황.

Scenario) Alice는 자신의 mail server에 Bob한테 보내고 싶은 mail을 그냥 밀어넣음(push). 이 sender(Alice)의 mail server가 receiver(Bob)의 mail server의 IP주소를 찾아 다시 밀어넣고(push) Bob의 mail box에 넣어놓음. (두 번 Push)

서버의 역할은 여기까지. 이제 receiver(Bob)의 user agent가 자신의 mail box를 열어 메일을 끌어옴(pull) ⇒ 이때 쓰는 메일 프로토콜이 POP or IMAP

- **SMTP**: delivery/storage to receiver’s server
- **mail access protocol**: retrieval from server (모두 TCP 위에서 동작)
    - **POP**: Post Office Protocol [RFC 1939]: authorization,
    download
    - **IMAP**: Internet Mail Access Protocol [RFC 1730]: more
    features, including manipulation of stored messages on
    server
    - **HTTP**: gmail, Hotmail, Yahoo! Mail, etc. (웹에서 document 가져오듯이 web-based 응용)

## POP3 and IMAP

User agent가 Mail server의 mail box에서 email 을 pull할 때 쓰는 프로토콜

POP3의 두 가지 모드 "download and delete" "download and keep"

IMAP은 more function(ex. 중요도, 폴더 분류), more complex than POP3

---

# DNS : Domain Name System

process와 process가 통신을 하기 위해선, port도 알아야하지만 결국 process가 running 하고 있는 machine의 주소를 알아야 함. 그 machine의 Network Interface Card에 부여된 Identity가 IPv4(32bit, 0~255), host name(domain name) (mylocation.co.kr에서 IP주소로 주소를 찾을 수 있음)

### Domain Name System

IPv4와 domain name을 맵핑해 놓은 mapping table(호스트 이름-IP 주소)

Why not centralize DNS? DNS 서버를 하나로 둔다면? 

1. 시간이 오래 걸림 (traffic volume / distant centralized database)
2. Single Point of Failure (하나의 centralized server die > 서비스 중단)
3. maintenance 

사실상 DNS 서버가 하는 일: host name을 IP address로 바꿔 라우팅

⇒ 3계층에서 해야 하는 일. 근데 왜 application layer에서 ?

- **distributed database**

implemented in **hierarchy** of many name servers 체계별로 계층 구분

- **application-layer protocol**

host안의 routing table은 단순. default gateway router(첫번째 router)로 모두 전송하고, 이 라우터가 시간마다 동적으로 변하는 routing table을 가짐

network 계층은 빨리 deliver만 하면 돼서, 복잡한 연산은 application layer에서 함.

## DNS: services, structure

1. hostname to IP address translation (기본 기능)
2. host aliasing - canonical(공식 이름), alias names(호스트의 별칭)
3. mail server aliasing
4. load distribution

### host aliasing

IP주소 찾기: **host alias name → host canonical name → IP address**

alias name:  많은 사람들이 기억하기 쉬운 host name (www.yahoo.com)

canonical name: alias name으로 알려진 서버의 진짜 host name ([westBD13.yahoo.com](http://westbd13.yahoo.com/))

[westBG13.yahoo.com](http://estbg13.yahoo.com/) 컴퓨터의 IP 주소가 130.14.13.14 이고, 어느 DNS query가 [www.yahoo.com](http://www.yahoo.com/) 의 IP 주소를 물었다면, 먼저 [westBD13.yahoo.com](http://westbd13.yahoo.com/) 을 찾아내고 그 다음 130.14.13.14 을 알려줌.

### mail server aliasing

도메인 이름으로 메일 서버의 호스트 이름 찾기

bob@ibm.com 으로 메일을 보낼 때 "ibm.com"은 도메인 이름이지 mail server의 host name은 아님. "ibm.com"이라는 도메인 이름으로 서버의 IP주소를 찾아 TCP연결을 해야 함.

### load distribution

동일 도메인 주소에 접근하는 클라이언트들에게 IP주소 분산 시켜 주기

"www.yahoo.com"의 도메인 서버는 distributed 되어있는데, 이 도메인으로 접속하는 클라이언트들에게 하나의 IP주소만 알려줄 수 없음. DNS 서버가 "www.yahoo.com"의 IP주소들의 list를 갖고 있고, 접속하는 클라이언트들에게 이 리스트를 모두 넘겨줌. 응답 받은 클라이언트가 보통 가장 첫번째 거를 사용하기 때문에 서버가 리스트 목록을 로테이션 시켜줌. → 분산효과

## DNS: a distributed, hierarchical database

<img width="700" alt="스크린샷 2020-11-12 오전 9 30 36" src="https://user-images.githubusercontent.com/45806836/98879815-b9d98980-24c9-11eb-9d04-9e2e48ac0f69.png">


계층별로 나뉘는 DNS 서버 & host name도 나라별 or 목적별 계층을 가짐

- Root DNS Servers

    **: TLD server들의 host name과 그에 맵핑 되는 IP addr들**

    → Top-Level Domain(TLD): .com, .net, .org, ...

- Top-Level Domain servers

    : **Authoritative domain server들의 host name과 그에 맵핑 되는 IP addr들**

    → 각 DNS 서버는 .com/.net/.org로 끝나는 host name의 DNS 서버의 host name과 IP addr들을 보유.

- Authoritative domain servers

    : Canonical name들과 맵핑되는 IP주소들을 가짐

Ex. Client가 www.amazon.com의 IP주소를 요청할 때

- root server에게 com TLD server의 호스트 name과 IP주소를 요청
- .com DNS server(TLD server)에게 [amazon.com](http://amazon.com) DNS 서버의 IP주소 요청
- [amazon.com](http://amazon.com) DNS server에게 IP 주소 요청

## DNS hierarchy in Korea

<img width="668" alt="스크린샷 2020-11-12 오전 9 31 28" src="https://user-images.githubusercontent.com/45806836/98879862-d8d81b80-24c9-11eb-882b-0072192855e4.png">

country-code: kr

### DNS란?

DNS는 도메인네임서버이며 거대한 분산 데이터베이스 시스템이다. 인터넷은 서버들을 유일하게 구분할 수 있는 32-bit IP주소를 기본체계로 이용하는데 숫자로 이루어진 조합이라 인간이 기억하기에는 무리다. 따라서 DNS를 이용해 IP주소를 인간이 기억하기 편한 언어체계로 변환하는 역할을 DNS가 한다.

### 도메인 이름 체계

DNS가 저장 관리하는 계층 구조에서 최상위엔 루트(root)가 존재하고 그 아래로 모든 호스트가 트리 구조로 이루어져 있다. 그리고 이 루트 도메인 바로 아래 단계에 있는 것을 1단계 도메인이라고 하며 이를 최상위 도메인이라고 한다. 이를 약어로 TLD(Top Level Domain)이라고 한다. 최상위 도메인은 국가명을 나타내는 국가최상위도메인과 일반적으로 사용 되는 일반최상위도메인으로 구분된다.

## DNS: root name servers

전세계 13개기관에서 루트서버들을 관리. resolve name 하기 위해 root server로 요청해야 함.

우리나라에서 가장 가까운 건 도쿄에 있는 서버.

## TLD, authoritative servers

*top-level domain(TLD) servers:*

responsible for com, org, net, edu, aero, jobs, museums, and all top-level country domains, e.g.: uk, fr, ca, jp

authoritative DNS server들의 host name과 IP주소들을 저장

.kr: 한국인터넷정보센터(KRNIC: Korea Network Information Center)

*authoritative DNS servers:*

organization’s own DNS server(s), providing authoritative hostname to IP mappings for **organization’s named hosts**

can be maintained by organization or service provider

## Local DNS name server (LDNS)

Client가 DNS query를 보내야 하는데, 직접 root server에 질의하지 않고,

모두 자신이 속한 ISP안에 **local DNS server**를 가짐. (=**default name server**)

local DNS server는 일종의 proxy 역할. Client는 얘한테만 질의.

## DNS name resolution example

### iterated query


<img width="697" alt="스크린샷 2020-11-12 오전 9 31 47" src="https://user-images.githubusercontent.com/45806836/98879881-e42b4700-24c9-11eb-8a32-cdf5fc887a27.png">


최종 목적: LDNS가 [gaia.cs.umass.edu](http://gaia.cs.umass.edu) 의 IP 주소를 Client에게 알려주는 것

- Client는 자신이 속한 ISP의 LDNS에게 DNS 질의
- LDNS는 root server에게 .edu를 관장하는 TLD 서버의 IP주소 받아옴
- LDNS는 IP주소를 받아 umass.edu의 authoritative server의 IP 요청,
- LDNS는 authoritative server에게 다시 질의, 최종 목적지의 IP 요청

3) .edu TLD server의 IP주소 알려줌

5) authoritative server의 IP주소 알려줌

7) 최종 목적지의 IP주소 알려줌

⇒ 8번 이후 결과적으로 TCP connection을 묻고 연결 시작

### recursive query
  
<img width="698" alt="스크린샷 2020-11-12 오전 9 32 16" src="https://user-images.githubusercontent.com/45806836/98879920-f4dbbd00-24c9-11eb-90eb-b074ece669ff.png">

root DNS server의 overhead가 커짐

## DNS: caching, upgrading records

LDNS 가 IP주소 질의 후 DNS 응답을 받았을 때 로컬메모리에 응답 저장

기존의 요청에 의해 저장된 호스트 네임과 IP 주소 쌍에 대해서는 DNS 질의 없이 즉시 목적지 IP주소로 요청 가능

- ***TTL (Time To Live)*** : 보통 2일, TTL 기간 내에 저장된 정보를 제거

cache entry가 out-of-date 라면, TTL 전에 모를 것.

`update` / `notify` 매커니즘으로 엔트리 정보 업데이트 가능

## DNS records

DNS의 기본 기능: 기억하기 쉬운 호스트 네임과 컴퓨터가 처리하기 쉬운 IP주소 사이의 맵핑 (1)

1. hostname to IP address translation (기본 기능)
2. host aliasing - canonical(공식 이름), alias names(호스트의 별칭)
3. mail server aliasing
4. load distribution

### `type` 필드에 따라 DNS 맵핑을 달리 해야함


<img width="691" alt="스크린샷 2020-11-12 오전 9 32 26" src="https://user-images.githubusercontent.com/45806836/98879937-fc02cb00-24c9-11eb-8b50-f6fbc9d0d3e5.png">


DNS 분산 데이터 베이스를 구현한 DNS 서버들은 호스트 네임을 IP 주소로 매핑하기 위한 자원 레코드(Resource Record, RR)을 저장한다.

각 DNS는 하나 이상의 자원 레코드를 가진 메시지로 응답한다.

자원 레코드는 아래와 같은 필드를 포함하는 4개의 투플(tuple)로 이루어짐

```sql
RR format: (Name, Value, Type, TTL) 
```

### type=A

 1. hostname to IP address translation (기본 기능)

hostname으로 IP주소를 알고 싶을 때 사용

두 관계를 매핑하는 엔트리는 `type=A` 를 가짐

### type=CNAME

2. host aliasing - canonical(공식 이름), alias names(호스트의 별칭)

별칭 호스트 네임(host alias name)으로 정식 호스트 이름 (canonical name)을 알고 싶을 때 사용

두 관계를 매핑하는 엔트리는 `type=CNAME` 을 가짐

### type=MX

3. mail server aliasing

별칭 호스트 네임(host alias name)인 도메인 이름으로 정식 호스트 이름(메일 서버의 정식 이름)을 알고 싶을 때 사용

두 관계를 매핑하는 엔트리는 `type=MX` 를 가짐

### type=NS

별칭 호스트 네임(host alias name)인 도메인 이름으로 정식 호스트 이름을 알 수 있는 Authoritative DNS 서버의 hostname을 알고 싶을 때 사용

⇒  Authoritative DNS 서버의 hostname은 .com TLD DNS Server가 가짐

두 관계를 매핑하는 엔트리는 `type=NS` 를 가짐

## DNS protocol, messages

<img width="700" alt="스크린샷 2020-11-12 오전 9 32 57" src="https://user-images.githubusercontent.com/45806836/98879976-0d4bd780-24ca-11eb-907f-70f79592f8e0.png">


---

# Quiz

(51) T/F를 표시하시오.

(a) DNS 서비스는 사람이 읽기 편한 호스트 주소인 hostname을 컴퓨터가 처리하기 편리한 IP address로 바꾸어야 할 때 사용된다.

⇒ T.

(b) 호스트가 연결된 첫번째 라우터를 default gateway 혹은 default router라고 한다.

⇒ T.

모든 호스트는 default gateway 혹은 default router의 IP 주소를 이미 알고 설정해 놓아야하며, 발생하는 모든 패킷은 해당 라우터로 일단 전송(2계층에서 하는 일)하게 됩니다. 그럼 그 라우터에서 진정한 라우팅이 시작됩니다. ;)

(c) 현재 지구상에는 13개의 original root DNS 서버가 있으며 그 중 우리나라에 3개가 있다.

⇒ F. 전세계 13개의 원본 root 서버가 존재하며 우리나라엔 mirror 서버만 있다.

(d) 어떤 ISP 던지 내부 local DNS 서버를 가지고 있으며, 주로 해당 ISP의 authoritative 서버가 이 역할을 한다.

⇒ F. local DNS 서버는 Client에게 일종의 proxy 역할을 하는 default name server이며, authoritative DNS 서버에게 질의할 수 있다.

(e) 어떤 ISP 던지 내부 local DNS 서버가 있는데, 이는 해당 ISP 내 클라이언트들의 DNS query를 대신 처리해 주고 reply를 저장했다가 일정시간 안에 동일한 질의가 있을 경우 재사용하여 외부 트래픽을 줄여주고 DNS query 처리 속도를 높여주는 일종의 proxy 서버이다.

⇒ T.

(f) IBM의 authoritative DNS 서버의 canonical name이 dns22.ibm.com 이라고 한다면, (ibm.com, dns22.ibm.com, NS, … )라는 RR이 필요하며, 이는 IBM의 authoritativeDNS 서버에 저장되어 있다

⇒ F. 

이 RR은 상위 DNS 서버인 .com TLD 서버에 저장되어 있어야 한다.

(52) 호스트 A가 호스트 B와 통신하고자 할 때 DNS 서비스는 결과적으로 호스트의 B의 IP주소를 찾기(이 작업을 DNS resolution이라 함)위한 것이다. 그런데 IP 주소는 네트워크 계층에서 사용하는 주소이며, 호스트 A가 속한 ISP안에서 호스트 C, D, … 도 호스트 B와 통신하고자 호스트 B의 IP주소를 찾는 경우를 고려한다면, default router에서 DNS resolution을 하는 것이 더 효율적일 텐데, 왜 호스트 A의 application 계층에 DNS resolution을 수행하나?

⇒ network 계층은 빨리 deliver만 하면 돼서, 복잡한 연산은 application layer에서 함.

인터넷은 core 네트워크가 **(빠르고 정확한) 패킷 전달에 집중하게 하기 위해서 복잡한 연산(기능)은 에지에 연결된 호스트에서 하도록 설계**되었기 때문이다.

(53) DNS resolution의 두가지 방법 중 root DNS 서버에 더 많은 overhead를 주는 방식은 무엇이며 그 이유는 무엇인가?

⇒ recursive query. LDNS가 root name server에 query를 보내면, root name server는 자신의 server에 등록되어 있는지 확인한 후 없으면 직접 TLD 서버에게 요청을 한다. 실제 domain name을 가지고 있는 server까지 query가 이동하여 IP주소를 얻으므로 결과적으로 iterative query 보다 root 서버에 더 많은 overhead를 준다.

recursive 방법은 **중간 DNS 서버들이 LDNS를 대신해서** 질의 응답을 한번씩 추가로 더 진행하게 되는데, root DNS 서버는 최상위 계층에 있기 때문에 root DNS 서버가 처리하게 되는 query수는 그 보다 하위 계층에 있는 TLD DNS 서버나 authoritative DNS 서버가 처리하는 query수보다 더 많아지기 때문이다.

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f3de32a7-1937-4793-9c42-5b5420aee97e/_2020-10-08__7.34.57.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f3de32a7-1937-4793-9c42-5b5420aee97e/_2020-10-08__7.34.57.png)

(예를 들어 만일 하나의 root DNS 서버에 3개의 TLD 서버가 있고, 각 TDL 서버에 10개의 authoritative DNS 서버가 붙어있는 경우, 총 30개의 authoritative DNS 서버가있게된다. 이때 각 authoritative DNS 서버로 DNS query message가 가는 경우라면 각 authoritative DNS는 1회, 각 TLD 서버들은 10회, root DNS 서버는 30회의 req/res를 해야한다.)

(54) LDNS 서버가 DNS reply를 받은 후 일정 시간 저장하기 위한 타이머 값을 무엇이라고 하는가? (동일한 이름의 필드가 IPv4 헤더에도 있다고 수업시간에 설명함)

⇒ Time To Live

(55) DNS 서버는 hostname의 IP주소를 알려주는 기본 작업 외에 부가적으로 3가지 기능을 할 수 있다. 무엇인가?

⇒ host aliasing, mail server aliasing, load distribution(load balancing)

(추가 질문) 각각이 어떤 기능인지 설명하시오.

host aliasing: 어느 호스트의 외부에 알려진 alias name에 해당하는 그 호스트의 진짜이름, canonical name을 알려주는 기능

mail server aliasing: 이메일 주소에서 @의 뒤부분에 해당하는 도메인안의 이메일 서버의 hostname을 알려주는 기능

load balancing: 하나의 서버 호스트의 hostname (alias name)에 해당하는 IP 주소 여러 개의 매핑을 가지고 있다가 질의가 들어오면 round robin 방식으로 최상단에 적히는 IP 주소를 변경함으로서 해당 서버로 접속하는 클라이언트들을 분산시키는 기능
