# TCP

- point to point 가 핵심. 하나의 소캣과 하나의 소캣. 소캣 한쌍끼리의 통신을 책임지는 것
- 파이프라인으로 데이터 쏟아부음. 근데 막 쏟아붇는게 아니라, 윈도우 크기만큼만.(sender Window Buffer) 같은 크기의 버퍼가 Receiver쪽에도 있음. 오는만큼 받아야하니깐. 
근데 웹서버-클라이언트 관계에서 서로가 sender이자 receiver이기 때문에 각각 sender buffer, receiver buffer 둘 다 갖고 있음
- 아래 그림을 보면, 계층을 내려갈 수록 하나씩 담기고 있음
    
<img width="613" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/f163f893-b65e-4040-8333-a8e2f5453158">
    
- APP -> PHY 로 보내므로 7층->1층 헤더를 차례대로 붙인다.
AH(Application Header) -> TH(Transport layer Header) -> NH(Network layer Header) -> DH(Datalink layer Header) 순으로 붙는다.

## Header
- 헤더 정보가 17개나 있음 ㅋㅋ 그만큼 중요하시다는 거지
<img width="552" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/9788d203-f172-440a-bd9a-c26626e73e5d">

- 위 그림은 TCP segment의 header 구조이다.
    - source port : 보내는 sender 측의 port 번호
    - destination port : 받는 receiver 측의 port번호
    - sequence number : 패킷의 일련번호
    - acknowledgement number
    - receive window : receiver buffer의 크기
    - UAPRSF의 bit가 0이면 미사용, 1이면 사용을 뜻한다.

## **[TCP 특징]**

- **Reliable Control**
    - timeout
        - (정의) 패킷을 주고받을때 일정 시간이 지나도 패킷 송수신이 완료되지 않는다면 다시 패킷을 보내는 제한 시간. 즉 **보낸 패킷이나 응답 패킷 자체가 유실됐다고 판단하는 것**
        - **바로 네트워크가 혼잡하다고 판단하는 근거가 되는 지표이다.**
        - 기본적으로 패킷을 주고받는 시간의 평균 RTT값을 기준으로 삼으나, 각 패킷별로 이동 경로가 다르기때문에 시간도 상이함. 그렇기에 따로 구하는 공식이 있음
    - 3 ACK Duplicated
        - (정의) 송신(클라이언트) 측이 3번 이상 중복된 ACK를 받는 경우
        - 네트워크 상황 상 수신 측이 특정 시퀀스 넘버 이후 데이터를 정상적으로 처리하고 있지 못하다는 뜻
        - 단, TCP 특성 상 패킷이 순서대로 도착하는 것이 아니므로, 해당 문제가 발생했다 해서 바로 네트워크 혼잡성이라고 판단하진 않는다. Timeout에 비해 덜 치명적.
        - Go-back-N이나, Selected Repeat 등 오류 제어 정책에 따라 문제 패킷에 관련한 부분만 따로 처리하도록 함. 갑자기 congestion control하지 않는다는 뜻.
    - TCP는 1개의 타이머만 가지며, 타임아웃 후 유실된 패킷만 선택하여 재전송한다.
        - 타이머 1개인건 go back N과 비슷하지만, 유실된 패킷만 고르는 방식은 selected Repeat 방식이다.

<img width="613" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/17fa76c0-713f-4514-af66-752b6bcb6d67">

- **Flow Control**
    - Receiver buffer가 패킷을 받을 수 있는 양만큼 보내야 하기 위해 진행 / bps조절
    - 결국 Receiver buffer 빈공간 크기에 의존
    - 또한 네트워크 상황에도 의존
    - 예를 들어 네트워크가 감당할 수 있는 패킷양이 5이고, buffer 빈공간이 10이면, 5를 기준으로 진행
    
