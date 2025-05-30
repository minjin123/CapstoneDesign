package springbook.chatbotserver.chat.service.strategy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import springbook.chatbotserver.chat.model.dto.MealResponse;
import springbook.chatbotserver.chat.model.dto.RasaResponse;
import springbook.chatbotserver.chat.model.mapper.BuildingMapper;
import springbook.chatbotserver.chat.model.mapper.MealMapper;

/**
 * Rasa 챗봇의 'ask_meal_of_dormitory' 인텐트를 처리하는 전략 클래스입니다.
 * 사용자로부터 받은 기숙사명, 날짜, 식사 유형 등을 기반으로 식단 정보를 조회하여 응답을 구성합니다.
 */
@Component
@RequiredArgsConstructor
public class MealStrategy implements IntentStrategy {
  private final BuildingMapper buildingMapper;
  private final MealMapper mealMapper;

  /**
   * 이 전략이 처리하는 인텐트(intent) 이름을 반환합니다.
   * @return "ask_meal_of_dormitory"
   */
  @Override
  public String getIntent() {
    return "ask_meal_of_dormitory";
  }

  /**
   * 사용자의 Rasa 응답으로부터 도출한 엔티티 정보를 기반으로 기숙사 식단 정보를 조회하고,
   * 사용자에게 적절한 문자열 형태로 응답을 구성합니다.
   *
   * @param response Rasa에서 추출된 인텐트 및 엔티티 정보
   * @return 식단 정보 또는 안내 메시지 문자열
   */
  @Override
  public String handle(RasaResponse response) {
    String dorm = response.getEntities().stream()
        .filter(e -> "dorm".equals(e.getEntity()))
        .map(RasaResponse.Entity::getValue)
        .findFirst().orElse("");
    String time = response.getEntities().stream()
        .filter(e -> "time".equals(e.getEntity()))
        .map(RasaResponse.Entity::getValue)
        .findFirst().orElse("오늘");
    String mealType = response.getEntities().stream()
        .filter(e -> "meal_type".equals(e.getEntity()))
        .map(RasaResponse.Entity::getValue)
        .findFirst().orElse("ALL");
    int buildingNumber = buildingMapper.findBuildingNumberOfBuildingName(dorm);
    if (buildingNumber == 0) {
      return "해당 식당은 존재하지 않습니다. 다시 입력해주세요.";
    }
    if ("이번주".equals(time)) {
      List<String> weekDates = getWeekDates();
      StringBuilder sb = new StringBuilder();
      sb.append("이번 주 ").append(dorm).append("의 식단 정보는 다음 날짜에 확인하실 수 있습니다:\n\n");

      for (String date : weekDates) {
        sb.append("- ").append(date).append("\n");
      }

      sb.append("\n확인하고 싶은 날짜를 다시 말씀해 주세요 (예: 오늘, 내일, 2025.05.27).");
      return sb.toString();
    }
    String mealDate = resolveMealDate(time);
    List<MealResponse> meals = mealMapper.findMealsByDates(buildingNumber, mealDate, mealType);
    if (meals.isEmpty()) {
      return dorm + "의 " + mealDate + " 식단을 찾을 수 없습니다.";
    }
    Map<String, List<String>> mealMap = new LinkedHashMap<>();
    for (MealResponse meal : meals) {
      mealMap.computeIfAbsent(meal.getMealType(), k -> new ArrayList<>())
          .add(meal.getMenuItem());
    }
    StringBuilder sb = new StringBuilder();
    sb.append(dorm).append("의 ").append(mealDate).append(" 식단 정보입니다:\n\n");

    for (Map.Entry<String, List<String>> entry : mealMap.entrySet()) {
      sb.append("[").append(entry.getKey()).append("]\n");
      for (String item : entry.getValue()) {
        sb.append(item).append("\n");
      }
      sb.append("\n");
    }
    return sb.toString().trim();
  }

  /**
   * 사용자로부터 입력된 날짜 문자열을 파싱하여 yyyy.MM.dd 형식으로 반환합니다.
   * 예외 처리를 포함하여 다양한 입력을 허용합니다.
   *
   * @param time "오늘", "내일", "5월27일", "2025.05.27" 등의 입력
   * @return yyyy.MM.dd 형식의 날짜 문자열
   */
  private String resolveMealDate(String time) {
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    if ("오늘".equals(time)) {
      return today.format(formatter);
    }

    if ("내일".equals(time)) {
      return today.plusDays(1).format(formatter);
    }
    if (time.matches(".*월.*일")) {
      int year = today.getYear();
      int month = Integer.parseInt(time.split("월")[0].replaceAll("[^0-9]", ""));
      int day = Integer.parseInt(time.split("월")[1].replace("일", "").replaceAll("[^0-9]", ""));
      LocalDate parsedDate = LocalDate.of(year, month, day);
      return parsedDate.format(formatter);
    }
    LocalDate parsedDate = LocalDate.parse(time, formatter);
    return parsedDate.format(formatter);
  }

  /**
   * 현재 주의 월요일부터 일요일까지의 날짜 목록을 반환합니다.
   * 식단 데이터 조회를 위한 날짜 선택지를 사용자에게 제시할 때 사용됩니다.
   *
   * @return yyyy.MM.dd 형식의 날짜 리스트 (7일)
   */
  private List<String> getWeekDates() {
    LocalDate today = LocalDate.now();
    LocalDate monday = today.with(DayOfWeek.MONDAY);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    return monday.datesUntil(monday.plusDays(7))
        .map(date -> date.format(formatter))
        .toList();
  }

}
