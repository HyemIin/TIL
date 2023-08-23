```java
public class API06 {
    //특정 서버에 있는 자원을 네트워크를 통해 가져오는 방법
    public static void main(String[] args) {
        String url = "https://www.fmkorea.com/search.php?mid=hotdeal&listStyle=webzine&document_srl=6104048875&search_keyword=%EB%A1%9C%EC%A7%80%ED%85%8D&search_target=title_content&page=1";
        //request --> response : HTML을 JSON으로 변경해서 파싱하려면, Jsoup API를 활용할 것
        // Jsoup API는 http connection과 parsing을 지원
        try {
            Document document =Jsoup.connect(url).get(); // url 커넥션 연결 & HTTP get 방식으로 요청한다는 뜻. get방식의 리턴 타입은 document
            System.out.println(document.toString()); // 크롤링한 html 파일 확인
            Element element1 =document.selectFirst("p.title"); // 찾는 태그.클래스 이름을 찾는 방법 // 저런 태그 단위를 엘리먼트라고 함
            Element element2=document.selectFirst("td.td-write");

            String text = element1.text(); // 추출한 엘리먼트 중 text만 출력
            System.out.println(text);
            String text2 = element2.text();
            System.out.println(text2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
```
