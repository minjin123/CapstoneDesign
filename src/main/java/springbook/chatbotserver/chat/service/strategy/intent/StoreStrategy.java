package springbook.chatbotserver.chat.service.strategy.intent;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import springbook.chatbotserver.chat.model.domain.Facility;
import springbook.chatbotserver.chat.model.mapper.FacilityMapper;
import springbook.chatbotserver.chat.service.strategy.AbstractIntentStrategy;

/**
 * Rasa 챗봇의 'ask_location_of_store' 인텐트를 처리하는 전략 클래스입니다.
 * 사용자가 매점의 위치를 요청할 때, 해당 매점 정보를 조회하여 응답을 생성합니다.
 */
@Component
public class StoreStrategy extends AbstractIntentStrategy {

  private static final Map<String, String> facilityMap = Map.of(
      "교내매점", "store",
      "매점", "store"
  );
  private final FacilityMapper facilityMapper;

  public StoreStrategy(FacilityMapper facilityMapper) {
    super("ask_location_of_store", "facility");
    this.facilityMapper = facilityMapper;
  }

  @Override
  public String handleEntityValue(String entityValue) {

    String facilityType = facilityMap.getOrDefault(entityValue, "");
    List<Facility> facilities = facilityMapper.findByFacilityType(facilityType);

    if (facilities.isEmpty()) {
      return "해당 시설은 존재하지 않습니다. 다시 입력해주세요.";
    }

    return storeLocationMessage(facilities);
  }

  private String storeLocationMessage(List<Facility> facilities) {
    StringBuilder sb = new StringBuilder();
    sb.append("다음은 매점이 위치한 장소입니다:\n\n");
    for (Facility facility : facilities) {
      sb.append("- ")
          .append(facility.getName())
          .append(" (")
          .append(facility.getLocationDetail())
          .append(")\n")
          .append("[지도 보기](")
          .append(facility.getMapUrl())
          .append(")\n\n");
    }
    return sb.toString().trim();
  }
}
