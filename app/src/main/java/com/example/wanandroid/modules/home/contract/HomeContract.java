package com.example.wanandroid.modules.home.contract;

import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.main.contract.CollectEventContract;

public interface HomeContract {

    interface View extends CollectEventContract.View {

        void showArticleList(ArticleListData data, boolean isRefresh);

//        void showBanner();
    }

    interface Presenter extends CollectEventContract.Presenter<View> {

        void getArticleList(boolean isShow);

        void getHomeData(boolean isShow);

        void refreshLayout(boolean isShow);

        void loadMore();
    }
}
