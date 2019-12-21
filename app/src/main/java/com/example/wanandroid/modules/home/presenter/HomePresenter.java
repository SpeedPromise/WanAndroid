package com.example.wanandroid.modules.home.presenter;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.core.event.CollectEvent;
import com.example.wanandroid.core.event.LoginEvent;
import com.example.wanandroid.core.event.LogoutEvent;
import com.example.wanandroid.core.event.RefreshHomeEvent;
import com.example.wanandroid.modules.home.contract.HomeContract;
import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.main.presenter.CollectEventPresenter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends CollectEventPresenter<HomeContract.View> implements HomeContract.Presenter {

    private int curPage;
    private boolean isRefresh = true;

    @Override
    public void getArticleList(boolean isShowLoading) {
        addSubscribe(apiService.getArticleList(curPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(articleListData -> mView != null)
                .subscribeWith(new BaseObserver<ArticleListData>(mView,
                        "Failed to get the list of articles",
                        isShowLoading) {
                    @Override
                    public void onSuccess(ArticleListData articleListData) {
                        mView.showArticleList(articleListData, isRefresh);
                    }
                }));
    }

    @Override
    public void getHomeData(boolean isShowLoading) {
        addSubscribe(Observable.zip(apiService.getTopArticles(), apiService.getArticleList(curPage), (topArticlesBaseResponse, articleListBaseResponse) -> {
            articleListBaseResponse.getData().getDatas().addAll(0, topArticlesBaseResponse.getData());
            return articleListBaseResponse;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(articleListData -> mView != null)
                .subscribeWith(new BaseObserver<ArticleListData>(mView,
                        "Failed to get the list of articles",
                        isShowLoading) {
                    @Override
                    public void onSuccess(ArticleListData articleListData) {
                        mView.showArticleList(articleListData, isRefresh);
                    }
                }));
    }

    @Override
    public void refreshLayout(boolean isShowLoading) {
        isRefresh = true;
        curPage = 0;
        getHomeData(isShowLoading);
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

    @Subscriber()
    public void logoutEvent (LogoutEvent logoutEvent) {
        getHomeData(false);
    }

    @Subscriber()
    public void loginEvent (LoginEvent loginEvent) {
        getHomeData(false);
    }

    @Subscriber()
    public void refreshHomeEvent (RefreshHomeEvent refreshHomeEvent) {
        getHomeData(false);
    }

    @Subscriber(tag = Constants.MAIN_PAGER)
    public void collectEvent (CollectEvent collectEvent) {
        if (collectEvent.isCollect()) {
            mView.showCollectSuccess(collectEvent.getPosition());
        } else {
            mView.showCancelCollectSuccess(collectEvent.getPosition());
        }
    }

}
