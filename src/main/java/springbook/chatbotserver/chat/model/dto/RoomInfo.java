package springbook.chatbotserver.chat.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RoomInfo {
  private final int buildingNumber;
  private final int roomNumber;
  private final boolean valid;

  public RoomInfo(int buildingNumber, int roomNumber) {
    this(buildingNumber, roomNumber, true);
  }

  public static RoomInfo invalid() {
    return new RoomInfo(0, 0, false);
  }
}
