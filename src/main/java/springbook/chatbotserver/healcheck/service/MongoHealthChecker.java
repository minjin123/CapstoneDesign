package springbook.chatbotserver.healcheck.service;

import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClient;

import springbook.chatbotserver.healcheck.model.HealthCheckResponse;
import springbook.chatbotserver.healcheck.model.HealthStatus;

/**
 * MongoHealthChecker는 MongoDB 데이터베이스의 건강 상태를 확인하는 서비스입니다.
 * MongoDB 클라이언트를 사용하여 데이터베이스 연결을 통해 상태를 체크하고, 결과를 HealthCheckResponse로 반환합니다.
 */
@Service
public class MongoHealthChecker implements HealthChecker {

  private final MongoClient mongoClient;

  public MongoHealthChecker(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  @Override
  public HealthCheckResponse checkHealth() {
    try {
      mongoClient.listDatabaseNames().first();
      return new HealthCheckResponse(HealthStatus.UP);
    } catch (Exception e) {
      return new HealthCheckResponse(HealthStatus.DOWN);
    }
  }

  @Override
  public String target() {
    return "MongoDB";
  }
}
