package springbook.chatbotserver.chat.service;

import java.time.LocalDateTime;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import springbook.chatbotserver.chat.model.domain.ChatLog;
import springbook.chatbotserver.chat.model.dto.RasaRequest;
import springbook.chatbotserver.chat.model.dto.RasaResponse;
import springbook.chatbotserver.chat.model.repository.ChatLogRepository;
import springbook.chatbotserver.chat.service.strategy.IntentStrategy;
import springbook.chatbotserver.chat.service.strategy.StrategyFactory;
import springbook.chatbotserver.config.RasaProperties;

/**
 * Rasa 챗봇과의 통신 및 인텐트 처리 전략 실행을 담당하는 서비스 클래스입니다.
 * 사용자 메시지를 Rasa 서버에 전송하고, 응답 결과를 기반으로 적절한 전략을 적용하여 응답을 생성합니다.
 */
@Service
@RequiredArgsConstructor
public class RasaService {

  private final RestTemplate restTemplate;
  private final RasaProperties rasaProperties;
  private final StrategyFactory strategyFactory;
  private final ChatLogRepository chatLogRepository;

  /**
   * 사용자 요청 메시지를 Rasa 서버로 전송하고,
   * 응답에 포함된 인텐트를 기반으로 적절한 전략을 실행하여 결과를 반환합니다.
   * 또한 사용자/챗봇의 메시지를 로그로 저장합니다.
   *
   * @param req 사용자 요청 DTO (deviceId, message 포함)
   * @return 챗봇 응답 메시지
   */
  public String sendMessageToRasa(RasaRequest req) {
    // 사용자 메시지 로그 저장
    chatLogRepository.save(ChatLog.builder()
        .deviceId(req.getDeviceId())
        .timestamp(LocalDateTime.now())
        .messageType("user")
        .text(req.getText())
        .build());

    // Rasa 서버에 POST 요청
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<RasaRequest> entity = new HttpEntity<>(req, headers);
    String rasaUrl = rasaProperties.getUrl();
    ;
    ResponseEntity<RasaResponse> response =
        restTemplate.postForEntity(rasaUrl, entity, RasaResponse.class);

    RasaResponse rasa = response.getBody();
    if (rasa == null) {
      throw new RuntimeException("라사서버 응답이 없습니다. 서버 상태를 확인해주세요.");
    }
    String intent = rasa.getIntent().getName();

    // 챗봇 응답 메시지 로그 저장
    String botText = rasa.getText();
    chatLogRepository.save(ChatLog.builder()
        .deviceId(req.getDeviceId())
        .timestamp(LocalDateTime.now())
        .messageType("bot")
        .text(botText)
        .build());

    // 전략 실행
    IntentStrategy strategy = strategyFactory.getStrategy(intent);
    return strategy.handle(rasa);
  }
}
