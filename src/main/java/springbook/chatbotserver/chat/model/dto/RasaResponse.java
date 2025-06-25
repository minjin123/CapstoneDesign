package springbook.chatbotserver.chat.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Rasa 챗봇 서버로부터 전달받은 응답 데이터를 담는 DTO 클래스입니다.
 * 인식된 인텐트, 엔티티, 원본 텍스트 정보를 포함합니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RasaResponse {

  private Intent intent;
  private List<Entity> entities;
  private String text;

  /**
   * Rasa가 인식한 인텐트 정보를 담는 내부 클래스입니다.
   */
  @Getter
  public static class Intent {
    private String name;
    private double confidence;
  }

  /**
   * Rasa가 추출한 엔티티 정보를 담는 내부 클래스입니다.
   */
  @Getter
  public static class Entity {
    private String entity;
    private String value;
    private String extractor;
    private Integer start;
    private Integer end;
    private Double confidence_entity;
  }
}
