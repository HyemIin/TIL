# 1. Intro

- 서버 사이드 렌더링
- Natural Template - 순수 HTML 유지( vs JSP)
- 스프링 통합 지원

### 타임리프 사용 선언

- HTML에 아래와 같은 선언 추가

```
<html xmlns:th="http://www.thymeleaf.org">
```

# 2. 텍스트-text,utext

- (정의) 가장 기본적인 텍스트 출력을 지원하는 기능
- 일반적으로 타임리프를 쓸 때는 th:text를 활용한다. 단 < b>, </ b>와 같은 태그를 활용하고 싶을 땐, th:utext를 활용한다.

## 1) 사용 방법

(1) HTML에 th 태그를 통해 출력할 텍스트 작성

`<span th:text="${data}">`

(2) Controller에서 model.addattribute를 활용해 뷰에서 보여줄 텍스트 정의

<img width="766" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/e9cbafcd-9af9-4e72-957b-b052fc5b3f8f">

- html 내 th:text가 붙은 태그 안에서 ${data} 라는 부분을 “Hello-Spring”으로 대체한다. 라는 뜻

<img width="724" alt="1" src="https://github.com/HyemIin/TIL/assets/114489245/26c4e3e9-5fde-4ba8-96cf-8ea60f2c47cb">

<img width="669" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/049a32d8-4a51-458f-a0b2-b406b5981367">

이러케 나옴

근데 표현하는 방식이 2개가 있다.

방금처럼 <>태그 내에서 th:text를 선언해서 표현하는방식&HTML 컨텐츠 영역에서 직접 출력하는 방식.

HTML 컨텐츠 영역에서 직접 출력하기 위해선 

<li>컨텐츠 안에서 직접 출력하기 = [[${data}]]</li> 로 작성하면 된다.

## 2) HTML 엔티티

- (정의) 사용자가 작성한 <>태그를 HTML문법에 적용하지 않고 그대로 내보내는 규칙. 이렇게 변경하는 것을 escape라고 한다.
- (why?) HTML은 < 를 HTML태그의 시작으로 인식. 그렇기 때문에 < 기호를 문자로 표현할 수 있는 방법이 필요
- (why not?) 사용자가 글을 작성할 때 정말 다양한 기호를 섞어서 사용하는데, 그중 <>가 들어간 것을 HTML 문법으로 받아들이면 전체 글이 깨져서 안보일 수 있는 위험이 존재.

- **타임리프는 escape 지원이 디폴트값이다.**
- 타임리프에서 escape를 비활성화하기 위해서 사용하는 것이 “th:utext” 이다.

<img width="777" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/33c017ea-041e-4030-9939-434d58556e79">

위와 같이 HTML태그를 넣어서 사용하고 싶을 땐,

<img width="811" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/d1c44870-2b52-4b02-addf-0d111bc31101">

(1) th:utext 태그를 사용하여 unescape를 활성화 한다.

(2) [(${data})]를 활용하여 구분한다. ↔ (기본) [[${data}]]

- 두번째 괄호를 대괄호가 아닌 소괄호로 변경한다.

<img width="623" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/26963c4c-0587-428d-aa17-bb6f766f3672">

바뀐거 확인가능

# 3. SpringEL 표현식

- ${user.username} == ${user.getusername()}  이랑 같은 문법이다. 편의 상 전자를 지원한다.
- `${user['username']}` == ${user.getusername()} 이 것도 같은 문법이다.
<img width="564" alt="image" src="https://github.com/HyemIin/TIL/assets/114489245/49f111f8-4f99-4869-8323-d13cbda1cd0e">

- 위 코드에서 보면, model.addAtribute를 통해 helloData 객체를 뷰에 넘기는 것을 확인 할 수 있다.
<img width="712" alt="image" src="https://github.com/HyemIin/TIL/assets/114489245/de1d119b-800d-4f11-a9d5-9e099a004fc5">

- 그럼 뷰에서는 th:text="${data.name}" 라는 SpringEL 표현식을 활용하여 넘어온 객체의 정보를 나타낼 수 있다.
- 위에 적힌 코드는 hellodata 객체에 작성된 name(정혜민)을 나타내기 위한 식이다.
- 여기서 짚고 넘어갈 것은, 컨트롤러 내 model.Addattribute에 작성한 attributeName과 타임리프 문법인 th:text="${data.name}" 내 data와 이름이 같아야한다는 점이다.

