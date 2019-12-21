package com.example.wanandroid.modules.main.contract;

import com.example.wanandroid.base.IPresenter;
import com.example.wanandroid.base.IView;
import com.example.wanandroid.modules.main.bean.UserInfoData;

public interface MainContract {
    interface View extends IView {
        void handleLoginSuccess();
        void handleLogoutSuccess();
        void setCoinCount(UserInfoData userInfoData);
    }
    interface Presenter extends IPresenter<View> {
        void getUserInfo();
        void logout();
        void setNightMode(boolean isNightMode);
        boolean isNightMode();
    }
}
