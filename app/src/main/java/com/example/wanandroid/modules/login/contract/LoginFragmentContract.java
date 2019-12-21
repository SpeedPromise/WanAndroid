package com.example.wanandroid.modules.login.contract;

import com.example.wanandroid.base.IPresenter;
import com.example.wanandroid.base.IView;
import com.example.wanandroid.core.event.RegisterEvent;

public interface LoginFragmentContract {
    interface View extends IView {
        void loginSuccess();
        void registerSuccess(RegisterEvent registerEvent);
    }

    interface Presenter extends IPresenter<View> {
        void login(String username, String password);
    }
}
