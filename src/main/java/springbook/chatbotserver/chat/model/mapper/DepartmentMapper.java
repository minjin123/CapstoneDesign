package springbook.chatbotserver.chat.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import springbook.chatbotserver.chat.model.domain.Department;

/**
 * 학과 정보와 관련된 DB 조회 기능을 제공하는 MyBatis 매퍼 인터페이스입니다.
 */
@Mapper
public interface DepartmentMapper {

  /**
   * 지정된 건물 번호에 소속된 학과 정보를 조회합니다.
   *
   * @param buildingNumber 건물 고유 번호
   * @return 해당 건물에 위치한 학과 객체
   */
  Department findDepartmentsInBuilding(@Param("buildingNumber") Integer buildingNumber);
}