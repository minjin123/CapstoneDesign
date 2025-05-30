package springbook.chatbotserver.crawling.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springbook.chatbotserver.chat.model.domain.Meal;
import springbook.chatbotserver.chat.model.domain.MealMenu;
import springbook.chatbotserver.chat.model.mapper.BuildingMapper;
import springbook.chatbotserver.chat.model.mapper.MealMapper;
import springbook.chatbotserver.crawling.domain.dto.CrawledMeal;
/**
 * 크롤링된 식단 정보를 데이터베이스에 저장하는 서비스 클래스입니다.
 * CrawledMeal 객체 리스트를 받아 각 식단 정보를 Meal과 MealMenu 객체로 변환하여 저장합니다.
 */
@Service
@RequiredArgsConstructor
public class MealSaveService {

  private final MealMapper mealMapper;
  private final BuildingMapper buildingMapper;

  /**
   * 크롤링된 식단 정보를 데이터베이스에 저장합니다.
   * @param crawledMeals
   */
  public void save(List<CrawledMeal> crawledMeals) {

    for (CrawledMeal crawledMeal : crawledMeals) {
      String[] rawMenuItems = crawledMeal.getMenuItems().split("[\\r\\n]+");
      List<String> menuItems = Arrays.stream(rawMenuItems)
          .map(String::trim)
          .filter(s -> !s.isBlank() && !s.equals("-"))
          .toList();

      if (menuItems.isEmpty())
        continue;

      String dormName = crawledMeal.getDormName();
      int buildingNumber = buildingMapper.findBuildingNumberOfBuildingName(dormName);
      String mealType = crawledMeal.getMealType();
      LocalDate dates = crawledMeal.getDate();
      Meal meal = Meal.of(
          buildingNumber,
          dates,
          mealType
      );
      mealMapper.insertMeal(meal);

      int mealId = meal.getId();
      for (String menuItem : menuItems) {
        MealMenu mealMenu = MealMenu.of(
            mealId,
            menuItem.trim()
        );
        mealMapper.insertMealMenu(mealMenu);
      }
    }
  }
}
