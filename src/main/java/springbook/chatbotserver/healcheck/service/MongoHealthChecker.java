package springbook.chatbotserver.healcheck.service;

import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClient;

import springbook.chatbotserver.healcheck.model.HealthCheckResponse;
import springbook.chatbotserver.healcheck.model.HealthStatus;

@Service
public class MongoHealthChecker {

	private final MongoClient mongoClient;

	public MongoHealthChecker(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public  HealthCheckResponse checkHealth() {
		try {
			mongoClient.listDatabaseNames().first();
			return new HealthCheckResponse(HealthStatus.UP);
		} catch (Exception e) {
			return new HealthCheckResponse(HealthStatus.DOWN);
		}
	}
}
