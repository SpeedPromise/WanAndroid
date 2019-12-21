package com.example.wanandroid.modules.main.contract;

import com.example.wanandroid.base.IPresenter;
import com.example.wanandroid.base.IView;
import com.example.wanandroid.modules.main.bean.UsefulSiteData;

import java.util.List;

public interface UsefulSitesContract {
    interface View extends IView {
        void showUsefulSites(List<UsefulSiteData> usefulSiteData);
    }

    interface Presenter extends IPresenter<View> {
        void getUsefulSites();
    }
}
