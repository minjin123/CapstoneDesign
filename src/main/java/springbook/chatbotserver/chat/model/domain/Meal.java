package springbook.chatbotserver.chat.model.domain;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
/* * 식사 정보를 담는 도메인 클래스입니다.
 * 기숙사 ID, 날짜, 식사 종류를 포함합니다.
 */
@Getter
@Builder
public class Meal {
  private int id;
  private int dormitoryId;
  private LocalDate date;
  private String mealType;

  /**
   * Meal 객체를 생성하는 정적 팩토리 메서드입니다.
   *
   * @param dormitoryId 건물 ID
   * @param date 날짜
   * @param mealType 식사 종류 (조식, 중식, 석식 등)
   * @return Meal 객체
   */
  public static Meal of(int dormitoryId, LocalDate date, String mealType) {
    return Meal.builder()
        .dormitoryId(dormitoryId)
        .date(date)
        .mealType(mealType)
        .build();
  }
}

