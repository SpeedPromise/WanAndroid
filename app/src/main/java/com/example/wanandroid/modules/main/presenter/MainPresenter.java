package com.example.wanandroid.modules.main.presenter;

import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.core.event.LoginEvent;
import com.example.wanandroid.core.event.LogoutEvent;
import com.example.wanandroid.modules.login.bean.LoginData;
import com.example.wanandroid.modules.main.bean.UserInfoData;
import com.example.wanandroid.modules.main.contract.MainContract;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    @Override
    public void logout() {
        addSubscribe(apiService.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(loginData -> mView != null)
                .subscribeWith(new BaseObserver<LoginData>(mView,
                        "Failed to logout",
                        false) {
                    @Override
                    public void onSuccess(LoginData loginData) {
                        setLoginStatus(false);
                        setLoginAccount("登录");
                        EventBus.getDefault().post(new LogoutEvent());
                        mView.handleLogoutSuccess();
                    }
                })
        );
    }

    @Override
    public void getUserInfo() {
        addSubscribe(apiService.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(userInfoData -> mView != null)
                .subscribeWith(new BaseObserver<UserInfoData>(mView,
                        "Failed to get points", false) {
                    @Override
                    public void onSuccess(UserInfoData userInfoData) {
                        mView.setCoinCount(userInfoData);
                    }
                })
        );
    }

    @Override
    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Subscriber()
    public void loginEvent(LoginEvent loginEvent) {
        mView.handleLoginSuccess();
    }

    @Override
    public void setNightMode(boolean isNightMode) {

    }

    @Override
    public boolean isNightMode() {
        return false;
    }
}