## 1) List에서 활용

- users[0].username == list.get(0).getUsername()
- users[0]['username'] 이것도 됨

## 2) Map에서 활용

- userMap['userA'].username : Map에서 userA를 찾고, username 프로퍼티 접근 → map.get("userA").getUsername()

## 3) th:with를 활용하면 지역변수를 활용할 수 있다

- <div th:with="first=${users[0]}">
<p>처음 사람의 이름은 <span th:text="${first.username}"></span></p>
    - th:with 태그를 활용한 것
    - users라는 리스트 내 0번 인덱스에 저장된 객체(UserA)를 first라는 변수에 담았다.
    - th:text 태그를 통해 fisrt.username ⇒ UserA.getusername()을 가져온 것

# 4. 사용하기 유용한 기본 객체

- HTTP 요청 파라미터 접근: param
예) ${param.paramData} HTTP 세션 접근: session
예) ${session.sessionData}
- 스프링 빈 접근: @
예) ${@helloBean.hello('Spring!')}

# 5. 타임리프로 URL 링크 동적으로 설정하는 법
- 먼저 주의해야할 점은, href 사용 시 a태그를 써야한다는 점이다.
- **간단한 URL 링크 만들기**
- `<li><a th:href=**"@{/hello}"**>basic url</a></li>`
    - 간단하게 URL 파라미터 만들기
- **쿼리 파라미터**
- `<li><a th:href="@{/hello(param1=${param1}, param2=${param2})}">`
    - http://localhost:8080/hello?param1=data1&param2=data2 로 나타남
    - param1,2 는 basicController에서 model.addattribute로 보내준 attributeValue1,2이다.
- **경로 변수**
- `<li><a th:href="@{/hello/{param1}/{param2}(param1=${param1}, param2=${param2})}">`
    - http://localhost:8080/hello/data1/data2 로 나타남
    - attributeValue만 url에 표현하고 싶을 떄
- **경로 변수 + 쿼리 파라미터**
- `<li><a th:href="@{/hello/{param1}(param1=${param1}, param2=${param2})}"`
    - http://localhost:8080/hello/data1?param2=data2

# 6. 리터럴

- 리터럴이란, 소스 코드 상 고정된 값을 말하는 용어(==상수) 이다.
- 예를 들어 String a = “Hello” 라는 것은 문자 리터럴을 의미한다.
- 타임리프를 사용할 때는 항상 작은 따옴표를 이용해서 문자 리터럴을 감싸야한다. 안그러면 오류남.
    - 기본 : <span th:text="'hello'">
    - **단, 이건 너무 귀찮은 일이므로, 공백없이 쭉 작성한다면, 작은 따옴표를 붙이지 않아도 타임리프가 이를 리터럴로 인지한다.**
    - 문제는 hello world 처럼 띄어쓰기가 발생한 경우, 에러가 발생한다는 것이다.
    - 그리고 특수문자붙어도 오류남!!(only 문자,숫자,[],.,-,_의 조합일 경우에만 가능)
        - 오류 발생!!! : <span th:text="hello world!"></span>
        - 이렇게 써줘야한다. <span th:text=**"'hello world!'"**></span>

# 7. 타임리프에서 연산자 사용하는 법

- 사칙 연산은 그냥 써도 결과가 나온다

<img width="632" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/2a7f9644-6a5f-4950-b761-13930df43a55">

- 비교 연산은 꺽쇠를 어떻게 쓸 것인지 주의해서 써야한다.

<img width="693" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/ff90a42f-fe7e-4b6c-afb7-327fbae68810">

# 8. 타임리프 속성 값 설정

- 아래와 같은 코드를 볼 때, name의 mock이라는 것을 th:name이라는 타임리프 속성값 설정으로 통해 userA로 대체할 수 있다.

<img width="639" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/d005ff60-ad90-4507-b4bc-e6a0f6f0c29b">

## 1) 속성 추가하는 법

- 타임리프를 활용하여 html 속성을 동적으로 변경할 수 있다.

