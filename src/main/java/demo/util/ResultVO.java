package demo.util;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Data
public class ResultVO<T> {
    private int code;
    private String message;
    private String errorMessage;
    private T data;
    private String url;
    private Date date;

    protected ResultVO() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            this.url = request.getRequestURI();
            this.date = new Date();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> ResultVO<T> of(Class<T> clazz) {
        ResultVO<T> resultVO = new ResultVO<>();
        return resultVO;
    }

    public static ResultVO<?> ok() {
        ResultVO<?> resultVO = new ResultVO<>();
        resultVO.setCode(HttpStatus.OK.value());
        resultVO.setMessage(HttpStatus.OK.name());
        resultVO.setErrorMessage("");
        return resultVO;
    }

    public static ResultVO<?> ok(int code) {
        ResultVO<?> resultVO = new ResultVO<>();
        resultVO.setCode(code);
        resultVO.setMessage(HttpStatus.OK.name());
        resultVO.setErrorMessage("");
        return resultVO;
    }

    public static <T> ResultVO<T> ok(T data) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(HttpStatus.OK.value());
        resultVO.setData(data);
        resultVO.setMessage(HttpStatus.OK.name());
        resultVO.setErrorMessage("");
        return resultVO;
    }

    public static <T> ResultVO<T> ok(T data, String message) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(HttpStatus.OK.value());
        resultVO.setData(data);
        resultVO.setMessage(message);
        resultVO.setErrorMessage("");
        return resultVO;
    }

    public static ResultVO<?> notFound() {
        ResultVO<?> resultVO = new ResultVO<>();
        resultVO.setCode(HttpStatus.NOT_FOUND.value());
        resultVO.setMessage(HttpStatus.NOT_FOUND.name());
        resultVO.setErrorMessage(HttpStatus.NOT_FOUND.name());
        return resultVO;
    }

    public static ResultVO<?> notFound(String errorMessage) {
        ResultVO<?> resultVO = new ResultVO<>();
        resultVO.setCode(HttpStatus.NOT_FOUND.value());
        resultVO.setMessage(HttpStatus.NOT_FOUND.name());
        resultVO.setErrorMessage(errorMessage);
        return resultVO;
    }

    public static ResultVO<?> error() {
        ResultVO<?> resultVO = new ResultVO<>();
        resultVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resultVO.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.name());
        resultVO.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.name());
        return resultVO;
    }

    public static ResultVO<?> error(int code, String errorMessage) {
        ResultVO<?> resultVO = new ResultVO<>();
        resultVO.setCode(HttpStatus.valueOf(code).value());
        resultVO.setMessage(HttpStatus.valueOf(code).name());
        resultVO.setErrorMessage(errorMessage);
        return resultVO;
    }

    public static ResultVO<?> error(String errorMessage) {
        ResultVO<?> resultVO = new ResultVO<>();
        resultVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resultVO.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.name());
        resultVO.setErrorMessage(errorMessage);
        return resultVO;
    }
}
