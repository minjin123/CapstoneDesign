package springbook.chatbotserver.chat.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import springbook.chatbotserver.chat.model.domain.Professor;

/**
 * 교수 정보와 관련된 DB 조회 기능을 제공하는 MyBatis 매퍼 인터페이스입니다.
 */
@Mapper
public interface ProfessorMapper {

  /**
   * 교수 이름을 기준으로 해당 교수의 연구실 정보를 조회합니다.
   *
   * @param professorName 교수 이름
   * @return 교수의 연구실 정보가 담긴 Professor 객체
   */
  Professor findOfficeByProfessorName(@Param("professorName") String professorName);
}
