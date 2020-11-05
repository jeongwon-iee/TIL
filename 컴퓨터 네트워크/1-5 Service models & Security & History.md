`Pop Quiz`

one hop delay에 포함되는 네 가지

- input port에서 L bit store 한 다음 에러체크하고 **routing,** routing table lookup 하는 process, output port 로 switching ⇒ processing delay
- output queue에서 대기하는 시간, congestion network일 때 영향을 받음 ⇒ queueing delay

    → Traffic Intensity = (초당 input queue로 들어오는 평균적인 비트 개수)/초당 output queue로 나가는 비트 개수(transmission rate) = La/R

- 순차적으로 packet 단위로 transmit, cable의 bandwidth에 영향 ⇒ transmission delay
- 첫번째 비트부터 전송, 한 bit가 1 hop을 travel ⇒ propagation delay

src:sender, dst:receiver

1 hop: link의 끝에서 끝, router의 input buffer로부터 다음 router의 input buffer까지 = link의 개수

src~des 사이의 end to end delay(e2e)? one hop delay


**end to end delay **
= hop의 개수 * one hop delay
= hop의 개수 * (Dproc + Dqueue + Dtrans + Dprop)

`Pop Quiz`

1) TI를 계산하면 queueing delay 를 정확하게 알 수 있다.

⇒ False. TI는 평균값을 계산한 것이고, **queue에 들어오는 패킷들이** **bursty 하기 때문에** 대략 유추만 할 수 있다.

2) Content Provider Network에는 그 content를 제공하는 회사의 traffic만 이동한다.

⇒ False. Google 같이 content도 가진 CDN(Content Distribution/Delivery Network)에는 회사의 traffic만 지나다니지만 Akamai 같은 Content Provider Network 회사는 content 회사가 아닌 third party임. Content 만드는 회사(Ex. Netflix, Hulu)에게 link와 서버, network를 팔음. 따라서 CPN에는 Netflix traffic만 지나다닐 수 없음

---

## Packet loss

output queue에 data가 지나치게 몰릴 때 (transmission rate, R값 보다 지나치게 많은 rate의 bps가 들어오면) queue가 넘쳐 loss가 생긴다.

## Throughput

초당 sender에서 receiver까지 보낼 수 있는 비트 수 (**end 2 end** 개념) 

← hop에 연결된 link의 capacity( = R)값이 영향 (queueing delay는 상황에 따라 다르므로)

⇒ 제일 성능(link capacity)이 떨어지는 link가 throughput을 결정짓게 됨

= ***bottleneck link***

(throughput에 문제가 있어 네트워크를 개선해야 한다면 bottleneck link를 찾아 throughput을 올려야 함)

### Throughput: Internet scenario

e2e의 throughput은 어떻게 결정될까? bottleneck이 뭘까?

Rc, Rs, 10 users가 사용하는 link 중 논리적으로 가장 적은 bps값을 가진 link

<img width="694" alt="스크린샷 2020-11-05 오후 3 36 02" src="https://user-images.githubusercontent.com/45806836/98206064-9e77f700-1f7c-11eb-9ebb-fd3470985c3f.png">

⇒ bottleneck link는 정해져 있는 게 아니다.

가장 적은 bps값을 가진 link이거나 아주 높은 transmission rate를 가진 link의 다음 link가 bottleneck link가 될 수 있다.

⇒ 중간에 몰릴 수 있는 traffic을 잘 고려하여 R을 설계하는 것이 중요하다. (bottleneck을 잘 찾아서 throughput을 증진시켜야 한다.)

Ex. bottleneck 찾기

<img width="665" alt="스크린샷 2020-11-05 오후 3 36 18" src="https://user-images.githubusercontent.com/45806836/98206083-a8015f00-1f7c-11eb-8c20-a2ea1d3671d3.png">

R을 4 users가 사용하니 100Mbps씩 사용. 20, 100, 40bps니 bottleneck은 Rc.

# Protocol layers, service models

## Protocol "layers"

