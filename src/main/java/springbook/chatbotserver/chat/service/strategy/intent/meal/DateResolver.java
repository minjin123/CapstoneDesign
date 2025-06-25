package springbook.chatbotserver.chat.service.strategy.intent.meal;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import springbook.chatbotserver.config.exception.CustomException;
import springbook.chatbotserver.config.exception.ErrorCode;

/**
 * 날짜 문자열을 해석하고 변환하는 유틸리티 클래스입니다.
 */
public class DateResolver {
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final Pattern MONTH_DAY_PATTERN = Pattern.compile("(\\d{1,2})[월./\\s]*(\\d{1,2})[일]?");
  private static final Map<String, DayOfWeek> DAY_OF_WEEK_MAP = Map.of(
      "월요일", DayOfWeek.MONDAY,
      "화요일", DayOfWeek.TUESDAY,
      "수요일", DayOfWeek.WEDNESDAY,
      "목요일", DayOfWeek.THURSDAY,
      "금요일", DayOfWeek.FRIDAY,
      "토요일", DayOfWeek.SATURDAY,
      "일요일", DayOfWeek.SUNDAY
  );

  /**
   * 주어진 날짜 문자열을 해석하여 "yyyy-MM-dd" 형식의 날짜 문자열로 변환합니다.
   * "오늘", "내일" 등의 키워드와 요일 이름을 지원하며, 잘못된 형식의 경우 예외를 발생시킵니다.
   *
   * @param time 날짜 문자열 (예: "오늘", "내일", "2023-10-01", "월")
   * @return 변환된 날짜 문자열
   */
  public static String resolve(String time) {
    LocalDate today = LocalDate.now();

    try {
      if ("오늘".equals(time)) {
        return format(today);
      }
      if ("내일".equals(time)) {
        return format(today.plusDays(1));
      }
      if (DAY_OF_WEEK_MAP.containsKey(time)) {
        return resolveWeekday(time, today);
      }
      if (MONTH_DAY_PATTERN.matcher(time).matches()) {
        return resolveMonthDay(time, today);
      }
      return LocalDate.parse(time, DATE_FORMATTER).format(DATE_FORMATTER);

    } catch (Exception e) {
      throw new CustomException(ErrorCode.INVALID_date);
    }
  }

  private static String resolveWeekday(String time, LocalDate today) {
    DayOfWeek target = DAY_OF_WEEK_MAP.get(time);
    if (target == null) {
      throw new CustomException(ErrorCode.INVALID_date);
    }
    LocalDate monday = today.with(DayOfWeek.MONDAY);
    LocalDate targetDate = monday.with(target);
    return targetDate.format(DATE_FORMATTER);
  }

  private static String resolveMonthDay(String time, LocalDate today) {
    Matcher matcher = MONTH_DAY_PATTERN.matcher(time);
    if (matcher.find()) {
      int month = Integer.parseInt(matcher.group(1));
      int day = Integer.parseInt(matcher.group(2));
      LocalDate date = LocalDate.of(today.getYear(), month, day);
      return date.format(DATE_FORMATTER);
    }
    throw new CustomException(ErrorCode.INVALID_date);
  }

  private static String format(LocalDate date) {
    return date.format(DATE_FORMATTER);
  }

  public static List<String> getWeekDates() {
    LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);

    return monday.datesUntil(monday.plusDays(7))
        .map(date -> date.format(DATE_FORMATTER))
        .toList();
  }
}
