package springbook.chatbotserver.chat.service.strategy;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import springbook.chatbotserver.chat.model.domain.Facility;
import springbook.chatbotserver.chat.model.dto.RasaResponse;
import springbook.chatbotserver.chat.model.mapper.FacilityMapper;

/**
 * Rasa 챗봇의 'ask_location_of_store' 인텐트를 처리하는 전략 클래스입니다.
 * 사용자가 교내 매점의 위치를 질문한 경우, 관련 시설 정보를 조회하여 응답을 생성합니다.
 */
@Component
@RequiredArgsConstructor
public class StoreStrategy implements IntentStrategy {

  private final FacilityMapper facilityMapper;

  /**
   * 처리 대상 인텐트 이름을 반환합니다.
   *
   * @return 처리할 인텐트 이름 (ask_location_of_store)
   */
  @Override
  public String getIntent() {
    return "ask_location_of_store";
  }

  /**
   * Rasa 응답에서 시설명 엔티티를 추출하고, 매점 유형으로 매핑된 시설 정보를 조회한 뒤,
   * 위치 안내 메시지를 생성하여 반환합니다.
   *
   * @param response Rasa 챗봇의 응답 객체
   * @return 매점 위치 안내 메시지 또는 오류 메시지
   */
  @Override
  public String handle(RasaResponse response) {
    StringBuilder sb = new StringBuilder();
    String rawCode = response.getEntities().stream()
        .filter(e -> "facility".equals(e.getEntity()))
        .map(RasaResponse.Entity::getValue)
        .findFirst().orElse("");

    String facilityType = "";
    if (rawCode.equals("교내매점") || rawCode.equals("매점")) {
      facilityType = "store";
    }

    List<Facility> facilities = facilityMapper.findByFacilityType(facilityType);
    if (facilities.isEmpty()) {
      return "해당 시설은 존재하지 않습니다. 다시 입력해주세요.";
    }

    sb.append("다음은 매점이 위치한 장소입니다:\n\n");
    for (Facility facility : facilities) {
      sb.append("- ")
          .append(facility.getName())
          .append(" (")
          .append(facility.getLocationDetail())
          .append(")\n")
          .append("  👉 [지도 보기](")
          .append(facility.getMapUrl())
          .append(")\n\n");
    }
    return sb.toString().trim();
  }
}
