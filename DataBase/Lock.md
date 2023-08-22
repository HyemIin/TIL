트랜잭션이란, DB 상태를 변환시키는 하나의 논리적인 기능을 수행하는 작업 단위로, 하나의 트랜잭션은 구분될 수 없다. 이처럼 트랜잭션의 가장 큰 특징은 작업의 원자성을 보장하는 것이다.

# LOCK
## 1. Lock이란? (what)
Lock이란, 트랜잭션 처리의 순차성(즉, 데이터 일관성)을 보장하기 위한 매커니즘.
결국 LOCK도 다양한 트랜잭션 격리 수준의 일부이다.

즉, 사용자 A가 Data에 접근 할 때 데이터에 Lock을 걸어 나머지 사용자들이 이 데이터에 접근하지 못하도록 하는 것.
![image](https://github.com/HyemIin/TIL/assets/114489245/19523798-546b-481c-80fe-d0526d49ea70)
![image](https://github.com/HyemIin/TIL/assets/114489245/8608f150-da77-48e9-8cc5-e46210317ac3)

## 2. 그러면 Lock이 왜 필요해?? (why?)
[배경]
우리는 어플리케이션을 사용하다 보면 Database에 저장된 Data를 수정하는 일이 발생한다.
이 때 사용자가 단 한 명이라면 어떠한 문제도 발생하지 않는다. 하지만 어플리케이션의 사용자가 여러명이고, 접근하려는 데이터가 같은 경우, 수정의 결과가 올바르게 적용되지 않을 수 있다.

이처럼 예를 들어 DB와 같은 자원에는 여러 사용자가 동시 접근(connection)할 가능성이 높은데, 이와 같은 경우 데이터가 오염될 확률이 높아 동시성 제어를 통해 데이터의 일관성을 보장해줘야할 필요가 있다.

정확하게는 ACID를 적용한다.

- 원자성(Atomicity) : 트랜잭션은 쪼갤 수 없는 최소한의 업무 단위(all or nothing)
- 일관성(Consistency) : 실행 후, 언제나 일관된 DB상태
- 독립성(Isolation) : 하나의 트랜잭션 실행 중 다른 트랜잭션 겐세이 금지
- 지속성(Duration) : 트랜잭션 결과는 시스템 고장유무와 관계없이 영구적 반영

## 3. Lock의 종류
### 공유(Shared) Lock - read 시 활용

공유 Lock은 데이터를 읽을 때 사용되어지는 Lock이다. 

이런 공유 Lock은 공유 Lock 끼리는 동시에 접근이 가능하다. 즉, 하나의 데이터를 읽는 것은 여러 사용자가 동시에 할 수 있다라는 것. 하지만 공유 Lock이 설정된 데이터에 베타 Lock을 사용할 수는 없다.

사용법 예시 : select * from ... for share;

### 베타(Exclusive) Lock - update 시 활용
베타 Lock은 데이터를 변경하고자 할 때 사용되며, 트랜잭션이 완료될 때까지 유지된다. 

베타락은 Lock이 해제될 때까지 다른 트랜잭션(읽기 포함)은 해당 리소스에 접근할 수 없다. 

또한 해당 Lock은 다른 트랜잭션이 수행되고 있는 데이터에 대해서는 접근하여 함께 Lock을 설정할 수 없다.
사용법 예시 : select * from ... for update;

## 4. 이러한 락을 구현하는 방법은 여러가지가 있다.
1. 낙관적 lock
- 한 트랙잭션이 현재 값(A)을 특정 값(B)으로 먼저 수정했다고 명시함으로써 다른 트랜잭션이 현재 값(A) 상태를 수정하지 못하도록 막는 방법
- 비관적 락과는 다르게 트랜잭션에 충돌은 없다고 가정하며, 늦게 요청된 트랜잭션은 커밋하는 시점에 안된다는 걸 알고 이후 무시된다.
- DB에서 제공하는 Lock이 아니라 Application loevel에서 제공하는 Lock이다.

![image](https://github.com/HyemIin/TIL/assets/114489245/2237cfc6-d487-4e8b-a57c-03e467d64df2)
- A가 table의 Id 2번을 읽음 ( name = Karol, version = 1 )
- B가 table의 Id 2번을 읽음 ( name = Karol, version = 1 )
- B가 table의 Id 2번, version 1인 row의 값 갱신 ( name = Karol2, version = 2 ) 성공
- A가 table의 Id 2번, version 1인 row의 값 갱신 ( name = Karol1, version = 2 ) 실패
  - Id 2번은 이미 version이 2로 업데이트 되었기 때문에 A는 해당 row를 갱신하지 못함

2. 비관적 lock(2번으로 왜 안바뀜;;)
- 트랜잭션이 시작될 때, Shard Lock 또는 Exclusive Lock을 걸고 시작하는 방법
- 트랜잭션이 무조건 충돌할 것이라는 가정을 전제로, 트랜잭션이 무시되지 않고 대기한다.
- Share Lock을 걸게되면 write하기 위해 Exclusive Lock을 얻어야하는데, 다른 트랙잭션에 의해 shared Lock이 걸려있으면 풀릴 때까지 업데이트 할 수 없다.
- 그러므로 수정하기 위해서는 해당 트랜잭션을 제외한 모든 트랜잭션이 종료되어야 한다.

![image](https://github.com/HyemIin/TIL/assets/114489245/44a329b3-fc1e-4784-a5a6-aeee1f926ba4)
- ransaction_1 에서 table의 Id 2번을 읽음 ( name = Karol )
- Transaction_2 에서 table의 Id 2번을 읽음 ( name = Karol )
- Transaction_2 에서 table의 Id 2번의 name을 Karol2로 변경 요청 ( name = Karol )
  - 하지만 Transaction 1에서 이미 shared Lock을 잡고 있기 때문에 Blocking
- Transaction_1 에서 트랜잭션 해제 (commit)
- Blocking 되어있었던 Transaction_2의 update 요청 정상 처리

## 5. 그럼 언제 뭐 씀??
- 충돌이 많이 예상된다면 비관적 락
- 속도가 중요하다면 낙관적 락

낙관적 락은 사실 트랜잭션을 필요로 하지 않기 때문에, 속도 측면에서 우위에 있음
문제는 롤백할 상황이 생길 경우, 비관적 락은 트랜잭션을 롤백하면 끝나지만.. 낙관적 락은,,,개발자가 한땀한땀 수동으로 처리해줘야함

예를 들면,
- 재고가 1개인 상품이 있다.
- 100만 사용자가 동시적으로 주문을 요청한다.

비관적 락의 경우 1명의 사용자 말고는 대기를 하다가 미리 트랜잭션 충돌 여부를 파악하게 된다. 

즉, 재고가 없음을 미리 알고 복잡한 처리를 하지 않아도 된다. 또한 트랜잭션이 대기하기 때문에 결과가 보장된다. 재고가 20개인 경우, 딱 20개만 소진된다.

낙관적 락의 경우 동시 요청을 보낸 사용자가 처리를 순차적으로 하다가 Commit을 하는 시점에 비로소 재고가 없음을 파악하게 된다. 

그리고 처리한 만큼 롤백도 해야하기 때문에, 자원 소모도 크게 발생하게 된다. 또한 결과도 보장할 수 없다. 

재고가 1개가 아니라 50만개라고 가정할 때, 50만개가 전부 소진되지 않는 이상한(?) 현상이 발생할 수도 있다. 왜냐하면 늦은 트랜잭션이 대기하지 않고 무시되기 때문.

# JPA LOCK
## 1. 낙관적 lock
JPA에서 낙관적 잠금을 사용하기 위해서는 @Version어노테이션을 붙인 필드를 추가하면 간단하게 적용

COPY
@Entity
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberNo;
    //...
    @Version
    private int version;
    //...
}
특정 필드에 @Version이 붙은 필드를 추가하면 자동적으로 낙관적 잠금이 적용. 엔티티에 접근해서 특정 데이터를 변경할 때 version도 함께 증가한다.

일단 실행 후,조회 시점의 버전과 수정 시점의 버전이 다르면 ObjectOptimisticLockingFailureException 예외가 발생.

## 2. 비관적 lock
JPA에서 낙관적 잠금을 사용하기 위해서는 @Lock(LockModeType.PESSIMISTIC_WRITE) 필드를 추가

COPY
public interface UserRepository extends JpaRepository<User, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from User b where b.id = :id")
    User findByIdForUpdate(Long id);
}
PESSIMISTIC_READ
다른 트랜잭션에서 읽기만 가능
PESSIMISTIC_WRITE
다른 트랜잭션에서 읽기도 못하고 쓰기도 못함
PESSIMISTIC_FORCE_INCREMENT
다른 트랜잭션에서 읽기도 못하고 쓰기도 못함 + 추가적으로 버저닝을 수행한다.