- **3 way handshake**
SYN : 연결요청. 세션을 설정하는데 사용
    
   <img width="611" alt="스크린샷 2023-08-15 오후 10 31 13" src="https://github.com/HyemIin/TIL/assets/114489245/c793fe04-9e3a-494f-b6c0-5eed553f8204">
    
    - **#1 클라이언트 → 서버**
        - 서버에게 패킷 1개 전달 후, SYN,ACK 응답을 기다리는 상태(SYN_Send)
            - SYN bit 1
            - 시퀀스 넘버 : x
    - **#2 서버→클라이언트**
        - 클라이언트에게 1개 패킷 전달 후, SYN_Recieved 상태
        - SYN,ACK bit가 둘 다 1로 설정해서 보내는 이유는 SYN(너네 포트 열어놔주세요), ACK(너가 보낸 요청 받았어요) 의미
        - ACK number가 x+1이라는 건, x번 패킷까진 받았으니 시퀀스 넘버가 x+1번인 패킷 달라는 뜻
            - SYN&ACK bit 1 (signal bit set)
            - 시퀀스넘버 y
            - TCP헤더의 acknowledge number(ACK아님) :  x+1
    - **#3 클라이언트 → 서버**
        - 다시 서버에게 패킷 1개 전달.실제 전달할 데이터를 서버에게 ACK1 & 시퀀스넘버 y+1을 알려준다.
        - ACK bit 1
        - TCP헤더의 acknowledge number(ACK아님) :  y+1
        - 이건 사실 HTTP Request와 동일하다.

<img width="465" alt="스크린샷 2023-08-15 오후 10 13 02" src="https://github.com/HyemIin/TIL/assets/114489245/9226f6be-5a8f-499d-91de-ad90d03a4e5a">
            
- **Congestion Control**
    - (정의) 네트워크 인프라 상황에 맞춰 TCP 연결 시 전달할 패킷 전송 속도(원도우 사이즈)를 조절하는 컨트롤
    - (배경) public 네트워크가 포용할 수 있는 패킷의 양은 유한한데, 각 클라이언트가 제한없이 패킷을 쏟아내는경우, 악순환이 생길 수 있음(패킷이 많이 유실되고, 유실된만큼 패킷을 재전송하고,,,)
    이런 악순환을 ‘혼잡 붕괴’라고 한다.
    - 네트워크 상황을 그때그때 어떻게 아는지?
        - End to End congest control
        - (정의) 클라이언트와 서버가 네트워크 상황을 유추하는 방법 (무책임하긴해)
            - 유추할 수 있는 방법은 TCP 통신을 통해 유추함.
            - SYN을 보내고 ACK가 와야하는데, ACK가 얼마나 빨리오는지를 기반으로 유추
    
    **[How?] 3가지 phase를 가진다.**
    
    - Slow Start
        - (정의) 패킷보내는 양을 점차 늘려보면서 네트워크가 포용할 수 있는 한계를 파악
        - 이름만 slow;; 빠름. 1개 보내보고, 2개보내보고, 4개보내보고, 8개보내고,,이런 식으로 제곱으로 보내면서 파악하기 때문.(원도우 사이즈를 늘리는 것과 같은 말)
    - Additive increase(합 증가)
        - (정의) slow start로 2배씩 늘려나가다가 특정 순간(Threshold)에서 늘리는 갯수를 세밀하게 늘리는 것(1개 씩)
        - (why) 언제가 네트워크 한계점인지 정확하게 파악하기 위함
    - Multiplicative decrease(곱 감소)
        - (정의) 네트워크 임계치를 파악하고, 보내는 패킷의 양을 현재 수준의 절반으로 줄이는 것
        - (why) 네트워크 상황에 따라 패킷을 정상적으로 보내기 위해서는 의미있는 양의 감소가 필요하기 때문
            
<img width="315" alt="스크린샷 2023-08-16 오후 7 23 39" src="https://github.com/HyemIin/TIL/assets/114489245/cc488662-e0e0-47c6-9852-9cc71bd94dbb">
            

## **[대표적인 혼잡 제어 정책]**

- TCP Tahoe(이거 안씀)_ver 1.0
    - 무조건 패킷 유실이 된 경우, 패킷 전달 개수를 1개부터 다시 시작
    - 즉, 패킷 유실을 발생한 상황이 3 duplicated ACK인지,TimeOut인지 구분하지 않고 무조건 패킷 1개 부터 재시작하는 것
- TCP Reno _ver2.0
    - 3 duplicated Ack로 패킷 유실이 탐지된경우, 그렇게 심각한 것이 아니므로 절반(?)으로만 패킷 양을 줄임
    - Timeout인 경우, 패킷 전달을 1개부터 다시 전달하는 action으로 시작
    
    <img width="715" alt="스크린샷 2023-08-16 오후 7 24 54" src="https://github.com/HyemIin/TIL/assets/114489245/a17e463e-dc2f-4caf-84fc-6b5af0853b2c">
    
- TCP is Fair
