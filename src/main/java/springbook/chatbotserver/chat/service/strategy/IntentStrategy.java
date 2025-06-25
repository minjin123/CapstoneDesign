package springbook.chatbotserver.chat.service.strategy;

import springbook.chatbotserver.chat.model.dto.RasaResponse;

/**
 * Rasa 인텐트를 처리하기 위한 전략 인터페이스입니다.
 * 각 전략 클래스는 특정 인텐트를 처리하는 로직을 이 인터페이스를 통해 구현합니다.
 */
public interface IntentStrategy {

  /**
   * 이 전략이 처리할 인텐트 이름을 반환합니다.
   *
   * @return 처리 대상 인텐트 이름
   */
  String getIntent();

  /**
   * Rasa 응답 객체를 기반으로 인텐트에 대한 처리 결과 메시지를 반환합니다.
   *
   * @param rasaResponse Rasa 챗봇의 응답 객체
   * @return 사용자에게 반환할 응답 메시지
   */
  String handle(RasaResponse rasaResponse);
}
