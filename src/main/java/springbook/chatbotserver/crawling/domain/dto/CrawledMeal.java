package springbook.chatbotserver.crawling.domain.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

/**
 * 크롤링된 식단 정보를 담는 DTO 클래스입니다.
 * 기숙사 이름, 날짜, 식사 종류, 메뉴 항목을 포함합니다.
 */
@Getter
@Builder
public class CrawledMeal {
  private String dormName;
  private LocalDate date;
  private String mealType;
  private String menuItems;

  /**
   * CrawledMeal 객체를 생성하는 정적 팩토리 메서드입니다.
   *
   * @param dormName 건물 이름
   * @param date 날짜
   * @param mealType 식사 종류 (조식, 중식, 석식 등)
   * @param menuItems 메뉴 항목 (줄바꿈으로 구분된 문자열)
   * @return CrawledMeal 객체
   */
  public static CrawledMeal of(String dormName, LocalDate date, String mealType, String menuItems) {
    return CrawledMeal.builder()
        .dormName(dormName)
        .date(date)
        .mealType(mealType)
        .menuItems(menuItems)
        .build();
  }
}
