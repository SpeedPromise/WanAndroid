package com.example.wanandroid.modules.Hierarchy.presenter;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.core.event.CollectEvent;
import com.example.wanandroid.modules.Hierarchy.contract.KnowledgeArticlesContract;
import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.main.presenter.CollectEventPresenter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KnowledgeArticlesPresenter extends CollectEventPresenter<KnowledgeArticlesContract.View>
    implements KnowledgeArticlesContract.Presenter{

    private int curPage = 0;
    private boolean isRefresh = true;
    private int cid;

    @Override
    public void refreshLayout(int cid, boolean isShowLoading) {
        this.cid = cid;
        curPage = 0;
        isRefresh = true;
        getKnowledgeArticles(isShowLoading);
    }

    @Override
    public void getKnowledgeArticles(boolean isShowLoading) {
        addSubscribe(apiService
                .getKnowledgeArticles(curPage, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ArticleListData>(mView,
                        "Filed to get knowledge hierarchy articles data",
                        isShowLoading) {
                    @Override
                    public void onSuccess(ArticleListData articleListData) {
                        mView.showKnowledgeArticles(articleListData, isRefresh);
                    }
                }));
    }

    @Override
    public void loadMore() {
        curPage++;
        isRefresh = false;
        getKnowledgeArticles(false);
    }

    @Override
    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }


    @Subscriber(tag = Constants.KNOWLEDGE_PAGER)
    public void collectEvent (CollectEvent collectEvent) {
        if (collectEvent.isCollect()) {
            mView.showCollectSuccess(collectEvent.getPosition());
        } else {
            mView.showCancelCollectSuccess(collectEvent.getPosition());
        }
    }
}
