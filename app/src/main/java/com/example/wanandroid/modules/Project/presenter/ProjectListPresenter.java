package com.example.wanandroid.modules.Project.presenter;

import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.core.DataManager;
import com.example.wanandroid.core.event.CollectEvent;
import com.example.wanandroid.modules.Project.contract.ProjectListContract;
import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.main.presenter.CollectEventPresenter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectListPresenter extends CollectEventPresenter<ProjectListContract.View> implements ProjectListContract.Presenter {

    private int curPage = 1;
    private int id;
    private boolean isRefresh = true;

    @Override
    public void getProjectList(boolean isShowLoading) {
        addSubscribe(apiService
                .getProjectList(curPage, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(articleListDataBaseResponse -> mView != null)
                .subscribeWith(new BaseObserver<ArticleListData>(mView,
                        "Failed to get Articles",
                        isShowLoading
                        ) {
                    @Override
                    public void onSuccess(ArticleListData articleListData) {
                        mView.showProjectList(articleListData, isRefresh);
                    }
                })
        );
    }

    @Override
    public void refreshLayout(int id, boolean isShowLoading) {
        isRefresh = true;
        curPage = 1;
        this.id = id;
        getProjectList(isShowLoading);
    }

    @Override
    public void loadMore() {
        curPage++;
        isRefresh = false;
        getProjectList(false);
    }

    @Override
    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = Constants.PROJECT_PAGER)
    public void collectEvent(CollectEvent collectEvent) {
        if (mView == null) return;
        if (collectEvent.isCollect()) {
            mView.showCollectSuccess(collectEvent.getPosition());
        } else {
            mView.showCancelCollectSuccess(collectEvent.getPosition());
        }
    }
}
