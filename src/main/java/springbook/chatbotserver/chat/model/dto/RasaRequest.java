package springbook.chatbotserver.chat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rasa 챗봇 서버로 전송할 요청 메시지를 담는 DTO 클래스입니다.
 * 사용자를 식별하는 deviceId와 사용자의 질문인 text를 포함합니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RasaRequest {
  private String deviceId;
  private String text;
}
