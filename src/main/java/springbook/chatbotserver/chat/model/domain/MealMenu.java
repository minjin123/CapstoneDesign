package springbook.chatbotserver.chat.model.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * 식사 메뉴 정보를 담는 도메인 클래스입니다.
 * mealId와 menuItem을 포함합니다.
 */
@Getter
@Builder
public class MealMenu {
  private int mealId;
  private String menuItem;

  /**
   * MealMenu 객체를 생성하는 정적 팩토리 메서드입니다.
   *
   * @param mealId 식사 ID
   * @param menuItem 메뉴 항목
   * @return MealMenu 객체
   */
  public static MealMenu of(int mealId, String menuItem) {
    return MealMenu.builder()
        .mealId(mealId)
        .menuItem(menuItem)
        .build();
  }
}
