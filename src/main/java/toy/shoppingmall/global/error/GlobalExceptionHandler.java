package toy.shoppingmall.global.error;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import toy.shoppingmall.domain.user.exception.DuplicateEmailException;
import toy.shoppingmall.domain.user.exception.EmailNotFoundException;
import toy.shoppingmall.domain.user.exception.invalidPasswordException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JSONObject duplicateEmailException() {
        return ErrorResponse.JsonErrorResponse(400, "중복된 이메일 입니다.");
    }

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public JSONObject emailNotFoundException() {
        return ErrorResponse.JsonErrorResponse(409, "존재하지 않는 이메일 입니다.");
    }

    @ExceptionHandler(invalidPasswordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public JSONObject invalidPasswordException() {
        return ErrorResponse.JsonErrorResponse(409, "비밀번호가 일치하지 않습니다.");
    }
}
