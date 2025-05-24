package springbook.chatbotserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * Rasa 관련 설정 값을 바인딩하는 설정 클래스입니다.
 * application.yml 또는 application.properties에 정의된 'rasa' 프리픽스 하위 속성을 로드합니다.
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "rasa")
public class RasaProperties {

  private String url;
}
