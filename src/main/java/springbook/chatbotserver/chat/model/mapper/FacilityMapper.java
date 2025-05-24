package springbook.chatbotserver.chat.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import springbook.chatbotserver.chat.model.domain.Facility;

/**
 * 시설 정보와 관련된 DB 조회 기능을 제공하는 MyBatis 매퍼 인터페이스입니다.
 */
@Mapper
public interface FacilityMapper {

  /**
   * 시설 유형을 기반으로 해당 유형의 시설 목록을 조회합니다.
   *
   * @param facilityType 시설 유형 (예: ATM, 복사기, 편의점 등)
   * @return 주어진 유형에 해당하는 시설 리스트
   */
  List<Facility> findByFacilityType(@Param("facilityType") String facilityType);
}
