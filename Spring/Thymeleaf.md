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

## 1) 사용 방법

(1) HTML에 th 태그를 통해 출력할 텍스트 작성

`<span th:text="${data}">`

(2) Controller에서 model.addattribute를 활용해 뷰에서 보여줄 텍스트 정의

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/088898f8-cc81-47b4-89f3-86a7493e094b/Untitled.png)

- html 내 th:text가 붙은 태그 안에서 ${data} 라는 부분을 “Hello-Spring”으로 대체한다. 라는 뜻

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/44dcbd63-e531-4efa-afd6-62e4f2fc758d/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/84cf8b7c-02a9-49d3-bc08-071042eac0ea/Untitled.png)

이러케 나옴

근데 표현하는 방식이 2개가 있다.

방금처럼 <>태그 내에서 th:text를 선언해서 표현하는방식&HTML 컨텐츠 영역에서 직접 출력하는 방식.

HTML 컨텐츠 영역에서 직접 출력하기 위해선 

<li>컨텐츠 안에서 직접 출력하기 = [[${data}]]</li> 로 작성하면 된다.

## 2) HTML 엔티티

- (정의) 사용자가 작성한 <>태그를 HTML문법에 적용하지 않고 그대로 내보내는 규칙
이렇게 변경하는 것을 escape라고 한다.
- (why?) HTML은 < 를 HTML태그의 시작으로 인식. 그렇기 때문에 < 를 문자로 표현할 수 있는 방법이 필요
- (why not?) 사용자가 글을 작성할 때 정말 다양한 기호를 섞어서 사용하는데, 그중 <>가 들어간 것을 HTML 문법으로 받아들이면 전체 글이 깨져서 안보일 수 있는 위험이 존재.

- **타임리프는 escape 지원이 디폴트값이다.**
- 타임리프에서 escape를 비활성화하기 위해서 사용하는 것이 “th:utext” 이다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0216ece2-a455-495e-9a9c-81d7471fc1bf/Untitled.png)

위와 같이 HTML태그를 넣어서 사용하고 싶을 땐,

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9027d690-156a-4aab-9cd2-7bf62bd0a81d/Untitled.png)

(1) th:utext 태그를 사용하여 unescape를 활성화 한다.

(2) [(${data})]를 활용하여 구분한다. ↔ (기본) [[${data}]]

- 두번째 괄호를 대괄호가 아닌 소괄호로 변경한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4daec229-b3c9-4cf7-8297-a7a91652d63f/Untitled.png)

바뀐거 확인가능

# 3. SpringEL 표현식

- ${user.username} == ${user.getusername()}  이랑 같은 문법이다. 편의 상 전자를 지원한다.
- `${user['username']}` == ${user.getusername()} 이 것도 같은 문법이다.

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
        - 오류 발생!!! : <span th:text="hello world!"></span>
        - 이렇게 써줘야한다. <span th:text=**"'hello world!'"**></span>

# 7. 타임리프에서 연산자 사용하는 법

- 사칙 연산은 그냥 써도 결과가 나온다

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/091457d8-926f-457d-a2e0-0a55484472d9/Untitled.png)

- 비교 연산은 꺽쇠를 어떻게 쓸 것인지 주의해서 써야한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cbfb0830-5f73-47c7-a37d-995949269827/Untitled.png)

# 8. 타임리프 속성 값 설정

- 아래와 같은 코드를 볼 때, name의 mock이라는 것을 th:name이라는 타임리프 속성값 설정으로 통해 userA로 대체할 수 있다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/92389227-952d-4368-a47f-394554e42db3/Untitled.png)

## 1) 속성 추가하는 법

- 타임리프를 활용하여 html 속성을 동적으로 변경할 수 있다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/74965d50-2dfd-4ebd-9acb-989dfc3fb1e2/Untitled.png)

위와 같은 코드가 웹을 거치면 아래처럼 변한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/24ed211a-1ba6-47bf-88d4-aafe390268c9/Untitled.png)

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

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e8c20bd5-9f40-4f94-92a0-a5e7e765630e/Untitled.png)

