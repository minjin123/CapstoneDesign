package springbook.chatbotserver.chat.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import springbook.chatbotserver.chat.model.domain.ChatLog;

/**
 *  챗 메시지 DTO 클래스 입니다.
 *  이는 DTO는 챗 메시지의 텍스트, 사용자 여부(user : true, bot: false), 타임스탬프를 포함합니다.
 */
@Getter
@AllArgsConstructor
public class ChatMessageDto {
  private String text;
  private boolean isUser;
  private LocalDateTime timestamp;

  public static ChatMessageDto from(ChatLog log) {
    return new ChatMessageDto(
        log.getText(),
        "user".equals(log.getMessageType()),
        log.getTimestamp()
    );
  }
}
