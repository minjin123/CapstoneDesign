package springbook.chatbotserver.healcheck.service;

import springbook.chatbotserver.healcheck.model.HealthCheckResponse;
import springbook.chatbotserver.healcheck.model.HealthStatus;

public class HealthCheckService {
	public static HealthCheckResponse checkHealth(){return new HealthCheckResponse(HealthStatus.UP);
	}
}
