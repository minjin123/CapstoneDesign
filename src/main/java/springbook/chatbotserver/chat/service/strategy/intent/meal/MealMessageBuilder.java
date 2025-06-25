package springbook.chatbotserver.chat.service.strategy.intent.meal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import springbook.chatbotserver.chat.model.dto.MealResponse;

/**
 * 식단 관련 메시지를 생성하는 클래스
 */
@Component
public class MealMessageBuilder {

  /**
   * 확인 하고 싶은 날짜를 다시 요청하는 메시지를 생성
   *
   * @param dorm 식당 이름
   * @return 포맷된 식단 메시지
   */
  public String buildWeekMenuMessage(String dorm) {
    List<String> weekDates = DateResolver.getWeekDates();

    StringBuilder sb = new StringBuilder();
    sb.append("이번 주 ").append(dorm).append("의 식단 정보는 다음 날짜에 확인하실 수 있습니다:\n\n");
    weekDates.forEach(date -> sb.append("- ").append(date).append("\n"));
    sb.append("\n확인하고 싶은 날짜를 다시 말씀해 주세요 (예: 오늘, 내일, 05월27일)");

    return sb.toString();
  }

  /**
   * 특정 식당의 식단 정보를 메시지 형식으로 생성
   *
   * @param dorm 식당 이름
   * @param mealDate 식단 날짜
   * @param meals 식단 정보 리스트
   * @return 포맷된 식단 메시지
   */
  public String buildMealMessage(String dorm, String mealDate, List<MealResponse> meals) {
    Map<String, List<String>> mealMap = new LinkedHashMap<>();
    meals.forEach(meal ->
        mealMap.computeIfAbsent(meal.getMealType(), k -> new ArrayList<>())
            .add(meal.getMenuItem())
    );

    StringBuilder sb = new StringBuilder();
    sb.append(dorm.toUpperCase()).append("의 ").append(mealDate).append(" 식단 정보입니다:\n\n");

    mealMap.forEach((type, items) -> {
      sb.append("[").append(type).append("]\n");
      items.forEach(item -> sb.append(item).append("\n"));
      sb.append("\n");
    });

    return sb.toString().trim();
  }
}