<img width="851" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/2f6cdb79-39d3-4b2e-ab70-db8d3a577af1">

위와 같은 코드가 웹을 거치면 아래처럼 변한다.

<img width="605" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/addb0fd6-2503-4333-b28c-ec1ac6c02172">

- attrappend=”class=’ large’”를 보면, class라는 속성 뒤에 “띄어쓰기+large:라는 텍스트가 추가된 것을 확인할 수 있다.
- attrpretend는 반대다.(앞에 붙음)
- classappend는 타임리프가 알아서 붙인다.(이건 좀 사용할 일이 있으니 알아둘 것)

## 2) Checked 처리 방법

- HTML에서는 
<input type="checkbox" name="active" checked="false" /> 
이 경우에도 checked 속성이 있기 때문에 checked 처리가 되어버린다.
- 즉, HTML에서 checked 속성은 checked 속성의 값과 상관없이 checked 라는 속성만 있어도 체크가 된다. 이런 부분이 true , false 값을 주로 사용하는 개발자 입장에서는 불편하다.
- 이런 문제를 해결하기 위해 타임리프를 활용할 수 있다.
<input type="checkbox" name="active" th:checked="false" />
위와 같이 작성해 준다면, 체크박스가 체크되지 않은 채 나타날 것이다.

# 9. 반복

- 타임리프의 코드를 반복하고 싶을 때 사용하는 방법에 대해 알아보자

<img width="590" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/bd7f7228-9aa8-479a-9096-5a56c693a31c">

- th:each라는 것을 사용한다.

<img width="309" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/da658322-c500-4eb3-a9bd-1937cd1738fc">

- th:each라는 반복문을 통해 각 기존 객체 리스트에 담긴 user 객체 A,B,C가 순서대로 타임리프 코드 ${users}에 대입된 후, user라는 변수에 담겨 아래 코드를 반복적으로 수행하는 것을 확인할 수 있다.

## 1) 반복 상태를 유지할 수도 있음

- 반복 상태를 유지하는 법

<img width="621" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/9674802e-2972-4e0e-ad1d-5d540f47ab24">

- 바로바로, 두번째 파라미터를 설정하는 것. 이 그림에서는 userStat이라고 표현되어 있다.
단, 이 두번째 파라미터는 생략가능하며, 기본적으로 1번째 변수명(user)+Stat로 자동 설정되니, 개발자는 숙지하여 사용할 수 있다!!

# 10. 조건부 평가

- th:if 라는 문법을 사용한다.(if not 문을 쓰려면 th:unless 로 쓰면 된다)
- 이 조건문을 충족해야만 해당 HTML 코드가 실행된다.

<img width="741" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/406d4922-96fe-40ed-91e3-a76a8b8af5ac">

- 만약 user.age를 조회했을 때 해당 객체의 user.age가 20 미만(lt)이라면, 미성년자라는 글씨를 띄운다.
- 또한 만약 user.age를 조회했을 때 해당 객체의 user.age가 20이상(ge)이 아니라면, 미성년자라는 글씨를 띄운다.
- 아래는 그 결과다.

<img width="448" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/6f9446c6-549d-44de-b2f6-4793638a5eeb">

- BasicController에서 user 객체별 이름과 나이를 아래와 같이 지정했다. 당연히 UserA외 모두 20살 이상이므로, “미성년자”라는 워딩은 UserA에서만 나오게 된다.

<img width="618" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/eadd4095-cac1-40cd-abd3-cfd69ecc7c6c">

## 1) Switch,case로도 활용 가능하다.

<img width="627" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/28a5823a-4128-4e26-809f-058959df7fc1">

# 11. th:Block = 블록에 대해 알아보자

- 묶음으로 th 태그를 적용하고 싶을 때 사용하는 태그이다.

<img width="630" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/93ba8be4-8813-4a26-adab-00a689a75e86">

- 위 코드를 보면, div로 태깅된 2개의 블록이 보인다. 이 2개의 div에 공통적으로 th:each를 적용하고자 할 때, <th :block> 태그로 감싸주면 된다.
- 만약 하나의 블록에만 th태그를 적용할 것이라면, <div th:each~~> 식으로 쓰면 된다.

