package cn.edu.nju.tickets.vo;

public class ResponseResult<T> {
    private boolean isSucceed;
    private String message;
    public T data;

    public ResponseResult(boolean isSucceed, String message, T data) {
        this.isSucceed = isSucceed;
        this.message = message;
        this.data = data;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
