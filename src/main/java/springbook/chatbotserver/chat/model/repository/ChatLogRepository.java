package springbook.chatbotserver.chat.model.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import springbook.chatbotserver.chat.model.domain.ChatLog;

/**
 * 챗 로그 레포지토리 인터페이스
 * 이 인터페이스는 MongoDB를 사용하여 챗 로그 데이터를 관리합니다.
 */
public interface ChatLogRepository extends MongoRepository<ChatLog, String> {
  List<ChatLog> findByDeviceIdOrderByTimestampAsc(String deviceId);
}
