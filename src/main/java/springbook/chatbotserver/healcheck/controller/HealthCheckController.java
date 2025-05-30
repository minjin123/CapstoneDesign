package springbook.chatbotserver.healcheck.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import springbook.chatbotserver.healcheck.model.HealthCheckResponse;
import springbook.chatbotserver.healcheck.model.HealthStatus;
import springbook.chatbotserver.healcheck.service.MariaHealthChecker;
import springbook.chatbotserver.healcheck.service.MongoHealthChecker;
import springbook.chatbotserver.healcheck.service.RasaHealthChecker;
import springbook.chatbotserver.healcheck.service.ServerHealthChecker;

@RestController
@RequestMapping("/v1/api/health")
@Tag(name = "헬스체크", description = "Spring 서버, MongoDB, Rasa 서버 등 주요 구성 요소의 가용성을 확인하여 서비스 상태를 점검하는 API입니다.")
public class HealthCheckController {

  private final ServerHealthChecker serverHealthChecker;
  private final MongoHealthChecker mongoHealthChecker;
  private final RasaHealthChecker rasaHealthChecker;
  private final MariaHealthChecker mariaHealthChecker;

  public HealthCheckController(ServerHealthChecker serverHealthChecker,
      MongoHealthChecker mongoHealthChecker,
      RasaHealthChecker rasaHealthChecker,
      MariaHealthChecker mariaHealthChecker) {

    this.serverHealthChecker = serverHealthChecker;
    this.mongoHealthChecker = mongoHealthChecker;
    this.rasaHealthChecker = rasaHealthChecker;
    this.mariaHealthChecker = mariaHealthChecker;
  }

  @Operation(summary = "서버", description = "서버가 정상적으로 작동하는지 확인합니다.")
  @GetMapping("/server")
  public ResponseEntity<Object> healthCheck() {
    HealthCheckResponse response = serverHealthChecker.checkHealth();
    return ResponseEntity.ok(response);

  }

  @Operation(summary = "MongoDB", description = "MongoDB가 정상적으로 작동하는지 확인합니다.")
  @GetMapping("/mongodb")
  public ResponseEntity<Object> healthCheckMongoDB() {
    HealthCheckResponse response = mongoHealthChecker.checkHealth();
    return ResponseEntity.status(
            response.status() == HealthStatus.UP ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR)
        .body(response);
  }

  @Operation(summary = "rasa", description = "rasa가 정상적으로 작동하는지 확인합니다.")
  @GetMapping("/rasa")
  public ResponseEntity<Object> healthCheckRasa() {
    HealthCheckResponse response = rasaHealthChecker.checkHealth();
    return ResponseEntity.status(
            response.status() == HealthStatus.UP ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR)
        .body(response);
  }

  @Operation(summary = "MariaDB", description = "MariaDB가 정상적으로 작동하는지 확인합니다.")
  @GetMapping("/mariadb")
  public ResponseEntity<Object> healthCheckMariaDB() {
    HealthCheckResponse response = mariaHealthChecker.checkHealth();
    return ResponseEntity.status(
            response.status() == HealthStatus.UP ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR)
        .body(response);
  }
}
