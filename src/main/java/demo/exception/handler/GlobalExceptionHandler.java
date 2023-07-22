package demo.exception.handler;

import demo.exception.BusinessException;
import demo.util.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO<?> handleException(Exception e, HttpServletRequest request) {
        printException(e, e.getClass(), request);
        return ResultVO.error("未知系统异常");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        printException(e, e.getClass(), request);
        return ResultVO.error("未知运行时异常");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultVO<?> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        printException(e, e.getClass(), request);
        return ResultVO.notFound(e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO<?> handleBusinessException(BusinessException e, HttpServletRequest request) {
        printException(e, e.getClass(), request);
        return ResultVO.error(e.getCode(), e.getMessage());
    }

    private void printException(Exception e, Class<?> clazz, HttpServletRequest request) {
        log.error("request uri:{}", request.getRequestURI());
        log.error(String.format(clazz.getSimpleName(), "!"), e);
    }
}
