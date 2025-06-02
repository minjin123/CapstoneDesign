package springbook.chatbotserver.chat.service.strategy.intent.meal;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateResolver {
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

  public static String resolve(String time) {
    LocalDate today = LocalDate.now();

    try {
      if ("오늘".equals(time))
        return today.format(DATE_FORMATTER);
      if ("내일".equals(time))
        return today.plusDays(1).format(DATE_FORMATTER);

      if (time.matches(".*월.*일")) {
        int year = today.getYear();
        int month = Integer.parseInt(time.split("월")[0].replaceAll("[^0-9]", ""));
        int day = Integer.parseInt(time.split("월")[1].replace("일", "").replaceAll("[^0-9]", ""));
        return LocalDate.of(year, month, day).format(DATE_FORMATTER);
      }

      return LocalDate.parse(time, DATE_FORMATTER).format(DATE_FORMATTER);

    } catch (Exception e) {
      throw new IllegalArgumentException("날짜 형식이 잘못되었습니다: " + time);
    }
  }

  public static List<String> getWeekDates() {
    LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);

    return monday.datesUntil(monday.plusDays(7))
        .map(date -> date.format(DATE_FORMATTER))
        .toList();
  }
}
