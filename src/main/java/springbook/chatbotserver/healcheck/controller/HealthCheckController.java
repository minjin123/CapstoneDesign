package springbook.chatbotserver.healcheck.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import springbook.chatbotserver.healcheck.model.HealthCheckResponse;
import springbook.chatbotserver.healcheck.model.HealthStatus;
import springbook.chatbotserver.healcheck.service.HealthChecker;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/health")
@Tag(name = "헬스체크", description = "Spring 서버, MongoDB, Rasa 서버, MariaDB 등 주요 구성 요소의 가용성을 확인하여 서비스 상태를 점검하는 API입니다.")
public class HealthCheckController {
  private final List<HealthChecker> healthCheckers;

  @GetMapping("/all")
  @Operation(summary = "전체 헬스체크", description = "서비스의 가용성을 확인합니다. 모든 구성 요소의 상태를 반환합니다.")
  public ResponseEntity<Map<String, HealthCheckResponse>> checkAll() {
    Map<String, HealthCheckResponse> result = new LinkedHashMap<>();
    for (HealthChecker healthChecker : healthCheckers) {
      HealthCheckResponse response = healthChecker.checkHealth();
      result.put(healthChecker.target(), response);
    }
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{target}")
  @Operation(summary = "단일 헬스체크", description = "서비스의 가용성을 확인합니다. 각 구성 요소의 상태를 반환합니다.(ex: Spring, Mongodb, Rasa, Mariadb 등)")
  public ResponseEntity<HealthCheckResponse> checkTarget(@PathVariable String target) {
    return healthCheckers.stream()
        .filter(c -> c.target().equalsIgnoreCase(target))
        .findFirst()
        .map(c -> {
          HealthCheckResponse res = c.checkHealth();
          HttpStatus status = res.status() == HealthStatus.UP ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
          return ResponseEntity.status(status).body(res);
        })
        .orElse(ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new HealthCheckResponse(HealthStatus.DOWN)));
  }

}
