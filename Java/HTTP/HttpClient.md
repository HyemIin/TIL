# HttpClientBuilder와 HttpClient.newBuilder()의 차이점:

## 클래스 유형:

- HttpClientBuilder: 이 클래스는 Apache HttpClient 라이브러리에 속합니다. 이 라이브러리는 Apache HttpComponents 프로젝트의 일부로서, HTTP 프로토콜을 처리하기 위한 다양한 기능을 제공합니다.
- HttpClient.newBuilder(): 이 메서드는 Java 11부터 도입된 기능으로, Java 표준 라이브러리에 속합니다. 이것은 Java에서 기본적인 HTTP 클라이언트 기능을 사용하기 위한 것입니다.

## 사용 목적:
- HttpClientBuilder: Apache HttpClient 라이브러리의 HttpClientBuilder 클래스는 더 많은 커스터마이징 기능을 제공하며, 연결 풀 설정, 프록시 설정, 인증, 리다이렉션 등 다양한 HTTP 클라이언트 관련 설정을 다룰 수 있습니다.
- HttpClient.newBuilder(): Java 11의 HttpClient.newBuilder() 메서드는 간단한 HTTP 요청 및 응답을 처리하는 데 사용됩니다. 더 간단하고 가볍지만 덜 복잡한 사용 사례에 적합합니다.

## 라이브러리 종속성:
- HttpClientBuilder: Apache HttpClient 라이브러리를 사용하려면 해당 라이브러리에 대한 종속성을 프로젝트에 추가해야 합니다.
- HttpClient.newBuilder(): Java 11부터 기본 Java 표준 라이브러리에 포함되어 있기 때문에 별도의 종속성 추가가 필요하지 않습니다.
