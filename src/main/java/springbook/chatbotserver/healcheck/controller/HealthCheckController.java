package springbook.chatbotserver.healcheck.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import springbook.chatbotserver.healcheck.model.HealthCheckResponse;
import springbook.chatbotserver.healcheck.service.MongoHealthChecker;
import springbook.chatbotserver.healcheck.service.ServerHealthChecker;

@RestController
@RequestMapping("/v1/api")
@Tag(name = "서버 정상 확인", description = "서버가 정상작동하는지 확인하는 API")
public class HealthCheckController {

	private final ServerHealthChecker serverHealthChecker;
	private final MongoHealthChecker mongoHealthChecker;

	public HealthCheckController(ServerHealthChecker serverHealthChecker, MongoHealthChecker mongoHealthChecker) {
		this.serverHealthChecker = serverHealthChecker;
		this.mongoHealthChecker = mongoHealthChecker;
	}
	@Operation(summary = "서버", description = "서버가 정상적으로 작동하는지 확인합니다.")
	@GetMapping("/health/server")
	public ResponseEntity<Object> healthCheck(){
		HealthCheckResponse response = serverHealthChecker.checkHealth();
		return ResponseEntity.ok(response);

	}
	@Operation(summary = "데이터베이스", description = "데이터베이스가 정상적으로 작동하는지 확인합니다.")
	@GetMapping("/health/mongodb")
	public ResponseEntity<Object> healthCheckMongoDB(){
		HealthCheckResponse response = mongoHealthChecker.checkHealth();
		return ResponseEntity.ok(response);

	}

}
