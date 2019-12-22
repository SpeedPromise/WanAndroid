package com.example.wanandroid.modules.home.contract;

import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.home.banner.BannerData;
import com.example.wanandroid.modules.main.contract.CollectEventContract;

import java.util.List;

public interface HomeContract {

    interface View extends CollectEventContract.View {

        void showArticleList(ArticleListData data, boolean isRefresh);

        void showBanner(List<BannerData> bannerData);
    }

    interface Presenter extends CollectEventContract.Presenter<View> {

        void getBannerData(boolean isShowLoading);

        void getArticleList(boolean isShowLoading);

        void getHomeData(boolean isShowLoading);

        void refreshLayout(boolean isShowLoading);

        void loadMore();
    }
}
