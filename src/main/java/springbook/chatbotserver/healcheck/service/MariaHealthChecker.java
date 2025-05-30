package springbook.chatbotserver.healcheck.service;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import springbook.chatbotserver.healcheck.model.HealthCheckResponse;
import springbook.chatbotserver.healcheck.model.HealthStatus;

@Service
public class MariaHealthChecker {

  private final DataSource dataSource;

  public MariaHealthChecker(DataSource dataSource) {
    this.dataSource = dataSource;
  }
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
}
