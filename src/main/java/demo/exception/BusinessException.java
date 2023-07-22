package demo.exception;

public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
    }

    public BusinessException(BusinessExceptionEnum exceptionEnum) {
        super(exceptionEnum.getErrorMessage());
        this.code = exceptionEnum.getCode();
    }

    public BusinessException(BusinessExceptionEnum exceptionEnum, String extend) {
        super(exceptionEnum.getErrorMessage() + ":" + extend);
        this.code = exceptionEnum.getCode();
    }

    public int getCode() {
        return code;
    }
}
