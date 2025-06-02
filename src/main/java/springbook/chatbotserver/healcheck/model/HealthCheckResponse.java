package springbook.chatbotserver.healcheck.model;

/**
 * 서버의 헬스 체크 응답을 나타내는 DTO 클래스입니다.
 * @param status
 */
public record HealthCheckResponse(HealthStatus status) {
}