- th:each라는 것을 사용한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9fffb576-84cd-4135-851b-1be1ab27985c/Untitled.png)

- th:each라는 반복문을 통해 각 기존 객체 리스트에 담긴 user 객체 A,B,C가 순서대로 타임리프 코드 ${users}에 대입된 후, user라는 변수에 담겨 아래 코드를 반복적으로 수행하는 것을 확인할 수 있다.

## 1) 반복 상태를 유지할 수도 있음

- 반복 상태를 유지하는 법

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2a508455-ca1e-47b3-b8ec-7df46fb488a8/Untitled.png)

- 바로바로, 두번째 파라미터를 설정하는 것. 이 그림에서는 userStat이라고 표현되어 있다.
단, 이 두번째 파라미터는 생략가능하며, 기본적으로 1번째 변수명(user)+Stat로 자동 설정되니, 개발자는 숙지하여 사용할 수 있다!!

# 10. 조건부 평가

- th:if 라는 문법을 사용한다.(if not 문을 쓰려면 th:unless 로 쓰면 된다)
- 이 조건문을 충족해야만 해당 HTML 코드가 실행된다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/43a3ea0d-9c51-42cf-81db-cdb7b09dcc7f/Untitled.png)

- 만약 user.age를 조회했을 때 해당 객체의 user.age가 20 미만(lt)이라면, 미성년자라는 글씨를 띄운다.
- 또한 만약 user.age를 조회했을 때 해당 객체의 user.age가 20이상(ge)이 아니라면, 미성년자라는 글씨를 띄운다.
- 아래는 그 결과다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c17d0a05-7e3e-460c-9237-7c4e22c5764f/Untitled.png)

- BasicController에서 user 객체별 이름과 나이를 아래와 같이 지정했다. 당연히 UserA외 모두 20살 이상이므로, “미성년자”라는 워딩은 UserA에서만 나오게 된다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f3051a17-a986-46c9-86b2-9a4a174c8a5b/Untitled.png)

## 1) Switch,case로도 활용 가능하다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/807b2ffe-96a2-4f7d-b685-ea2daf25b579/Untitled.png)

# 11. th:Block = 블록에 대해 알아보자

- 묶음으로 th 태그를 적용하고 싶을 때 사용하는 태그이다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b5cfc32a-b718-426f-bb15-a81b5fbaddd0/Untitled.png)

- 위 코드를 보면, div로 태깅된 2개의 블록이 보인다. 이 2개의 div에 공통적으로 th:each를 적용하고자 할 때, <th :block> 태그로 감싸주면 된다.
- 만약 하나의 블록에만 th태그를 적용할 것이라면, <div th:each~~> 식으로 쓰면 된다.

# 12. 타임리프 안에서 자바스크립트를 쓰고자 할 때

- 타임리프는 자바스크립트에서 타임리프를 편리하게 사용할 수 있는 자바스크립트 인라인 기능을 제공한다.
자바스크립트 인라인 기능은 다음과 같이 적용하면 된다.

```
<script th:inline="javascript">
```

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1b2124bb-aa98-4dd9-9557-b221a5f01a01/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d18dfc31-4e48-4fa8-8e97-5ac492fe70a9/Untitled.png)

- <script th:inline="javascript">을 사용한다면, 아래와 같은 이점을 얻을 수 있다.
    - 변수에 저장되는 문자,숫자를 구분해준다(문자는 쌍따옴표 처리 등)
    - 변수에 저장되는 객체를 처리하기 쉽도록 json 형식으로 변환시켜준다.
    - 주석 부분을 인지하고 주석에 해당하는 부분을 자동으로 렌더링해준다.(오 주석을 무시하는게 아니라,,)
- 또한 자바스크립트 인라인은 th:each도 허용한다.
    - [# th:each~] 처럼 해시태그를 넣어주는 것이 핵심이다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/32e249d7-d0b4-403f-a48e-3b8ee2eaccb7/Untitled.png)

- 아래와 같은 결과가 나온다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/77613a2b-cd5c-4541-aa81-f6dae80bff1c/Untitled.png)

