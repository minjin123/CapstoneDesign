package springbook.chatbotserver.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 응답코드 및 메시지를 정의하는 열거형 클래스입니다.
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {
  NOT_ISSUE(0, "OK");

  private final int subCode;
  private final String message;
}
