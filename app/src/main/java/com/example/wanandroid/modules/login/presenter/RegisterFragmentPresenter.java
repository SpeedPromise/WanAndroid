package com.example.wanandroid.modules.login.presenter;

import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.core.event.RegisterEvent;
import com.example.wanandroid.modules.login.bean.LoginData;
import com.example.wanandroid.modules.login.contract.RegisterFragmentContract;


import org.simple.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterFragmentPresenter extends BasePresenter<RegisterFragmentContract.View> implements RegisterFragmentContract.Presenter {

    @Override
    public void register(String username, String password, String password2) {
        addSubscribe(apiService
                .register(username, password, password2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(loginData -> mView != null)
                .subscribeWith(new BaseObserver<LoginData>(mView,
                        "register failed",
                        true) {
                    @Override
                    public void onSuccess(LoginData loginData) {
                        EventBus.getDefault().post(new RegisterEvent(loginData.getUsername(), loginData.getPassword()));
                        mView.registerSuccess();
                    }
                })
        );
    }
}
