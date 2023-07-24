# 의문 : 왜 자바는 힙메모리와 스택메모리를 구분해서 사용될까??
그냥 힙메모리만 쓰면 안돼?? or 스택메모리만 쓰면 안돼?? 왜??

## 자바의 메모리 관리
- 자바의 메모리 관리는 GC(가비지컬렉터)가 수행한다.
- 또한 자바의 메모리 영역은 heap영역과 stack영역으로 구분된다.
- 이 두 영역에 저장되는 요소는 다르다. 즉 각자 역할이 있다는 것.
- 그렇다면 왜 "굳이" 역할 구분을 했을까??
  
![image](https://github.com/HyemIin/TIL/assets/114489245/411cec73-6251-4544-b9f4-07c080b1b402)
힙메모리는 공유하고, 스택 메모리는 구분되어 있는 것을 확인할 수 있다. 또한 스택 메모리가 힙 메모리의 객체 데이터 값을 참조하고 있는 것을 확인할 수 있다.

### Stack 메모리 영역
- 정적으로 할당된 메모리 영역이다.
- 기본 자료형(원시타입)의 데이터 값이 할당된다.
- 객체의 참조 값이 할당된다.(즉, 주소값)
- Stack의 메모리는 쓰레드 당 1개씩 할당 된다. 또한 각 쓰레드끼리는 다른 쓰레드의 Stack 영역에 접근할 수 없다.

### Heap 메모리 영역
- 동적으로 할당된 메모리 영역이다.
- 객체의 실제 데이터 값이 할당된다.
- Heap 영역은 단 1개만 존재하며, 쓰레드끼리 공유하는 구조이다.
- 스택에 저장되는 것을 제외한 모든 것이 힙에 저장된다고 생각하면 된다.

사실 글로 쓰면 좀 헷갈리긴 한다.(그리고 아직 왜 둘을 구분하는지에 대한 답변도 안나옴)
코드로 봐보자.

### 코--드
<img width="589" alt="image" src="https://github.com/HyemIin/TIL/assets/114489245/5dbdac82-c18b-4dc6-b95e-da9de18e10e0">

- int num = 3;
  위 코드에 의해 stack 메모리에 실제 num = 3라는 값이 할당된다.
  
- num = operator(num);
  operater 메서드가 실행되면서, stack 메모리에 param = 3라는 값이 할당된다.
  
- int tmp = param*5;
- int result = tmp/3;
  위 코드에 의해 stack 메모리에 tmp = 15라는 값이 할당되며 + result = 5라는 값 또한 할당된다.

현재 스택 메모리 상태.jpg

<img width="795" alt="image" src="https://github.com/HyemIin/TIL/assets/114489245/16e0124c-3955-4f98-938c-c92e60943b28">

- num = operator(num);
  위 코드가 실행됨에 따라 operator 메서드는 종료되고, operator에서 사용된 모든 지역변수는 Stack 메모리에서 pop 된다.
  
최종 스택 메모리 상태.jpg

<img width="817" alt="image" src="https://github.com/HyemIin/TIL/assets/114489245/7d7d3a2a-816b-45e2-8411-74113ff9e1bc">

- 이후 main 메서드도 종료되면, 남아있던 num 데이터 값도 stack 메모리에서 pop 됨으로써 종료된다. 
