package com.example.wanandroid.modules.WxArticle.presenter;

import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.core.DataManager;
import com.example.wanandroid.modules.WxArticle.bean.WxArticleChapterData;
import com.example.wanandroid.modules.WxArticle.contract.WxArticleContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class WxArticlePresenter extends BasePresenter<WxArticleContract.View> implements WxArticleContract.Presenter {

    @Override
    public void getWxArticleChapters() {
        addSubscribe(apiService
                .getWxArticleChapters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(wxArticleChapters -> mView != null)
                .subscribeWith(new BaseObserver<List<WxArticleChapterData>>(mView,
                        "Failed to get WxArticle Chapters",
                        true) {
                    @Override
                    public void onSuccess(List<WxArticleChapterData> wxArticleChapterData) {
                        mView.showWxArticleChapters(wxArticleChapterData);
                    }
                }));
    }
}