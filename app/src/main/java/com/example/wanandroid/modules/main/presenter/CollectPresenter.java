package com.example.wanandroid.modules.main.presenter;

import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.core.event.CollectEvent;
import com.example.wanandroid.core.event.RefreshHomeEvent;
import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.main.contract.CollectContract;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CollectPresenter extends CollectEventPresenter<CollectContract.View> implements CollectContract.Presenter {

    private int curPage = 0;
    private boolean isRefresh = true;

    @Override
    public void getCollectList(boolean isShowLoading) {
        addSubscribe(apiService.getCollectList(curPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(collection -> mView != null)
                .subscribeWith(new BaseObserver<ArticleListData>(mView,
                        "Failed to get collection of articles",
                        isShowLoading) {
                    @Override
                    public void onSuccess(ArticleListData collection) {
                        mView.showCollectList(collection, isRefresh);
                    }
                })
        );
    }

    @Override
    public void cancelCollectInPage(int position, int id, int originId) {
        addSubscribe(apiService.cancelCollectInPage(id, originId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(collection -> mView != null)
                .subscribeWith(new BaseObserver<ArticleListData>(mView,
                        "Failed to cancel collection of article",
                        false) {
                    @Override
                    public void onSuccess(ArticleListData collection) {
                        mView.showCancelCollectSuccess(position);
                        EventBus.getDefault().post(new RefreshHomeEvent());
                    }
                })
        );
    }

    @Override
    public void loadMore() {
        curPage++;
        isRefresh = false;
        getCollectList(false);
    }

    @Override
    public void refreshLayout(boolean isShowLoading) {
        isRefresh = true;
        curPage = 0;
        getCollectList(isShowLoading);
    }

    @Override
    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = Constants.COLLECT_PAGER)
    public void collectEvent (CollectEvent collectEvent) {
        if (collectEvent.isCollect()) {
            mView.showCollectSuccess(collectEvent.getPosition());
        } else {
            mView.showCancelCollectSuccess(collectEvent.getPosition());
        }
        EventBus.getDefault().post(new RefreshHomeEvent());
    }
}
