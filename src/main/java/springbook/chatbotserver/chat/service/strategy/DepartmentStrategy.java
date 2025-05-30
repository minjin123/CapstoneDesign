package springbook.chatbotserver.chat.service.strategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import springbook.chatbotserver.chat.model.domain.Building;
import springbook.chatbotserver.chat.model.dto.RasaResponse;
import springbook.chatbotserver.chat.model.mapper.BuildingMapper;

/**
 * Rasa 챗봇의 'ask_building_of_department' 인텐트를 처리하는 전략 클래스입니다.
 * 사용자가 특정 학과의 위치를 질문하면, 해당 학과가 소속된 건물 정보를 조회하여 응답을 생성합니다.
 */
@Component
@RequiredArgsConstructor
public class DepartmentStrategy implements IntentStrategy {

  private final BuildingMapper buildingMapper;

  /**
   * 처리 대상 인텐트 이름을 반환합니다.
   *
   * @return 처리할 인텐트 이름 (ask_building_of_department)
   */
  @Override
  public String getIntent() {
    return "ask_building_of_department";
  }

  /**
   * Rasa 응답에서 학과명 엔티티를 추출하여, 해당 학과가 위치한 건물 정보를 조회합니다.
   * 건물 정보가 존재할 경우 위치 메시지를 반환하고, 없을 경우 오류 메시지를 반환합니다.
   *
   * @param response Rasa 챗봇의 응답 객체
   * @return 사용자에게 제공할 학과 건물 안내 메시지
   */
  @Override
  public String handle(RasaResponse response) {
    String rawCode = response.getEntities().stream()
        .filter(e -> "department".equals(e.getEntity()))
        .map(RasaResponse.Entity::getValue)
        .findFirst().orElse("");

    Pattern pattern = Pattern.compile("^(.*?과)(은|는|이|가|를|의|에|으로)?$");
    Matcher matcher = pattern.matcher(rawCode);
    String departmentName = matcher.find() ? matcher.group(1) : rawCode;

    Building building = buildingMapper.findBuildingNameOfDepartment(departmentName);

    if (building == null) {
      return "해당 학과가 있는 건물은 존재하지 않습니다. 다시 입력해주세요.";
    }

    return departmentName + "는 " + building.getName() + "에 있습니다.\n"
        + "[건물 위치](" + building.getMapUrl() + ")";
  }
}