package springbook.chatbotserver.chat.controller;

import static springbook.chatbotserver.http.ResponseCode.NOT_ISSUE;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import springbook.chatbotserver.chat.model.dto.ChatMessageDto;
import springbook.chatbotserver.chat.service.ChatLogService;
import springbook.chatbotserver.http.HttpResponseBody;

/**
 * 채팅 로그 조회 컨트롤러
 * 이 컨트롤러는 특정 디바이스 ID에 대한 채팅 로그를 조회하는 API를 제공합니다.
 * 이는 사용자가 앱을 키면 해당 디바이스의 채팅 로그를 불러오는 기능을 수행합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/logs/{deviceId}")
public class ChatLogController {
  private final ChatLogService chatLogService;

  /**
   * 채팅 로그 조회
   * @param deviceId 디바이스 ID
   * @return 채팅 로그 목록
   */
  @GetMapping
  public ResponseEntity<Object> getChatLogs(@PathVariable String deviceId) {
    List<ChatMessageDto> chatLogs = chatLogService.getChatLogs(deviceId);
    return HttpResponseBody.builder()
        .code(HttpStatus.OK.value())
        .subCode(NOT_ISSUE.getSubCode())
        .message(NOT_ISSUE.getMessage())
        .response(chatLogs)
        .build();

  }
}
