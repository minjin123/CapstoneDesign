package springbook.chatbotserver.chat.service.strategy.intent;

import org.springframework.stereotype.Component;

import springbook.chatbotserver.chat.model.dto.RasaResponse;
import springbook.chatbotserver.chat.service.strategy.IntentStrategy;
import springbook.chatbotserver.config.exception.CustomException;
import springbook.chatbotserver.config.exception.ErrorCode;

/**
 * Rasa 인텐트를 처리할 수 없는 경우에 대응하는 기본 전략 클래스입니다.
 * 어떤 전략에도 매핑되지 않는 요청에 대해 기본 응답을 제공합니다.
 */
@Component
public class DefaultStrategy implements IntentStrategy {

  /**
   * 처리 대상 인텐트 이름을 반환합니다.
   * 기본 전략이므로 항상 "default"를 반환합니다.
   *
   * @return "default" 인텐트 문자열
   */
  @Override
  public String getIntent() {
    return "default";
  }

  /**
   * 처리 불가능한 요청에 대해 사용자에게 전달할 기본 응답 메시지를 반환합니다.
   *
   * @param rasaResponse Rasa 챗봇의 응답 객체 (사용되지 않음)
   * @return 기본 오류 메시지 문자열
   */
  @Override
  public String handle(RasaResponse rasaResponse) {
    throw new CustomException(ErrorCode.INTENT_NOT_FOUND);
  }
}

