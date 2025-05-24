package springbook.chatbotserver.chat.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import springbook.chatbotserver.chat.model.domain.ChatLog;

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
