package com.example.wanandroid.modules.main.presenter;

import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.modules.main.bean.ArticleItemData;
import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.main.contract.CollectEventContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CollectEventPresenter<V extends CollectEventContract.View>
        extends BasePresenter<V> implements CollectEventContract.Presenter<V> {

    @Override
    public void addCollectArticle(int position, int id) {
            addSubscribe(apiService.addCollectArticle(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(articleListData -> mView != null)
                    .subscribeWith(new BaseObserver<ArticleListData>(mView,
                            "Failed to add collection",
                            false) {
                        @Override
                        public void onSuccess(ArticleListData articleListData) {
                            mView.showCollectSuccess(position);
                        }
                    })
            );
    }

    @Override
    public void cancelCollectArticle(int position, int id) {
        addSubscribe(apiService.cancelCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(articleListData -> mView != null)
                .subscribeWith(new BaseObserver<ArticleListData>(mView,
                        "Failed to Cancel collection",
                        false) {
                    @Override
                    public void onSuccess(ArticleListData articleListData) {
                        mView.showCancelCollectSuccess(position);
                    }
                })
        );
    }


}
