package springbook.chatbotserver.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import springbook.chatbotserver.http.HttpResponseBody;

/**
 * 전역 예외 처리 클래스
 * 모든 컨트롤러에서 발생하는 예외를 처리합니다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 커스텀 예외 처리
   * @param e CustomException
   * @return ResponseEntity<Object>
   */
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<Object> handleCustomException(CustomException e) {
    log.error("{}:{}", e.getErrorCode().name(), e.getMessage());

    return HttpResponseBody.builder()
        .code(e.getErrorCode().getStatus().value())
        .subCode(e.getErrorCode().getSubCode())
        .message(e.getErrorCode().getMessage())
        .response(null)
        .build();
  }

  /**
   * 비즈니스 예외 처리
   * @param e BusinessException
   * @return ResponseEntity<Object>
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGeneralException(Exception e) {
    log.error("Exception", e);

    return HttpResponseBody.builder()
        .code(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
        .subCode(ErrorCode.INTERNAL_SERVER_ERROR.getSubCode())
        .message(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
        .response(null)
        .build();
  }
}

