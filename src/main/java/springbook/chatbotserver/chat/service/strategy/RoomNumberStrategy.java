package springbook.chatbotserver.chat.service.strategy;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import springbook.chatbotserver.chat.model.domain.Building;
import springbook.chatbotserver.chat.model.dto.RasaResponse;
import springbook.chatbotserver.chat.model.mapper.BuildingMapper;

/**
 * Rasa 챗봇의 'ask_room_location' 인텐트를 처리하는 전략 클래스입니다.
 * 사용자가 특정 강의실의 위치를 질문한 경우, 건물 번호를 추출하여 위치 정보를 반환합니다.
 */
@Component
@RequiredArgsConstructor
public class RoomNumberStrategy implements IntentStrategy {

  private final BuildingMapper buildingMapper;

  /**
   * 처리 대상 인텐트 이름을 반환합니다.
   *
   * @return 처리할 인텐트 이름 (ask_room_location)
   */
  @Override
  public String getIntent() {
    return "ask_room_location";
  }

  /**
   * Rasa 응답에서 강의실 번호를 추출하고, 건물 번호를 기반으로 위치 정보를 조회합니다.
   * 유효하지 않은 강의실 번호이거나 해당 건물이 존재하지 않는 경우 예외 메시지를 반환합니다.
   *
   * @param response Rasa 챗봇의 응답 객체
   * @return 강의실 위치 또는 오류 안내 메시지
   */
  @Override
  public String handle(RasaResponse response) {
    String rawCode = response.getEntities().stream()
        .filter(e -> "room_number".equals(e.getEntity()))
        .map(RasaResponse.Entity::getValue)
        .findFirst().orElse("");

    String code = rawCode.replaceAll("[^0-9]", "");

    if (code.length() < 5) {
      return "강의실 번호를 다시 입력해주세요.";
    }

    int buildingNumber = Integer.parseInt(code.substring(0, 2));
    int roomNumber = Integer.parseInt(code.substring(2, 5));
    Building building = buildingMapper.findByBuildingNumber(buildingNumber);

    if (building == null) {
      return "해당 강의실이 있는 건물은 존재하지 않습니다. 다시 입력해주세요.";
    }

    return "해당 강의실은 " + building.getName() + " "
        + roomNumber + "호에 있습니다..\n" + "건물 위치는 다음과 같습니다.\n"
        + "[건물 위치](" + building.getMapUrl() + ")";
  }
}


