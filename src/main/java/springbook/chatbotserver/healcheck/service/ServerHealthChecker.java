package springbook.chatbotserver.healcheck.service;

import org.springframework.stereotype.Service;

import springbook.chatbotserver.healcheck.model.HealthCheckResponse;
import springbook.chatbotserver.healcheck.model.HealthStatus;

@Service
public class ServerHealthChecker {
	public HealthCheckResponse checkHealth() {
		return new HealthCheckResponse(HealthStatus.UP);
	}
}
