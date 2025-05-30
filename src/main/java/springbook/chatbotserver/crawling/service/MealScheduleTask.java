package springbook.chatbotserver.crawling.service;

import java.io.IOException;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import springbook.chatbotserver.crawling.domain.dto.CrawledMeal;
/*
 * 주기적으로 기숙사 식단 정보를 크롤링하고 데이터베이스에 저장하는 스케줄러 클래스입니다.
 * 매주 일요일 새벽 2시에 실행됩니다.
 */
@Component
@RequiredArgsConstructor
public class MealScheduleTask {
  private final MealCrawler mealCrawler;
  private final MealSaveService mealSaveService;

  /* * 매주 일요일 새벽 2시에 기숙사 식단 정보를 크롤링하여 데이터베이스에 저장합니다.
   * 각 기숙사별로 URL을 지정하고, 해당 URL에서 식단 정보를 크롤링합니다.
   * 크롤링된 식단 정보는 MealSaveService를 통해 데이터베이스에 저장됩니다.
   */
  @Scheduled(cron = "0 0 2 ? * SUN")
  public void updateWeeklyMeals() {
    try {
      List<String[]> dorms = List.of(
          new String[] {"혜화문화관", "https://www.dju.ac.kr/dju/cm/cntnts/cntntsView.do?mi=7064&cntntsId=4222"},
          new String[] {"2생활관", "https://www.dju.ac.kr/dju/cm/cntnts/cntntsView.do?cntntsId=4223&mi=7065"},
          new String[] {"HRC", "https://www.dju.ac.kr/dju/cm/cntnts/cntntsView.do?cntntsId=4224&mi=7066"}
      );
      for (String[] dorm : dorms) {
        String dormName = dorm[0];
        String url = dorm[1];
        List<CrawledMeal> crawledMeals = mealCrawler.crawl(dormName, url);
        mealSaveService.save(crawledMeals);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
