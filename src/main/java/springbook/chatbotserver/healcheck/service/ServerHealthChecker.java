package springbook.chatbotserver.healcheck.service;

import org.springframework.stereotype.Service;

import springbook.chatbotserver.healcheck.model.HealthCheckResponse;
import springbook.chatbotserver.healcheck.model.HealthStatus;

/**
 * ServerHealthChecker는 서버의 건강 상태를 확인하는 서비스입니다.
 * 서버가 작동 중임을 확인하는 로직을 포함하고 있습니다.
 */
@Service
public class ServerHealthChecker implements HealthChecker {

  @Override
  public HealthCheckResponse checkHealth() {
    return new HealthCheckResponse(HealthStatus.UP);
  }

  @Override
  public String target() {
    return "Server";
  }
}
