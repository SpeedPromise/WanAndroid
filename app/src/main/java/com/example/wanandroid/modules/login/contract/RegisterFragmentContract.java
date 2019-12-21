package com.example.wanandroid.modules.login.contract;

import com.example.wanandroid.base.IPresenter;
import com.example.wanandroid.base.IView;

public interface RegisterFragmentContract {
    interface View extends IView {
        void registerSuccess();
    }

    interface Presenter extends IPresenter<View> {
        void register(String username, String password, String password2);
    }
}
