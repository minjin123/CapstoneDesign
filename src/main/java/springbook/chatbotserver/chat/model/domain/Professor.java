package springbook.chatbotserver.chat.model.domain;

import lombok.Getter;

/**
 * 교수의 교수실 정보를 나타내는 클래스입니다.
 * 소속 건물명, 연구실 위치, 지도 URL 정보를 포함합니다.
 */
@Getter
public class Professor {

  private String buildingName;
  private String office;
  private String mapUrl;
}