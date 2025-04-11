package springbook.chatbotserver.healcheck.service;

import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClient;

@Service
public class MongoHealthCheck {

	private final MongoClient mongoClient;

	public MongoHealthCheck(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public boolean mongoHealthCheck() {
		try {
			mongoClient.listDatabaseNames().first();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
