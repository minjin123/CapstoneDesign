package springbook.chatbotserver.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springbook.chatbotserver.chat.model.dto.ChatMessageDto;
import springbook.chatbotserver.chat.model.repository.ChatLogRepository;

/**
 * 채팅 기록을 조회하는 서비스입니다.
 * 이 서비스는 특정 디바이스 ID에 대한 채팅 로그를 가져오는 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class ChatLogService {
  private final ChatLogRepository chatLogRepository;

  /**
   * 주어진 디바이스 ID에 대한 채팅 로그를 조회합니다.
   *
   * @param deviceId 조회할 디바이스의 ID
   * @return 해당 디바이스의 채팅 로그 목록
   */
  public List<ChatMessageDto> getChatLogs(String deviceId) {
    return chatLogRepository.findByDeviceIdOrderByTimestampAsc(deviceId)
        .stream()
        .map(ChatMessageDto::from)
        .toList();
  }
}
