# JPA 소개
- EntityManagerFactory 필수 - DB당 1개씩 묶여서 돌아감
- EntityManager 필수 - 고객의 요청을 처리하는 객체
- 모든 요청은 트랜잭션 안에서 실행

- 순서
    - 트랜잭션.begin()
    - 객체 생성 및 값 set → SQL 쿼리 날림
    - 트랜잭션 commit()
    - EntityManager.close()
    - [WAS가 내려갈때 닫아줌] EntityManagerFactory.close()
 
# 1. 영속성 컨텍스트(EntityManager)

- (정의) 엔티티를 영구 저장하는 환경 (논리적 개념. 실재하는 것은 아님)
- EntityManager.persist(entity);
    - entity를 영속화한다는 의미의 메서드 (DB에 저장하는 것처럼 보이지만, DB에 저장하는 작업이 아님)
- (why) 영속성 컨텍스트가 주는 장점은?
    - **1차 캐시에서 조회 이점**
        - em.find 메서드 호출 시 바로 DB를 조회해서 데이터를 찾는 것이 아니라,1차 캐시를 먼저 조회해서 데이터를 찾음
        - 만약 1차 캐시에 없으면 DB를 조회하고, 한번 조회한 데이터는 1차 캐시에 저장해놓는다.
