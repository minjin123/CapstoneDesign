package springbook.chatbotserver.crawling.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springbook.chatbotserver.config.exception.CustomException;
import springbook.chatbotserver.config.exception.ErrorCode;
import springbook.chatbotserver.crawling.domain.dto.CrawledMeal;

/** 식단 정보를 크롤링하는 서비스 클래스입니다.
 * Jsoup 라이브러리를 사용하여 HTML 문서를 파싱하고, 식단 정보를 추출합니다.
 */
@Service
@RequiredArgsConstructor
public class MealCrawler {

  public List<CrawledMeal> crawl(String dormName, String url) throws IOException {
    Document doc = Jsoup.connect(url).get();
    Element targetTable = doc.selectFirst("table");

    if (targetTable == null) {
      throw new CustomException(ErrorCode.TABLE_NOT_FOUND);
    }
    List<LocalDate> dateList = extractDates(targetTable);

    return extractMeals(targetTable, dateList, dormName);
  }

  private List<LocalDate> extractDates(Element targetTable) {
    List<LocalDate> dateList = new ArrayList<>();
    Elements dateHeaders = targetTable.select("thead tr th");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");

    for (int i = 1; i < dateHeaders.size(); i++) { // 첫 번째는 '구분'
      String dateStr = dateHeaders.get(i).text().trim();
      if (!dateStr.isEmpty()) {
        dateList.add(LocalDate.parse(dateStr, formatter));
      }
    }
    return dateList;
  }

  private List<CrawledMeal> extractMeals(Element targetTable, List<LocalDate> dateList, String dormName) {
    List<CrawledMeal> result = new ArrayList<>();
    Elements rows = targetTable.select("tbody tr");

    for (Element row : rows) {
      Element th = row.selectFirst("th");
      if (th == null) {
        continue;
      }

      String mealType = th.text().replace("[Extras Bar]", "").trim();
      Elements tds = row.select("td");

      for (int i = 0; i < tds.size(); i++) {
        Element td = tds.get(i);
        String[] items = td.html().split("<br>");
        String joined = Arrays.stream(items)
            .map(item -> Entities.unescape(item.trim()))
            .filter(s -> !s.isEmpty())
            .collect(Collectors.joining("\n"));

        CrawledMeal meal = CrawledMeal.of(dormName, dateList.get(i), mealType, joined);
        result.add(meal);
      }
    }

    return result;
  }
}
