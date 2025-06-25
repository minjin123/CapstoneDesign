package springbook.chatbotserver.chat.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MealRequest {
  private final String dorm;
  private final String time;
  private final String mealType;

}
