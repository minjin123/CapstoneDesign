package springbook.chatbotserver.chat.model.domain;

import lombok.Getter;

/**
 * 시설 정보를 나타내는 클래스입니다.
 * 시설명, 상세 위치, 지도 URL 등의 정보를 포함합니다.
 */
@Getter
public class Facility {

  private String name;
  private String locationDetail;
  private String mapUrl;
}
