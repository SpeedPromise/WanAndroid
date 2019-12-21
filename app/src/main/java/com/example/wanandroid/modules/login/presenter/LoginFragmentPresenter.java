package com.example.wanandroid.modules.login.presenter;

import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.core.event.LoginEvent;
import com.example.wanandroid.core.event.RegisterEvent;
import com.example.wanandroid.modules.login.bean.LoginData;
import com.example.wanandroid.modules.login.contract.LoginFragmentContract;


import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginFragmentPresenter extends BasePresenter<LoginFragmentContract.View> implements LoginFragmentContract.Presenter {

    @Override
    public void login(String username, String password) {
        addSubscribe(apiService
                .login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(loginData -> mView != null)
                .subscribeWith(new BaseObserver<LoginData>(mView,
                        "login failed",
                        true) {
                    @Override
                    public void onSuccess(LoginData loginData) {
                        setLoginStatus(true);
                        setLoginAccount(username);
                        EventBus.getDefault().post(new LoginEvent());
                        mView.loginSuccess();
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
    public void RegisterEvent(RegisterEvent registerEvent) {
        mView.registerSuccess(registerEvent);
    }
}
