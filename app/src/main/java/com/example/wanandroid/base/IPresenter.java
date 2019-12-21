package com.example.wanandroid.base;

public interface IPresenter<T extends IView> {

    void attachView(T view);

    void detachView();

    void registerEventBus();

    void unregisterEventBus();

    void setLoginStatus(boolean loginStatus);

    boolean getLoginStatus();

    String getLoginAccount();

    void setLoginAccount(String account);
}
