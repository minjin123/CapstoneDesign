package springbook.chatbotserver.chat.service.strategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 인텐트 이름에 따라 해당 전략(IntentStrategy)을 매핑하고 제공하는 전략 팩토리 클래스입니다.
 * 모든 전략 구현체를 초기화 시점에 수집하여, 인텐트별로 처리 전략을 동적으로 선택할 수 있도록 구성합니다.
 */
@Component
public class StrategyFactory {

  private final Map<String, IntentStrategy> strategyMap;

  /**
   * 모든 IntentStrategy 구현체를 주입받아, 인텐트 이름 기준으로 매핑 테이블을 구성합니다.
   *
   * @param strategies 모든 IntentStrategy 구현체 목록
   */
  @Autowired
  public StrategyFactory(List<IntentStrategy> strategies) {
    this.strategyMap = strategies.stream()
        .collect(Collectors.toMap(IntentStrategy::getIntent, strategy -> strategy));
  }

  /**
   * 주어진 인텐트 이름에 해당하는 전략을 반환합니다.
   * 해당 인텐트가 없을 경우 기본 전략("default")을 반환합니다.
   *
   * @param intent 인텐트 이름
   * @return 인텐트에 대응하는 전략 객체
   */
  public IntentStrategy getStrategy(String intent) {
    return strategyMap.getOrDefault(intent, strategyMap.get("default"));
  }
}