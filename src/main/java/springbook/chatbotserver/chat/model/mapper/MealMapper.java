package springbook.chatbotserver.chat.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import springbook.chatbotserver.chat.model.domain.Meal;
import springbook.chatbotserver.chat.model.domain.MealMenu;
import springbook.chatbotserver.chat.model.dto.MealResponse;

/**
 * 식사 정보와 관련된 DB 조회 기능을 제공하는 MyBatis 매퍼 인터페이스입니다.
 */
@Mapper
public interface MealMapper {
  /**
   * 지정된 건물 번호와 날짜, 식사 유형에 해당하는 식사 정보를 조회합니다.
   *
   * @param buildingNumber 건물 고유 번호
   * @param mealDate       식사 날짜 (형식: yyyy-MM-dd)
   * @param mealType       식사 유형 (예: 조식, 중식, 석식)
   * @return 해당 조건에 맞는 식사 정보 리스트
   */
  List<MealResponse> findMealsByDates(@Param("buildingNumber") int buildingNumber,
      @Param("mealDate") String mealDate,
      @Param("mealType") String mealType);

  /**
   * meal 객체를 DB에 삽입합니다.
   * @param meal 건물 번호, 날짜, 식사 유형을 포함하는 식사 정보 객체
   */
  void insertMeal(@Param("meal") Meal meal);

  /**
   * mealMenu 객체를 DB에 삽입합니다.
   * @param mealMenu 식사 ID와 메뉴 항목을 포함하는 식사 메뉴 정보 객체
   */
  void insertMealMenu(@Param("mealMenu") MealMenu mealMenu);

  void deleteMealMenusByDormitoryId(int buildingNumber);

  void deleteMealsByBuildingNumber(int buildingNumber);
}