# 12. 타임리프 안에서 자바스크립트를 쓰고자 할 때

- 타임리프는 자바스크립트에서 타임리프를 편리하게 사용할 수 있는 자바스크립트 인라인 기능을 제공한다.
자바스크립트 인라인 기능은 다음과 같이 적용하면 된다.

```
<script th:inline="javascript">
```

<img width="648" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/ae6756a4-b607-409a-af71-908fd31e8503">

<img width="521" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/3bbb818c-1b85-4eca-bef5-e0d82a02de15">

- <script th:inline="javascript">을 사용한다면, 아래와 같은 이점을 얻을 수 있다.
    - 변수에 저장되는 문자,숫자를 구분해준다(문자는 쌍따옴표 처리 등)
    - 변수에 저장되는 객체를 처리하기 쉽도록 json 형식으로 변환시켜준다.
    - 주석 부분을 인지하고 주석에 해당하는 부분을 자동으로 렌더링해준다.(오 주석을 무시하는게 아니라,,)
- 또한 자바스크립트 인라인은 th:each도 허용한다.
    - [# th:each~] 처럼 해시태그를 넣어주는 것이 핵심이다.

<img width="477" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/e832ea5a-e45d-46de-abbd-56db6d13d6e6">

- 아래와 같은 결과가 나온다.

<img width="373" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/a896e4e8-561e-4b40-a1fd-8c29a747d713">

# 12. 템플릿 조각

- (배경) 웹 페이지 개발 시 웹 페이지 내 공통 영역이 존재하는데, 공통 영역 코드를 매번 복사 붙여넣는 것은 확장성 측면에서 불편하다.
- (Why) 이런 문제를 해결하기 위해 템플릿 조각이 등장했다.
- 템플릿 조각을 활용한다면, 웹 페이지 중 특정 태그를 다른 html 파일 내 태그로 대체할 수 있다.

<img width="590" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/d30d0259-1bc2-4688-91ef-d33d20ac0f0d">

- 방법은 insert,replace가 있다.
- 경로 :: footer name을 적어주면 해당 부분이 대체된다.
    - <footer> 태그 전체가 들어오는 것을 확인할 수 있다.

<img width="375" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/661d51f4-62ef-4444-9452-aff84467da0b">

- 파라미터를 넣을 수도 있다.
    - copyParam이라는 footer가 param1,2를 매개변수로 받는 footer이다. 그리고 각각 param1,2를 화면에 출력하는 footer인 것을 가정했다.

<img width="603" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/bb78a93d-bdcb-4ba9-b301-a4710fa69a89">

- 이를 활용하기 위해선, 아래와 같이 템플릿 조각 문법을 지키면서 파라미터를 “데이터1”,”데이터2”로 설정하여 활용할 수 있다.

<img width="796" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/f0e1ff0f-1a4a-409b-ac72-feb405d97703">

결과

<img width="532" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/b07d3d09-a7ae-4469-bf24-b9585d9473f8">

# 13. 템플릿 레이아웃

- 템플릿 조각은 일부 코드 조각을 가져와서 사용했다면, 이 방법은 코드 조각을 레이아웃에 넘겨서 사용하는 방법이다.
- (why) 공통으로 사용하는 부분은 그대로 유지하되, 각 페이지마다 필요한 정보를 추가해서 사용하고 싶을 때 활용하는 방법이다.

**[layoutmain.html]**
<img width="695" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/1be39e7b-adee-4ad8-8c94-c56e067e3589">

- 위 코드를 봐보자.
- 나는 base.html에 있는 공통 부분을 layoutMain.html 코드에 포함시키고자 한다.
- 빨간색 박스에서 보면, 마지막에 ~{::title},~{::link} 라는 코드가 있는데,  태그에 내가 옮기고자하는 title태그와 link태그를 담은 후, common_header라는 th:fragment 를 가진 코드(base.html)에 적용하고 불러온다는 뜻이다.

**[base.html]**
<img width="750" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/830460b7-6d1d-4e1a-8a99-f8c608bde187">

- 위 코드는 앞서 말한 common_header라는 th:fragment를 가진 html 코드이다.
- common_header는 매개변수로 title,links를 가진다고 설정해놨기 때문에, 앞서 담았던 title과 link를 그대로 적용할 수 있는 기반을 갖췄다.
- base.html은 모든 html이 공통적으로 사용할 태그(css,js 코드 등)를 모아둔 코드이므로, 이를 layoutMain.html에 적용하고자한다.
    - 단, 앞서 말한대로 layoutMain.html에 있던 title과 link 태그를 빠뜨리면 안된다.
- 이미 layoutMain.html에서 th:replace를 통해 base.html을 불렀으므로, layoutMain.html을 웹페이지에 띄운 모습을 확인해보자.

- 이를 실제 웹페이지에서 보자
http://localhost:8080/template/layout
위 url은 layoutmain.html을 띄우는 url이다.

<img width="780" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/60f459b1-d1b6-4dd9-97cd-a5212160f0eb">

- title 태그를 보면, 기존에 common_header 매개변수로 담았던 title이 그대로 적용됐다.
- base.html에 있던 공통 link 태그는 그대로 나오면서
- common_header의 link 매개변수에 담았던 link태그가 <추가> 파트에 추가되어 출력되는 것을 확인할 수 있다.

즉, 공통 태그(base.html)을 불러오면서, 자신만의 개별 태그 또한 함께 적용할 때 이런 방법을 사용한다.

- 정리하자면,
    - layoutMain.html에서 th:replace를 통해 공통 태그를 첨가하고자 함
    - 단, 매개변수를 통해 본인이 원래 갖고 있던 태그 중 필요한 태그를 담음
    - base.html에 위에서 담은 태그가 추가되서, 최종본이 layoutMain.html에 띄워짐.
    - layoutMain.html은 기존 매개변수에 담은 태그랑 합쳐진 base.html을 replace하여 웹에 띄움

## 1) 템플릿을 HTML 전체에 적용

- 사실 위에서 했던거..HTML 전체에 공통적으로 적용할 수 있다.
- 만약 100개의 HTML 파일이 있는데, title은 각 페이지마다 다르고, footer가 모두 동일해야하는 상황이라 가정해보자.

<img width="709" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/408a1b06-00dd-4257-a239-519fb22428c8">

- 과정은 위랑 똑같긴 함
- 맨 위 <html th:fragment= “layout(title,content)” 이부분 보면, footer는 따로 매개변수로 받지 않음. 즉 footer는 위 코드에 적용된 footer로 고정하겠다는 뜻이다.
- 그럼 변하는 건 title과 content 파트라고 할 수 있다.


# 1. 타임리프 스프링 통합

- 스프링EL 문법 통합
- 스프링 빈 호출 지원(타임리프 문법에서 @빈이름으로 부를 수 있음)
- 스프링 메세지, 국제화 기능의 편리한 통합(국제화 : 접속 국가에 따라 언어 변경 기능)
- 스프링 검증, 오류 처리 통합
- 스프링 변환 서비스 통합

## 1) 타임리프 - 스프링 적용 방법

- 사실 이건 굉장히 쉽다.. 우리도 알고 있듯,, dependecy에 의존성 추가만 해주면 된다.
`implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'`
- 좀 더 자세히 설명하자면, 위 의존성 추가를 통해 타임리프 템플릿 엔진을 스프링 빈에 등록하고, 타임리프용 뷰 리졸버를 스프링 빈으로 등록하는 것까지 자동으로 된다는 의미다.

## 2) th:field 란?

<img width="893" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/065505d0-3cc2-4678-9875-5b1f955e67d8">

- (what?) HTML 태그의 id , name , value 속성을 자동으로 처리해주는 태그이다.
(여러 속성을 하나로 묶어주는 ~~퉁치는~~ 좋은 태그)
- 먼저, th:object=”${item}” 을 설정하면, 컨트롤러에서 넘긴 item 객체가 타임리프 코드 안으로(object) 넘어온다.
- 이후 div 태그 내(바디 역할을 하는,,) th:field=”*{itemName}” 으로 작성한다면, 기존에 있던
id=”itemName” name=”itemName” 을 쓰지 않아도 자동으로 렌더링 해준다.
- 타임리프가 th:object에 담긴 객체 이름을 통해 id와 name을 자동 인식할 수 있기 때문이다.

<img width="791" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/b88adab9-fb6d-41bb-bc3b-c1bad547d50b">

- 위 결과 소스코드를 보면, name을 지웠어도 웹페이지 소스코드에는 뜨는 것을 알 수 있다.
- (why?) field 설정을 함으로써 id,name, 객체가 가진 값(value)까지 한번에 출력할 수 있는 편리함을 제공한다.
- 보다 명확히 효과를 확인하기 위해 아래 소스 코드를 가져왔다.

<img width="829" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/518eab06-2c7a-4c1d-8e1d-66b923e9f849">

- 위 코드는 th:field 를 쓴 코드이고, 아래 코드는 안쓴 코드이다. 확연히 위 코드가 간결한 것을 알 수 있다.
아래 코드의 name, value,th:value 코드가 모두 사라졌기 때문이다.
~~(사실 id도 지워도 되는데, 지우면 인텔리제이에서 빨간색 표시 넣어서 보기 싫어서 냅둠)~~
- 또한!! th:field는 더 많은 일을 대신해준다.
- 만약 checkbox타입에 th:field를 적용할 경우, 자동으로 히든타입과 name을 적용시켜 준다.

<img width="763" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/9ab81d69-aaef-4aab-97ae-3a9a01c45ae1">

<img width="899" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/607e305e-efab-4eda-ad76-b2fea2cd34d7">

# 2. @ModelAttribute

- 컨트롤러가 호출되서 모델을 사용할 일이 있으면, 무조건 특정 값이 담기도록 할 수 있는 어노테이션
- 즉, 매번 GetMapping만들어서 model.adattribute(~~) 하지 않고, 공통으로 넣을 것이 있으면 이 어노테이션을 쓰면 된다.

- 기존 작성

<img width="635" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/508df262-5ad8-41c0-a5cf-87046e89c373">

edit 뿐 아니라 add,상세화면 등에도 같은 내용이 들어가 줘야함.

- @ModelAttribute 어노테이션 활용 후

<img width="664" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/8d059de7-8b0c-4844-86d0-fc19e3f9a7c0">

이거 하나 있으면 매번 getMapping에 추가하지 않아도 자동으로 해당 데이터가 들어가는 것을 보장할 수 있음.

- 어떤 원리냐면, 매번 getMapping에서 호출하는 것을 묶었다고 생각하면 됨.
model.addAttribute(”regions”,regions) 가 컨트롤러 내 모든 메서드에 자동으로 들어감

# 3. 체크박스-멀티

<img width="537" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/c509c351-3e21-4c0a-9e19-48dc4ce41575">


- 위 사진 처럼 멀티 체크박스를 만들 수 있는 방법에 대해 알아보자.

<img width="864" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/afbba40d-6bd3-45f7-b799-41d2806e93fd">

- 1) 컨트롤러에 @modelattribute 추가해서 뿌릴 데이터 정의할 것
- 2) th:each로 region을 불러온다. (아까 만든 model.attribute)
- 3) th:value : 보면, 아까 모델 어트리뷰트에 만든 서울,부산,제주 key 값을 의미한다.
th:field에 있는 값은 item.java에 있는 regions에 담기는 List 값이다.
- 4) 주의해야할 문제가 있는데, 반복문으로 체크박스를 생성하지만, 각각 체크박스 별로 id는 달라야한다.
th:field가 있기 때문에 id를 개발자가 굳이 지정하지 않고도 자동으로 반복문 돌때마다 번호를 붙여주긴 하는데, 문제는 번호만 붙여주지, id 이름이 뭔지는 개발자가 지정해줘야하는 것이다.
그렇기때문에 th:for=””${#ids.prev(’원하는 아이디 이름’)} 라는 태그를 통해, 아이디 이름을 지정해준다


- 위 코드는 th:field 를 쓴 코드이고, 아래 코드는 안쓴 코드이다. 확연히 위 코드가 간결한 것을 알 수 있다.
아래 코드의 name, value,th:value 코드가 모두 사라졌기 때문이다.
~~(사실 id도 지워도 되는데, 지우면 인텔리제이에서 빨간색 표시 넣어서 보기 싫어서 냅둠)~~
