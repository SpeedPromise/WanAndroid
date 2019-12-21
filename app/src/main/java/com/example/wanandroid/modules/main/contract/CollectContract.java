package com.example.wanandroid.modules.main.contract;

import com.example.wanandroid.modules.main.bean.ArticleListData;

public interface CollectContract {
    interface View extends CollectEventContract.View {
        void showCollectList(ArticleListData collection, boolean isRefresh);
    }

    interface Presenter extends CollectEventContract.Presenter<View> {
        void getCollectList(boolean isShowLoading);
        void loadMore();
        void refreshLayout(boolean isShowLoading);
        void cancelCollectInPage(int position, int id, int originId);
    }
}
