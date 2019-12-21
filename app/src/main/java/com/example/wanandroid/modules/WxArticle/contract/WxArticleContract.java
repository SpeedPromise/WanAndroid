package com.example.wanandroid.modules.WxArticle.contract;

import com.example.wanandroid.base.IPresenter;
import com.example.wanandroid.base.IView;
import com.example.wanandroid.modules.WxArticle.bean.WxArticleChapterData;

import java.util.List;

public interface WxArticleContract {
    interface View extends IView {
        void showWxArticleChapters(List<WxArticleChapterData> wxArticleChapters);
    }

    interface Presenter extends IPresenter<View> {
        void getWxArticleChapters();
    }
}
