# 싱글스레드와 멀티스레드 간 컨텍스트 스위칭으로 인한 속도 비교

## 싱글스레드
- '-'를 출력하는 작업과 '|'를 출력하는 작업을 하나의 쓰레드가 연속적으로 처리하는 시간을 측정하는 예제이다. 시간 측정의 편의성을 위해 new String("-")을 활용하여 "-" 대비 수행 속도를 늦췄다
- 평균적으로 -와 |의 수행시간 차이는 약 50~60ms 정도로 측정됐다.(5회 측정 평균)

## 멀티스레드
- 싱글스레드와 동일한 조건 하에 -와 |를 출력하는 작업의 소요시간을 측정했다. 다만 멀티쓰레드 환경을 구현하기 위해 Thread 클래스를 상속받은 "ThreadEx3_1"를 구현했다.
- 결과적으로 약 60~70ms 정도의 수행시간 차이가 보였다.(5회 측정 평균)

## 결론
- 확실히 멀티쓰레드와 싱글쓰레드의 속도차이는 존재한다. 
- 멀티쓰레드의 경우 2개의 쓰레드가 번갈아가면서 작업을 처리하기 때문에 쓰레드 간 작업전환시간, 즉 컨텍스트 스위칭이 발생하기 때문이다.

- 다만, 두 쓰레드가 서로 다른 자원을 사용하는 작업의 경우, 싱글쓰레드 프로세스보다 멀티쓰레드 프로세스가 훨씬 효율적이다.
예를 들어, 사용자에게 데이터를 입력받거나, 네트워크를 통해 데이터를 주고받는 작업
