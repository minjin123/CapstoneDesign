package springbook.chatbotserver.crawling.service;

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
   * @param crawledMeals CrawledMeal 객체 리스트로, 각 객체는 식당 이름, 날짜, 식사 종류 및 메뉴 항목을 포함합니다.
   */
  public void save(List<CrawledMeal> crawledMeals) {

    for (CrawledMeal crawledMeal : crawledMeals) {
      List<String> menuItems = parseMenuItems(crawledMeal.getMenuItems());

      if (menuItems.isEmpty())
        continue;

      saveMealAndMenuItems(crawledMeal, menuItems);
    }
  }
  /**
   * 데이터베이스의 모든 식단 정보를 삭제합니다.
   * Meal과 MealMenu 테이블의 모든 데이터를 제거합니다.
   */
  public void deleteAll() {
    mealMapper.deleteAllMealMenus();
    mealMapper.deleteAllMeals();
  }

  private List<String> parseMenuItems(String menuItemsRaw) {
    return Arrays.stream(menuItemsRaw.split("[\\r\\n]+"))
        .map(String::trim)
        .filter(s -> !s.isBlank() && !s.equals("-"))
        .toList();
  }

  private void saveMealAndMenuItems(CrawledMeal crawledMeal, List<String> menuItems) {
    String dormName = crawledMeal.getDormName();
    Integer buildingNumber = buildingMapper.findBuildingNumberOfBuildingName(dormName);

    Meal meal = Meal.of(buildingNumber, crawledMeal.getDate(), crawledMeal.getMealType());
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
