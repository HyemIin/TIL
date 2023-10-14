# 1. 기본 개념

- **why github actions?**
    - 깃허브에서 서버를 제공하기 때문에 CI/CD를 위한 서버를 따로 구축하지 않아도 된다.
    - 즉, 부담이 적고 간편하다.
    
    <img width="600" alt="image" src="https://github.com/HyemIin/TIL/assets/114489245/5488ad64-6bde-4a5b-84f4-bcbb7fc18718">

    

## 1) Events

- githun actions 실행하는 트리거(특정 이벤트,행동)
    - ex . 누군가 새로운 이슈를 만들경우, 커밋할 경우 등

## 2) workflows

- 이벤트가 발생했을 때, 어떤 작업(job)을 수행할 것인지 명시
- 작업들의 자동화된 프로세스를 의미
- 1개 이상의 Job으로 구성

## 3) Jobs

- workflow에서 실행하는 작업 단위
- 스크립트 형태로 작성
- 하나의 Ruuner에서 하나의 Job이 실행 (runner = 컴퓨터.vm)
- 깃허브 액션은Job을 매번 새로운 서버에서 실행하기 때문에, Job마다 프로젝트에 의존하는 라이브러리를 새로 받아줘야한다. 그렇기 때문에 깃허브에서 제공하는 캐시에 라이브러리 정보를 저장하고 재사용하는 것이 CI 성능에 좋다.

## 4) Actions

- 웬만한 명령어를 수행할 수 있는 명령 집합
- Action을 실행할 때는 uses 키워드를 사용하며 {소유자}/{저장소명}@{참조자}의 형태로 사용
- 참조자는 보통 Action의 버전을 구분하기 위해 사용
    - 위의 예시에서 v3 참조자는 Action의 버전 태그를 의미

## 5) Runners

- 각각의 Job들은 runner라는 vm 컨테이너에서 실행
    
    <img width="604" alt="image" src="https://github.com/HyemIin/TIL/assets/114489245/00d969f1-f72f-418b-88b6-82588b749a55">

    

# 2. How To Use?

- .github/workflows/xxx.yml 경로로 파일을 지정
- 기본적으로 깃허브 액션은 yml 문법을 사용한다
