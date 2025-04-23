package springbook.chatbotserver.healcheck.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import springbook.chatbotserver.healcheck.model.HealthCheckResponse;
import springbook.chatbotserver.healcheck.model.HealthStatus;

@Service
public class RasaHealthChecker {

	private final RestTemplate restTemplate;

	public RasaHealthChecker(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	public HealthCheckResponse checkHealth() {
		String url = "http://localhost:5005/version";
		HealthCheckResponse response = restTemplate.getForObject(url, HealthCheckResponse.class);

		if (response == null) {
			return new HealthCheckResponse(HealthStatus.DOWN);
		}
		return new HealthCheckResponse(HealthStatus.UP);
	}
}
