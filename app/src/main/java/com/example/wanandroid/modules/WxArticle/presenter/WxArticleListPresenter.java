package com.example.wanandroid.modules.WxArticle.presenter;

import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.core.DataManager;
import com.example.wanandroid.core.event.CollectEvent;
import com.example.wanandroid.modules.WxArticle.contract.WxArticleListContract;
import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.main.presenter.CollectEventPresenter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WxArticleListPresenter extends CollectEventPresenter<WxArticleListContract.View> implements WxArticleListContract.Presenter {

    private int curPage = 1;
    private boolean isRefresh = true;
    private int id;


    @Override
    public void getArticleList(boolean isShowLoading) {
        addSubscribe(apiService
                .getWxArticles(id, curPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(articleListData -> mView != null)
                .subscribeWith(new BaseObserver<ArticleListData>(mView,
                        "Failed to get articles," +
                                isShowLoading) {
                    @Override
                    public void onSuccess(ArticleListData articleListData) {
                        mView.showArticleList(articleListData, isRefresh);
                    }
                }));

    }

    @Override
    public void refreshLayout(int id, boolean isShowLoading) {
        isRefresh = true;
        curPage = 1;
        this.id = id;
        getArticleList(isShowLoading);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        curPage++;
        getArticleList(false);
    }

    @Override
    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = Constants.WX_PAGER)
    public void collectEvent(CollectEvent collectEvent) {
        if (mView == null) return;
        if (collectEvent.isCollect()) {
            mView.showCollectSuccess(collectEvent.getPosition());
        } else {
            mView.showCancelCollectSuccess(collectEvent.getPosition());
        }
    }
}
