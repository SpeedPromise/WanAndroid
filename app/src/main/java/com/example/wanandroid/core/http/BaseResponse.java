package com.example.wanandroid.core.http;

public class BaseResponse<T> {

    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;

    //success ? 1 : 0
    private int errorCode;
    private String ErrorMsg;
    private  T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
