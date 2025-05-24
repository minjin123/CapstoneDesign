package springbook.chatbotserver.chat.model.domain;

import lombok.Getter;

/**
 * 건물 정보를 나타내는 클래스입니다.
 * 건물 번호, 이름, 지도 URL 등의 정보를 포함합니다.
 */
@Getter
public class Building {
  private Integer buildingNumber;
  private String name;
  private String mapUrl;
}
