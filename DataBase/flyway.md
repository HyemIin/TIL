# flyway

- (정의) 데이터베이스 마이그레이션 툴 (오픈소스)
  - 마이그레이션 : 한 DB에서 다른 DB로 이동을 의미하나, 여기선DB에서 진행하는 모든 동작을의미.
- 소스코드의 깃허브와 동일한 작업이라 생각하면 편하다.
- 데이터베이스를 이관할 때 사용할 수 있음. 배포 이후에 많이 활용한다.

## 1. 이거 왜 씀??
- 나와 같은 주니어(난 주니어 레벨도 안되지만) 취준 개발자 입장에선 일단 서비스를 만드는거 자체에 집중하는 경향이 큰데, flyway는 그 이후를 봐야할 때 빛을 보는 tool 이다.<br>
프로젝트에서는 local,개발,배포 환경의 DB가 다를 수 있는데, Flyway는 런타임 시 자동으로 실행되기 때문에 실수 여지를 줄여준다.
- 즉, 쉽게 DB 환경을 다르게 가져갈 수 있어 편리하다.
  
- 예를 들어 local,개발환경은 seed를 넣고, 운영환경에서는 안넣을 수 있음 <br>
  또한 일일이 사람이 버전관리하는 것이 아니기 때문에 휴먼 에러 방지에도 큰 역할을 한다.이처럼, 서비스 개발 이후 DB 파트에서 유지 보수 및 버전 관리가 필요할 때 활용한다.

- 예시를 같이 보자.
  ```java
  @Entity
  public class RealComplexableEntity {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String username;
      //        ... 그외 여러가지
  }
  ```

위처럼 몹시 complexable한 엔티티가 이미 배포되어 있는 상태에서, 만약 nickname이라는 필드를 추가해야한다면??

```java
@Entity
  public class RealComplexableEntity {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String username;
      //        ... 그외 여러가지
      private String nickname;
  }
```
- 알다시피, 배포 서버DB를 직접 조작하는 건 좋은 방법이 아니다.(일단 귀찮음 + 휴먼 에러 가능성)

이런 상황을 방지하기 위해, 우리는 DB변경사항만 입력하면 자동으로 버전 관리와 업데이트를 지원하는 flyway tool을 활용하는 것이다.

## 2. Flyway 특징
### Migrate
- 데이터베이스 변경을 담당하는 작업이다.
sql파일을 다른 DB에 전달하면, 해당 DB에서 sql 실행 및 histroy 테이블을 생성한다.
파일 위치는 resources/db/migration 밑에 작성해야 시스템이 인식할 수 있다.
시작 시, init 파일 추가 : resources/db/migration/V1__init.sql
마이그레이션 파일 이름 규칙
버전 새로 적용되는 파일은 기존 파일보다 버전이 높아야한다.
버전이 낮으면 적용이 안된다.
파일 명명 규칙은 다음와 같다. == {숫자}__{설명}. sql
ex) V1_init.sql -> V1.1_init.sql..
버전 숫자는 단순 숫자의 오름차순으로 구성해도 되지만, 버전이 상세하게 나눠질 경우 관리가 어려울 수 있기 때문에 날짜를 붙이는 경우도 있다.

### Baseline
비어있지 않은 데이터베이스에서 flyway를 적용하는 방법이다.
address라는 테이블을 생성하려면, v1_baseline 만들고, v2_address.sql 만들어서 적용한다.
history테이블 생성

### Info
DB 변경이력을 저장하는 방법이다.
flyway를 함으로써 만들어진 history 파일에서 중요하게 봐야할 건 2가지가 있다. version & checksum
지금까지 작성된 version을 확인하고,기존 version 보다 높은 버전으로 작성하여 진행해야한다.
기존 버전의 DB 변경이 될 시 checksum의 해시값이 훼손되서 해당 버전을 사용할 수 없도록 설정되어 있다.. 그러므로 무조건 새버전 만들어서 사용해야한다.

### Repair
실패한 migration파일을 복구하는 작업이다.

### Validate
flyway 적용 시 DB에 적용가능한지 확인하는 작업이다.

### Undo
되돌리기

### Clean
DB 클리너라고 생각하면 된다.
3. 그러면 어떻게 써야함??
- 의존성 추가 (gradle 기준)
```java
  dependencies {
      implementation('org.flywaydb:flyway-mysql')
  }
```

- yml파일 추가

```java
  #data source 설정/DB에 맞게 설정
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  spring.datasource.url=jdbc:mysql://localhost:13306/flyway
  spring.datasource.username=yourusername
  spring.datasource.password=yourpassword

  #flyway 설정
  spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
  spring.flyway.enabled=true
  spring.jpa.generate-ddl=false
```
