package springbook.chatbotserver.healcheck.service;

import springbook.chatbotserver.healcheck.model.HealthCheckResponse;

/**
 * HealthChecker 인터페이스는 시스템의 건강 상태를 확인하는 기능을 제공합니다.
 * 각 구현 클래스는 특정 시스템(예: 데이터베이스, 서버 등)의 건강 상태를 체크하고
 * 그 결과를 반환합니다.
 */
public interface HealthChecker {
  HealthCheckResponse checkHealth();

  String target();
}
