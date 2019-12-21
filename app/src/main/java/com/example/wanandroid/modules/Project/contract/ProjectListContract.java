package com.example.wanandroid.modules.Project.contract;

import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.main.contract.CollectEventContract;

public interface ProjectListContract {
    interface View extends CollectEventContract.View {
        void showProjectList(ArticleListData articleListData, boolean isRefresh);
    }

    interface Presenter extends CollectEventContract.Presenter<View> {
        void getProjectList(boolean isShowLoading);

        void refreshLayout(int id, boolean isShowLoading);

        void loadMore();
    }
}
