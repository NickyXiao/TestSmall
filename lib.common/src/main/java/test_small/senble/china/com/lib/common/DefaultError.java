package test_small.senble.china.com.lib.common;

/**
 * Created by Administrator on 2017/9/4.
 */

public class DefaultError implements IUserCase.ErrorValue {
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int statusCode = -1;
    private String message = "";

    public DefaultError(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.message = errorMessage;
    }
}