# 12. 템플릿 조각

- (배경) 웹 페이지 개발 시 웹 페이지 내 공통 영역이 존재하는데, 공통 영역 코드를 매번 복사 붙여넣는 것은 확장성 측면에서 불편하다.
- (Why) 이런 문제를 해결하기 위해 템플릿 조각이 등장했다.
- 템플릿 조각을 활용한다면, 웹 페이지 중 특정 태그를 다른 html 파일 내 태그로 대체할 수 있다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9c8574e3-2203-44c8-9783-40a3c4c658bd/Untitled.png)

- 방법은 insert,replace가 있다.
- 경로 :: footer name을 적어주면 해당 부분이 대체된다.
    - <footer> 태그 전체가 들어오는 것을 확인할 수 있다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/814330d8-7d5c-4d59-9a8a-6d1c9c484823/Untitled.png)

- 파라미터를 넣을 수도 있다.
    - copyParam이라는 footer가 param1,2를 매개변수로 받는 footer이다. 그리고 각각 param1,2를 화면에 출력하는 footer인 것을 가정했다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2e8f6f8a-25e5-415f-a7fa-4dab6d5fce59/Untitled.png)

- 이를 활용하기 위해선, 아래와 같이 템플릿 조각 문법을 지키면서 파라미터를 “데이터1”,”데이터2”로 설정하여 활용할 수 있다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/08e558bd-8242-411a-9139-2425c918fcf9/Untitled.png)

결과

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/906b9df3-7be2-4dc9-926f-408c9e2ac100/Untitled.png)

# 13. 템플릿 레이아웃

- 템플릿 조각은 일부 코드 조각을 가져와서 사용했다면, 이 방법은 코드 조각을 레이아웃에 넘겨서 사용하는 방법이다.
- (why) 공통으로 사용하는 부분은 그대로 유지하되, 각 페이지마다 필요한 정보를 추가해서 사용하고 싶을 때 활용하는 방법이다.

**[layoutmain.html]**

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/bee602fe-7b81-42a0-a85b-24eaaaf0d188/Untitled.png)

- 위 코드를 봐보자.
- 나는 base.html에 있는 공통 부분을 layoutMain.html 코드에 포함시키고자 한다.
- 빨간색 박스에서 보면, 마지막에 ~{::title},~{::link} 라는 코드가 있는데,  태그에 내가 옮기고자하는 title태그와 link태그를 담은 후, common_header라는 th:fragment 를 가진 코드(base.html)에 적용하고 불러온다는 뜻이다.

**[base.html]**

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/8b1c5767-54b7-4c77-a774-27f8ff196e86/Untitled.png)

- 위 코드는 앞서 말한 common_header라는 th:fragment를 가진 html 코드이다.
- common_header는 매개변수로 title,links를 가진다고 설정해놨기 때문에, 앞서 담았던 title과 link를 그대로 적용할 수 있는 기반을 갖췄다.
- base.html은 모든 html이 공통적으로 사용할 태그(css,js 코드 등)를 모아둔 코드이므로, 이를 layoutMain.html에 적용하고자한다.
    - 단, 앞서 말한대로 layoutMain.html에 있던 title과 link 태그를 빠뜨리면 안된다.
- 이미 layoutMain.html에서 th:replace를 통해 base.html을 불렀으므로, layoutMain.html을 웹페이지에 띄운 모습을 확인해보자.

- 이를 실제 웹페이지에서 보자
http://localhost:8080/template/layout
위 url은 layoutmain.html을 띄우는 url이다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/51b612ad-0f8f-4a3f-9436-3dbb57d7b48f/Untitled.png)

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

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/57465dda-d09b-4ad9-b02a-608dc46bfc21/Untitled.png)

- 과정은 위랑 똑같긴 함
- 맨 위 <html th:fragment= “layout(title,content)” 이부분 보면, footer는 따로 매개변수로 받지 않음. 즉 footer는 위 코드에 적용된 footer로 고정하겠다는 뜻이다.
- 그럼 변하는 건 title과 content 파트라고 할 수 있다.
