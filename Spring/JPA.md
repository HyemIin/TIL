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
