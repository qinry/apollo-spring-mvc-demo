package demo.exception;

public enum BusinessExceptionEnum {
    CUSTOMER_NOT_FOUND(404, "customer not found");

    private int code;
    private String errorMessage;

    BusinessExceptionEnum(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
