package springbook.chatbotserver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import springbook.chatbotserver.crawling.domain.dto.CrawledMeal;
import springbook.chatbotserver.crawling.service.MealCrawler;
import springbook.chatbotserver.crawling.service.MealSaveService;

@SpringBootTest
@DisplayName("MealCrawler HTML 파싱 테스트")
public class MealCrawlerTest {

  @Autowired
  private MealCrawler mealCrawler;
  @Autowired
  private MealSaveService mealSaveService;

  @Test
  void crawMealTest() throws Exception {
    List<String[]> dorms = List.of(
        new String[] {"혜화문화관", "https://www.dju.ac.kr/dju/cm/cntnts/cntntsView.do?mi=7064&cntntsId=4222"},
        new String[] {"2생활관", "https://www.dju.ac.kr/dju/cm/cntnts/cntntsView.do?cntntsId=4223&mi=7065"},
        new String[] {"HRC", "https://www.dju.ac.kr/dju/cm/cntnts/cntntsView.do?cntntsId=4224&mi=7066"}
    );
    List<CrawledMeal> meals = new ArrayList<>();
    for (String[] dorm : dorms) {
      String dormName = dorm[0];
      String url = dorm[1];
      List<CrawledMeal> result = mealCrawler.crawl(dormName, url);

      meals.addAll(result);
    }

    assertFalse(meals.isEmpty());

    for (CrawledMeal meal : meals) {
      System.out.println("[" + meal.getDormName() + "]");
      System.out.println("[" + meal.getMealType() + "]");
      System.out.println(meal.getMenuItems());
      System.out.println();
    }
  }
}
