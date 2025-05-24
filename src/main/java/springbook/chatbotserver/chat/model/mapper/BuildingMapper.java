package springbook.chatbotserver.chat.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import springbook.chatbotserver.chat.model.domain.Building;

/**
 * 건물 정보와 관련된 DB 조회 기능을 제공하는 MyBatis 매퍼 인터페이스입니다.
 */
@Mapper
public interface BuildingMapper {

  /**
   * 건물 번호를 기반으로 건물 정보를 조회합니다.
   *
   * @param buildingNumber 건물 고유 번호
   * @return 해당 건물 번호에 대응하는 건물 객체
   */
  Building findByBuildingNumber(@Param("buildingNumber") Integer buildingNumber);

  /**
   * 학과명을 기반으로 해당 학과가 소속된 건물 정보를 조회합니다.
   *
   * @param departmentName 학과 이름
   * @return 학과가 위치한 건물 객체
   */
  Building findBuildingNameOfDepartment(@Param("departmentName") String departmentName);
}
