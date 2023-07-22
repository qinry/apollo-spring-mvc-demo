package demo.controller;

import demo.util.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping
    public ResponseEntity<ResultVO<?>> error(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String uri = (String)request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        ResultVO<?> error;
        if (statusCode == null) {
            error = ResultVO.error();
            error.setUrl(uri);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
        error = ResultVO.error(statusCode,
                HttpStatus.valueOf(statusCode).name());
        error.setUrl(uri);
        return ResponseEntity.status(HttpStatus.valueOf(statusCode)).body(error);
    }
}
