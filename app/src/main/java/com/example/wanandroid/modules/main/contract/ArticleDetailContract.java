package com.example.wanandroid.modules.main.contract;

public interface ArticleDetailContract {
    interface View extends CollectEventContract.View {
        void showArticle();

        void showError();
    }

    interface Presenter extends CollectEventContract.Presenter<View> {
        void share();
    }
}
