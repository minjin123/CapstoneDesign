package springbook.chatbotserver.chat.service.strategy.intent.facility;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import springbook.chatbotserver.chat.model.domain.Facility;
import springbook.chatbotserver.chat.model.mapper.FacilityMapper;
import springbook.chatbotserver.chat.service.strategy.AbstractIntentStrategy;
import springbook.chatbotserver.config.exception.CustomException;
import springbook.chatbotserver.config.exception.ErrorCode;

/**
 * Rasa 챗봇의 'ask_location_of_atm' 인텐트를 처리하는 전략 클래스입니다.
 * 사용자가 ATM의 위치를 요청할 때, 해당 ATM 정보를 조회하여 응답을 생성합니다.
 */
@Component
public class AtmStrategy extends AbstractIntentStrategy {

  private static final Map<String, String> facilityMap = Map.of(
      "ATM", "atm",
      "현금인출기", "atm",
      "atm", "atm"
  );
  private final FacilityMapper facilityMapper;

  public AtmStrategy(FacilityMapper facilityMapper) {
    super("ask_location_of_atm", "facility");
    this.facilityMapper = facilityMapper;
  }

  @Override
  public String handleEntityValue(String entityValue) {

    String facilityType = facilityMap.getOrDefault(entityValue, "");
    List<Facility> facilities = facilityMapper.findByFacilityType(facilityType);
    if (facilities.isEmpty()) {
      throw new CustomException(ErrorCode.FACILITY_NOT_FOUND);
    }

    return atmLocationMessage(facilities);
  }

  private String atmLocationMessage(List<Facility> facilities) {
    StringBuilder sb = new StringBuilder();
    sb.append("다음은 ATM이 위치한 장소입니다:\n\n");
    for (Facility facility : facilities) {
      sb.append("- ")
          .append(facility.getName())
          .append(" (")
          .append(facility.getLocationDetail())
          .append(")\n")
          .append(facility.getMapUrl())
          .append("\n\n");
    }
    return sb.toString().trim();
  }
}

