# 1. 메시지 란?

- 만약 화면에 “상품명”이라는 글자를 “상품이름”으로 전부 바꾸고 싶다고 하자.
그러면 수십, 수백개의 단어를 교체해야하는 상황이 올수도 있다.
- 왜냐하면 메시지가 HTML에 하드코딩 되어 있기 때문이다.
- (정의) 이런 다양한 메시지를 한 곳에서 관리하는 기능을 메시지 기능이라고 한다.
- 즉, 매번 수정사항이 생길떄마다 모든 파일을 뒤져가면서 이름을 바꾸는게 아니라, 특정 이름들이 모여저 있는”메시지 파일”(message.properties)만 바꾸면 되도록 개발하는 것. 그러기 위해선 타임리프를 활용하여 html에 message.properties를 읽어오도록 설정해두어야 할 것이다.
    - 어떤 내용으로 대체할 지는 th:text=”#{…}”로 적어주면 됨.
- (why) 편리한 명칭 관리

# 2. 국제화 란?

- (정의) 웹 페이지를 접속했을 때 각 나라의 언어에 맞춰서 웹 페이지 내용을 자동으로 번역해 놓는 것
- 예를 들어, 영어를 사용하는 국적이면 `message_en.properties` 
한국어를 사용하는 국적이면 `message_kr.properties`를 제공하도록 개발하면 된다.

# 3. 스프링은 메시지 국제화 기능을 알아서 제공한다.

- 1) application.properties에 아래와 같은 코드 추가
`spring.messages.basename=messages`
- 2) messages_en.properties 등 파일만 등록하면 스프링이 자동으로 인식한다.
messages_ko.properties 등

- MessageSource 라는 인터페이스에 메시지를 읽는 기능이 들어가 있으니, 해당 인터페이스를 구현하여 쓰면 된다.

# 3-1. 메시지 쓸 때, 인텔리제이 UTF-8로 인코딩설정 해둬야한다.

<img width="1025" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/c965e994-8acd-4012-8a75-36f09484bcb8">

- 그리고 인텔리제이 재시작하면 된다. 아니면 한글 못씀;;

# 4. 타임리프 메시지 적용

- 타임리프 메시지 표현식 : #{…} 을 사용하면 스프링의 메시지를 편리하게 조회할 수 있다.

<img width="671" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/7dc95829-06ec-4e32-bf59-1701f4a4723b">

- addform.html에 적용한 메시지이다. page.addItem이라는 것을 message.properties에서 찾아서 가져올 것이다.
<img width="500" alt="Untitled" src="https://github.com/HyemIin/TIL/assets/114489245/ba42cd9d-6330-4cb9-8126-ba944d587857">
