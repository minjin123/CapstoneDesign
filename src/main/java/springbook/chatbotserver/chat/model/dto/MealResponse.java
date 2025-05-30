package springbook.chatbotserver.chat.model.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 특정 날짜의 (조식,중식,석식) 별 식단을 담은 DTO 클래스입니다.
 * 날짜인 mealDate 와 식사 종류인 mealType, 메뉴 항목인 menuItem을 포함합니다.
 */
@Getter
@Builder
public class MealResponse {
  private String mealDate;
  private String mealType;
  private String menuItem;
}
