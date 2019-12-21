package com.example.wanandroid.modules.main.contract;

import com.example.wanandroid.base.IPresenter;
import com.example.wanandroid.base.IView;

public interface CollectEventContract {

    interface View extends IView {
        void showCollectSuccess(int position);
        void showCancelCollectSuccess(int position);
    }

    interface Presenter<V extends View> extends IPresenter<V> {
        void addCollectArticle(int position, int id);
        void cancelCollectArticle(int position, int id);
    }
}
