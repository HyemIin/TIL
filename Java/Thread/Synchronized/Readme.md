# Synchronized
- (배경) 자바 멀티 스레드 환경에서는 스레드끼리 static 영역과 heap 영역을 공유하므로 쓰레드에 따라 데이터의 상태가 서로 얽힐 수 있다.
- 데이터의 원자성을 지키기 위해 동기화하는 작업이 필요하다.
- 객체의 메소드에 synchronized 키워드를 사용하여 멀티쓰레드 환경을 구현할 수 있다.
- 현재 이 메서드가 속해있는 객체에 lock을 건다.
- synchronized로 지정된 임계영역은 한 스레드가 이 영역에 접근하여 사용할때 lock이 걸림으로써 다른 스레드가 접근할 수 없게 된다.
- 이후 해당 스레드가 이 임계영역의 코드를 다 실행 후 벗어나게되면 unlock 상태가 되어 그때서야 대기하고 있던 다른 스레드가 이 임계영역에 접근하여 다시 lock을 걸고 사용할 수 있게 된다. 

- lock은 해당 객체당 하나씩 존재하며, synchronized로 설정된 임계영역은 lock 권한을 얻은 하나의 객체만이 독점적으로 사용하게 된다. 
- 자바에서는 deadlock을 방지하는 기술이 제공되지 않으므로 되도록이면 synchronized 메서드에서 다른 synchronized 메서드는 호출하지 않도록 한다.

### 클래스 종류
- Bank
- Parents extends Thread(쓰레드 1)
- Son extends Thread(쓰레드 2)

#### Bank
- 초기 자본 : 10000원
- saveMoney 메서드(매개변수 + 자본)
- minusMoney 메서드(자본 - 매개변수)
