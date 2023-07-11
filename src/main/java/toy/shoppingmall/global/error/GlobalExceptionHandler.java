package toy.shoppingmall.global.error;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import toy.shoppingmall.domain.user.exception.DuplicateEmailException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public JSONObject duplicateEmailException() {
        return ErrorResponse.JsonErrorResponse(400, "중복된 이메일 입니다.");
    }
}
