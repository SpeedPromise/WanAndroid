package com.example.wanandroid.modules.WxArticle.contract;

import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.main.contract.CollectEventContract;

public interface WxArticleListContract {
    interface View extends CollectEventContract.View {
        void showArticleList(ArticleListData articleListData, boolean isRefresh);
    }

    interface Presenter extends CollectEventContract.Presenter<View> {
        void getArticleList(boolean isShowLoading);

        void refreshLayout(int id, boolean isShowLoading);

        void loadMore();
    }
}
