package springbook.chatbotserver.chat.service.strategy;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import springbook.chatbotserver.chat.model.domain.Professor;
import springbook.chatbotserver.chat.model.dto.RasaResponse;
import springbook.chatbotserver.chat.model.mapper.ProfessorMapper;

/**
 * Rasa 챗봇의 'ask_office_of_professor' 인텐트를 처리하는 전략 클래스입니다.
 * 사용자가 특정 교수의 연구실 위치를 질문한 경우, 관련 정보를 조회하여 응답을 생성합니다.
 */
@Component
@RequiredArgsConstructor
public class ProfessorStrategy implements IntentStrategy {

  private final ProfessorMapper professorMapper;

  /**
   * 처리 대상 인텐트 이름을 반환합니다.
   *
   * @return 처리할 인텐트 이름 (ask_office_of_professor)
   */
  @Override
  public String getIntent() {
    return "ask_office_of_professor";
  }

  /**
   * Rasa 응답에서 교수 이름 엔티티를 추출하고,
   * 해당 교수의 연구실 위치 정보를 조회하여 메시지를 생성합니다.
   *
   * @param response Rasa 챗봇의 응답 객체
   * @return 교수의 위치 정보 또는 존재하지 않는 경우의 안내 메시지
   */
  @Override
  public String handle(RasaResponse response) {
    String professorName = response.getEntities().stream()
        .filter(e -> "professor".equals(e.getEntity()))
        .map(RasaResponse.Entity::getValue)
        .findFirst().orElse("");

    Professor professor = professorMapper.findOfficeByProfessorName(professorName);
    if (professor == null) {
      return "해당 교수님은 존재하지 않습니다. 다시 입력해주세요.";
    }

    return professorName + "교수님의 교수실은 "
        + professor.getBuildingName() + " " + professor.getOffice() + "에 있습니다.\n"
        + "[건물 위치](" + professor.getMapUrl() + ")\n\n";
  }
}