멀리 떨어진 src(end)부터 dst(end)까지 communication 하려고 msg를 주고 받기 위해 중간 과정에서 해야할 일이 많음

어디로 가야할 지 라우팅, 비트에서 시그널로, 산 넘고 물 건너 보내고, 시그널에서 다시 비트로, 오다가 깨졌는지 확인 해보고, 보낸 대로 받았는지 검사하고,  도착하면 맞는 application process한테 보내 줘야 하고 ... 수많은 기능

→ 일을 처리해야 하는 순서대로 해야할 일들을 층별로 구별해 놓음. 해당 layer에서 define된 goal을 수행, 층별로 독립적으로 수행 하기 위해 input output을 정의, 순서대로 처리한 후 도착하면 역순으로 처리


dst로의 길을 찾고자 도중 라우터를 거치기도 함.

## Why layering?

시스템이 복잡해 해야 하는 일들을 계층 별로 분리, 모듈화 하여 독립적으로 작업을 수행하게 함.

**reference model** 각 layer에서 해야 하는 일들의 정의

cross-layer protocol: 두 계층의 기능을 커버

## 5-layer Internet protocol stack

- **application 계층 (msg)** user가 사용하는 최상단 network application을 support하는 protocol. IETF에서 RFC문건으로 정의

    Ex. FTP, SMTP(email이라는, network를 필요로하는 application을 support), HTTP(web, file upload)

- **transport 계층 (segment)** dst를 찾아 process to process data transfer(마치 같은 컴퓨터에 있는 것처럼 메시지를 주고 받음) 그러려면? dst에 가 있어야함. 그 역할을 하는 게 routing 하는 밑 계층.(그래서 아래 계층이 윗 계층의 service provider, 윗 계층은 아래 계층이 제공하는 service를 이용하는 user). IETF에서 RFC문건으로 정의

    Ex. TCP, UDP

- **network 계층 (packet, datagram)** src로부터 dst까지 routing을 수행. IETF에서 RFC문건으로 정의

    Ex. IP, routing protocols(OSPF, RIP, BGP)

- **link 계층 (frame)** link, 한 hop을 지나가는 일을 수행. IEEE에서 정의 NIC(Network Interface Card)에 있음. link layer의 두 sublayer 중 윗 계층까지 sw로 개발할 수 있음

    Ex. Ethernet, 802.11 (Wifi), PPP

- **physical 계층**  물리적인 일 담당. IEEE에서 정의, NIC에 있음

⇒ 각 계층은 아래 계층의 서비스를 제공 받는 **service user**, 윗 계층에게 서비스를 제공하는 **service provider**

각 protocol이 처리하는 data의 기본 단위 (Protocol Data Unit: **PDU**)가 모두 달라 이름을 다르게 붙임.



## Encapsulation

각 층에서 헤더를 붙여 데이터를 아래 계층으로 보냄. (SMTP인지 HTTP인지 프로토콜에 따라 다른 헤더를 붙이겠지)

*모든 PDU에는 헤더가 있다!*

src의 layer에서 붙인 헤더는 dst의 해당 layer가 봐야 하는 것



- **source's** **application layer** 헤더를 붙여 4계층으로 내려보냄
- **source's** **transport layer** process2process 해야하니까 dst의 transport layer가 보라고 헤더를 붙여 내려보냄. port 주소 담김
- **source's** **network layer** 라우팅할 때 보라고 dst address(IP) 등 붙여 내려보냄
- **source's** **link layer** 1 hop을 이동 (switch는 3계층이 없으니 IP 주소도 없고 2계층의 MAC주소도 없음), 여기서 붙이는 header는 다음 switch의 link layer가 보라고 붙이는 것. 주로 MAC addr 담김
- **switch's** **link layer** router로 가기 위한 MAC addr가 담김
- **router's link layer** link layer에서 1 hop을 이동한 것
- **router's network layer** 헤더를 떼고 올려줘야 src의 network layer에서 붙인 헤더를 보고 dst addr를 알 수 있음
- **router's link layer** 다시 ****헤더에 다음(여기선 dst) 2계층 주소를 설정
- **destination's link layer** 받음
- **destination's network layer** 헤더의 dst addr를 보고 맞게 왔는지 확인할 수 있음
- **destination's transport layer** 헤더의 port 번호를 보고 맞는 application에 msg 전달

