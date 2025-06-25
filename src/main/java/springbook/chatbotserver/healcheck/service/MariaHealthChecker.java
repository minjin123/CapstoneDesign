package springbook.chatbotserver.healcheck.service;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import springbook.chatbotserver.healcheck.model.HealthCheckResponse;
import springbook.chatbotserver.healcheck.model.HealthStatus;

/**
 * MariaHealthChecker는 MariaDB 데이터베이스의 건강 상태를 확인하는 서비스입니다.
 * 데이터베이스 연결을 통해 상태를 체크하고, 결과를 HealthCheckResponse로 반환합니다.
 */
@Service
public class MariaHealthChecker implements HealthChecker {

  private final DataSource dataSource;

  public MariaHealthChecker(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public HealthCheckResponse checkHealth() {
    try (var connection = dataSource.getConnection()) {
      if (connection.isValid(2)) {
        return new HealthCheckResponse(HealthStatus.UP);
      } else {
        return new HealthCheckResponse(HealthStatus.DOWN);
      }
    } catch (Exception e) {
      return new HealthCheckResponse(HealthStatus.DOWN);
    }
  }

  @Override
  public String target() {
    return "MariaDB";
  }
}
