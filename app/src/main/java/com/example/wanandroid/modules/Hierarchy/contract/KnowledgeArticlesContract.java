package com.example.wanandroid.modules.Hierarchy.contract;

import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.main.contract.CollectEventContract;

public interface KnowledgeArticlesContract {
    interface View extends CollectEventContract.View {
        void showKnowledgeArticles(ArticleListData articleListData, boolean isRefresh);
    }

    interface Presenter extends CollectEventContract.Presenter<View> {

        void refreshLayout(int cid, boolean isShowLoading);

        void getKnowledgeArticles(boolean isShowLoading);

        void loadMore();
    }
}