한 hop(link)이 길면 중간에 **switch**가 있을 수 있음

패킷 네트워크에서 패킷이 1-hop을 지나간다고 하는 것은 하나의 네트워크를 지나간다는 의미

보다 구체적으로, “사용자 디바이스(end host)와 라우터” 사이 혹은 “라우터와 라우터” 사이를 이동하는 것을 의미

⇒ 사용자와 라우터 사이에 존재하는 2계층 스위치는 같은 네트워크에 포함되므로 하나의 홉에 포함됨

⇒ 위 그림에서 source에서 router까지가 한 hop. source의 link 계층 헤더는 router의 link 계층에서 봄

link 계층과 network 계층의 차이

network 계층은 최종 목적지를 향한 **`다음 노드`** (라우터 혹은 목적지 호스트)를 결정. → 3계층 헤더에 포함된 목적지 주소는 **destination host의 IP 주소**

link 계층 헤더에 포함된 목적지 주소는 `**다음 노드`** 까지 가는 **2계층 주소**

결과적으로 하나의 패킷이 한 홉을 지나갈 때 3계층 IP 목적지 주소는 변하지 않으나 2계층 목적지 주소는 계속 변함

⇒ 2계층에서 주로 MAC addr 사용, 3계층에서 주로 IP addr 사용

# Network security

90년대 초반 불특정 다수가 Internet을 쓸 수 있게 되면서 보안 이슈가 등장 하게 함.

### field of network security

네트워크로의 어떤 공격을 할 수 있는지 알고, 공격을 방어하고, 미리 대비할 수 있다.

### put malware into hosts via Internet

**virus** 기생해서 실행, 사용자의 개입(실행)이 필요, 컴퓨터 내 공격

**worm** 독자적으로 실행, 사용자의 개입이 필요 없음, 네트워크 공격 

### Denial of Service (DoS)

쓸데없는 패킷 계속 보내서 처리하게 하기

<img width="695" alt="스크린샷 2020-11-05 오후 3 37 59" src="https://user-images.githubusercontent.com/45806836/98206218-e39c2900-1f7c-11eb-8572-2a9b4d5c2635.png">

### packet "sniffing"

케이블이 붙어있기만 하면 share할 수 있는 Ethernet과 wireless 등에서 정보 빼서 보기 


<img width="690" alt="스크린샷 2020-11-05 오후 3 38 12" src="https://user-images.githubusercontent.com/45806836/98206240-ebf46400-1f7c-11eb-86d8-28105fd613f6.png">


### IP spoofing

B인척 하고 보내거나 내용을 바꾸기 (man-in-the-middle-attack)

<img width="698" alt="스크린샷 2020-11-05 오후 3 38 47" src="https://user-images.githubusercontent.com/45806836/98206292-01698e00-1f7d-11eb-9fa6-31ea45603e7e.png">


# Internet history

연도 안에서 일어난 큰 이슈들

60년대: Packet Switching. 인터넷의 어머니인 ARPAnet 등장

70년대: NCP (TCP 이전), 최초의 인터넷 서비스인 email(loss-sensitive), 위성 인터넷 ALOHAnet과 radio 등장, 인터넷의 철학

> - minimalism, autonomy : no internal changes required to interconnect networks
- best effort service model
- stateless routers
- decentralized control

1. 네트워크 연결의 내부적인 변화를 주지 않는다.

2. Best effort service model : 최선을 다하지만 throughput, delay, loss 등의서비스 품질을 보장하지 않으며 그때 그때 망 상황에 따라 품질이 결정되는 서비스

3. Stateless routers : 오류가 나도 report로 저장하지 않는다.

4. Decentralized control : 중앙제어 하지 않는다

80년대: TCP/IP, DNS

