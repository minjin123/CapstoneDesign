package springbook.chatbotserver.http;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * HTTP 응답 본문을 나타내는 클래스입니다.
 * 이 클래스는 HTTP 응답의 상태 코드, 서브 코드, 메시지 및 실제 응답 데이터를 포함합니다.
 * 이 클래스는 빌더 패턴을 사용하여 객체를 생성할 수 있습니다.
 * @param <T> 응답 데이터의 타입
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponseBody<T> {
  private Integer code;
  private Integer subCode;
  private String message;
  private T response;

  public static <T> HttpResponseBodyBuilder<T> builder() {
    return new HttpResponseBodyBuilder<>();
  }

  public static class HttpResponseBodyBuilder<T> {
    private Integer code;
    private Integer subCode;
    private String message;
    private T response;

    HttpResponseBodyBuilder() {
    }

    public HttpResponseBodyBuilder<T> code(final Integer code) {
      this.code = code;
      return this;
    }

    public HttpResponseBodyBuilder<T> subCode(final Integer subCode) {
      this.subCode = subCode;
      return this;
    }

    public HttpResponseBodyBuilder<T> message(final String message) {
      this.message = message;
      return this;
    }

    public HttpResponseBodyBuilder<T> response(final T response) {
      this.response = response;
      return this;
    }

    public ResponseEntity<Object>  build() {
      return ResponseEntity
          .status(code)
          .body(new HttpResponseBody<>(this.code, this.subCode, this.message, this.response));
    }
  }
}
