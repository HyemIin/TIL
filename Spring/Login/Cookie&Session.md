# 2. 내가 만든 첫번째 쿠키(보안 상 문제 있는 기본 설명)

- (배경) 로그인 상태를 유지한 채로 서비스를 사용해야하는데, 계속 다른 웹페이지로 이동하면 로그인 정보가 저장되지 않아 서비스 이용이 불편하다.
- (정의) 이런 로그인 상태를 유지하기 위한 방법으로 쿠키를 사용한다.
- **(보안 문제)**
    - **위변조가 겁나 쉬움. 클라이언트가 개발자모드에서 쿠키 value를 강제로 변경가능.(다른 사용자 계정에 맘대로 접속 가능하다는 것)**
    - **쿠키 정보를 누군가 훔쳐갈 수 있음(웹 브라우저에 정보가 보관되고, 네트워크 요청마다 계속 클라이언트에서 서버로 요청되기 때문에 local PC가 털리거나, 네트워크 구간에서 털릴수도..)**
- **(대안)**
    - **쿠키에 중요한 값을 일단 노출하지 말 것**
    - **사용자별로 예측 불가능한 토큰을 노출할 것(JWT?세션?)**
    - **토큰과 사용자가 연계된 정보를 서버가 관리할 것**
    - **토큰 유지시간은 짧게 유지**

### 1) 쿠키 굽는법

- (일단, 보안상 문제 있는 방법)
    - 서버에서 로그인을 성공하면, HTTP 응답에 쿠키를 담아서 브라우저에 전달할 것.
    - 브라우저는 앞으로 해당 쿠키를 지속해서 보내준다.
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/5adb8fa7-1ca9-4a87-829e-f6520debc246/Untitled.png)
    
    - 1) 브라우저에서 사용자가 로그인 정보를 입력한다.
        - 로그인 정보 : id,password
    - 2) 서버는 로그인 정보를 확인하고, 아이디에 해당하는 memberId를 쿠키에 담아 브라우저에게 반환한다.
    - 3) 브라우저는 받은 쿠키를 쿠키 저장소에 저장한다.
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/240ac47d-0135-4b15-9a1c-318e2d1415b7/Untitled.png)
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/96b958e4-55dc-4aeb-a169-ef7e09843978/Untitled.png)
    
    - 보면, 로그인 후 실제로 쿠키에 memberId가 담겨서 넘어간 것을 확인할 수 있다.
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/12884c24-4158-4ab7-a98a-2c085d62f967/Untitled.png)
    
    - redirect를 하면, 쿠키가 남아있는걸 볼 수 있다.
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d3f9db4f-2b8e-4b2e-88bd-e7af716ce5b0/Untitled.png)
    
    - 여기서도 확인 가능.(이 사람 로그인 정보가 쿠키에 담겼구나!!)
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f257c442-6a3d-4300-a1b0-39247a88e953/Untitled.png)
    
    - 이제 어떤 페이지를 요청하든 도메인안에서는 쿠키 정보를 함께 보내줌
    
    ### 2) 로그인 코드로 어케 구현함??
    
    **[LoginController]**
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/855fce0a-aec4-44db-ba9a-42332ed50c1c/Untitled.png)
    
    - LoginController 내 login 메서드에서 쿠키 관련 코드를 추가한다.
    - 이때 Cookie Class를 사용한다.
    - cookie 객체를 생성하여 쿠키이름, 어떤 값을 전달할지 파라미터로 설정한다.
    - HttpServletResponse를 이용하여 쿠키를 브라우저에게 반환한다.
    
    **[HomeController]**
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f251cbbb-ea74-4da0-a7f9-4e235f0b4b59/Untitled.png)
    
    - homeController 내 login-cookie관련 메서드를 추가한다.
    - @CookieValue를 사용하면 편리하게 쿠키를 조회할 수 있다.
    - 비로그인 사용자의 접근을 막기 위해 required=false를 사용한다.
    - 로그인 쿠키가 없는 사용자(memberId = null)는 home으로 보냄
    - 쿠키가 있어도 회원이 없으면(loginMember = null) home으로 보냄
    
    ### 3) 로그아웃
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b524e70d-3487-445e-b7de-7a1479753ec5/Untitled.png)
    
    - 로그인과 똑같이 HttpServletResponse에 쿠키를 넣어보낼 것
    - 다만 cookie.setMaxAge(0)으로 설정하여 보낼 것.
    

# 3. 내가 만든 두번째 쿠키(세션 활용)

- (배경) 첫번째 쿠키에서 발생한 보안이슈 발생
- (정의) 세션을 이용하는 방법
    - 결국 중요한 정보를 모두 서버에 저장하고, 클라이언트와 서버는 추정 불가능한 식별자 값으로 연결해야 한다. 이렇게 서버에 중요한 정보를 보관하고 연결을 유지하는 방법을 세션이라 한다.

### 1) 세션이 어떻게 동작하나?

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d9005e91-d59a-4368-b22c-1038faa9ae88/Untitled.png)

- 웹 브라우저에서 로그인을 통해 id와 password가 서버로 전달된다.
- 서버는 loginId를 회원저장소(DB)와 확인한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cb589496-3b30-4457-b346-1f895da53306/Untitled.png)

- 확인 후 회원이 맞다면 회원에 해당하는 SessionId를 생성한다.
- 여기서 세션은 UUID로 생성하는데, 거의 역추적이 불가능한 구조이다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/790bf631-87c3-40e0-bd0c-16dea493d805/Untitled.png)

- 이 세션ID를 쿠키에 담아 클라이언트에 반환하고, 클라이언트는 해당 세션ID를 쿠키 저장소에 저장한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/5e6e5706-c601-4348-93e1-fe91b58844e3/Untitled.png)

- 클라이언트는 요청 시 항상 mySessionId 쿠키를 서버에 함께 전달한다.
- 서버는 세션Id를 조회한 후, 사용자와 일치하는지 확인하고 응답한다.
- 이 세션의 만료시간은 매우 짧게(30분?) 유지하기 때문에, 해킹을 시도하기 쉽지 않다.]

### 2) 세션은 코드로 어케 구현??

- 크게 3가지를 구현해야 한다. 세션 생성, 세션 조회, 세션 만료

### [세션 생성]

[SeesionManager]

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a4feba92-4008-4cbd-a759-f809e763c0d9/Untitled.png)

- sessionStore : 세션값 & 멤버정보가 담기는 테이블(Map)
- UUID.randomUUID().toString()을 하면, 스프링이 자동으로 세션값을 UUID로 생성한다.
- 생성한 UUID와 멤버값을 sessionStore Map에 넣는다.
- 이후 쿠키 생성해서 세션값 쿠키에 담고 response로 웹브라우저에 응답한다.

### [세션 조회]

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/dc6f6302-1584-4d5a-9102-261b1c0cd7f4/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cba7cd3e-fe30-4bf3-aaf9-30ee1a1782b7/Untitled.png)

- 세션을 조회하기 위한 getsession 메서드 생성
- findCookie메서드 만들어서 쿠키 찾는 로직 생성

### [세션 만료]

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4a2c1d47-2934-4bd6-ba27-8a130026ef72/Untitled.png)

### [HomeController]

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/61ea328b-bd7a-40d9-935d-b81ca5955396/Untitled.png)