90년대: Web의 등장, 누구나 인터넷을 쓸 수 있게 됨, security

현재: mobility, CDN 등장, cloud(컨텐츠를 관리하는 방법), MEC(Multi access edge computing), Fog

---

# Quiz

(30) T/F로 표시하시오.

(a) Akamai에서 운영하는 CDN(Content Distribution/Delivery Network)에는 한개의 컨텐츠 회사 데이터만 지나다닌다. F

⇒ akamai같은 CDN에는 한 회사의 트래픽만 지나다닐 수 없음. (수업 시간에 Akamai는 Thire Party Content "Provider" Network 회사라고 했는데 CDN과 CPN을 공용으로 써도 되는 건가요? 네. 공용해서 사용함

(b) Throughput을 결정하는 bottlenext link는 source와 destination 사이 링크들 중 Transmission rate 값이 가장 큰 링크이다. F

⇒ source와 destination 사이의 end-to-end path 중에 transmission rate이 가장 작은 링크

→ Transmission rate이 가장 적은 링크이거나 아주 높은 링크의 다음 링크” 라고 보조설명을 한답변이 있었는데 약간 불안합니다. 지금은 이론적인 상황만을 설명하고 있으므로 source와 destination 사이의 end-to-end path 중에 transmission rate이 가장 작은 링크가 맞습니다. 아주 높은 링크의 다음 링크는 e2e path에 속한 링크가 딱 2개인 경우를 설명하는 것 같네요..

(c) 2계층 PDU 이름은 segment 이고 4계층 PDU 이름은 frame 이다. F

⇒ 5계층:메시지, 4계층: 세그먼트, 3계층: 패킷 or datagram, 2계층: 프레임

(d) 두 사용자가 Point-to-point로 연결된 네트워크에서 제3의 해커가 sniffing할 수 있다. F

⇒ 사이에 제 3의 해커가 위치해야 sniffing 할 수 있음

(e) 사용자의 실행 없이도 자가복제가 가능하며, 주로 네트워크 링크 혹은 서버를 공격하는 malware를 worm이라고 한다. T

(f) ALOHA 네트워크는 ARPANET 이전에 등장했다. F

⇒ ARPAnet은 60년대 등장한 인터넷의 어머니

(g) 1970년대에는 DECnet, XNA등 다양한 네트워크가 등장했다. T

(31) Throughput을 정의하시오.

⇒ end host와 end host 사이 혹은 sender와 receiver, source와 destination 사이 단위시간당 전송되는 비트수 혹은 초당 전송되는 비트 수

(32) 슬라이드 #62 답은?

⇒ ****Rc 가 bottleneck, 종단 대 종단으로 취할 수 있는 최대 throughput은 

20Mbps = min{Rs(40),R(400/4=100), Rc(20)} Mbps

4쌍의 client-server가 동시에 connection을 열어서 사용한다는 가정을 한 것 이므로 중간 링크의 속도를 the shared-link transmission capacity (400Mbps)를 4로 나눈 100 Mbps로 봄

(33) 슬라이드 #62에서 만일 서버쪽이 모두 90Mbps, 중간 링크가 300 Mbps, 클라이언트쪽이 모두 100 Mbps라면 Throughput은 어떻게 달라지는가?

⇒ 75Mbps= min{ Rs(90), R(300/4=75), Rc(100) }

(34) 최초의 인터넷 서비스는?

⇒ E-mail

(35) best-effort 서비스의 의미는?

⇒ **throughput, delay, loss 등의 서비스 품질을 보장하지 않으며 그때 그때 망 상황에 따라 품질이 결정되는 서비스**

(36) Vint Cerf가 설립한 internetworking의 4가지 원칙은?

⇒ 

- **각 ISP의 자치권 보장 (즉, 최소한의 변경으로 ISP 망 연결)**
- **end-host들에게 서비스 품질을 보장하지 않는 best-effort 서비스**
- **라우터들이 지나간 연결에 관한 정보를 유지하지 않는다.**
- **하나의 관리자가 제어하지 않는다**
