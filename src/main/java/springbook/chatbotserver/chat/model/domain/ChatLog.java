package springbook.chatbotserver.chat.model.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 채팅 기록을 저장하는 도메인 클래스입니다.
 *이 클래스는 MongoDB에 저장되며, 채팅 메시지의 ID, 디바이스 ID, 타임스탬프, 메시지 유형 및 텍스트를 포함합니다.
 *
 */
@Document(collection = "chat_logs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatLog {

  @Id
  private String id;
  private String deviceId;
  private LocalDateTime timestamp;
  private String messageType;
  private String text;
}
