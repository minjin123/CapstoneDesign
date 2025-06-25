package springbook.chatbotserver.healcheck.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import springbook.chatbotserver.healcheck.model.HealthCheckResponse;
import springbook.chatbotserver.healcheck.model.HealthStatus;

/**
 * RasaHealthChecker는 Rasa 서버의 건강 상태를 확인하는 서비스입니다.
 * Rasa 서버의 버전 정보를 요청하여 상태를 체크하고, 결과를 HealthCheckResponse로 반환합니다.
 */
@Service
public class RasaHealthChecker implements HealthChecker {

  private final RestTemplate restTemplate;

  public RasaHealthChecker(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public HealthCheckResponse checkHealth() {
    String url = "http://localhost:5005/version";
    HealthCheckResponse response = restTemplate.getForObject(url, HealthCheckResponse.class);

    if (response == null) {
      return new HealthCheckResponse(HealthStatus.DOWN);
    }
    return new HealthCheckResponse(HealthStatus.UP);
  }

  @Override
  public String target() {
    return "Rasa";
  }
}
