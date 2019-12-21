package com.example.wanandroid.modules.Hierarchy.presenter;

import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.core.DataManager;
import com.example.wanandroid.modules.Hierarchy.bean.KnowledgeTreeData;
import com.example.wanandroid.modules.Hierarchy.contract.KnowledgeContract;

import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KnowledgePresenter extends BasePresenter<KnowledgeContract.View> implements KnowledgeContract.Presenter{

    @Override
    public void getKnowledgeTree() {

        addSubscribe(apiService
                .getKnowledgeTree()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<List<KnowledgeTreeData>>(mView,
                        "Failed to get knowledge hierarchy data",
                        true) {
                    @Override
                    public void onSuccess(List<KnowledgeTreeData> knowledgeTreeData) {
                        mView.showKnowledgeTree(knowledgeTreeData);
                    }
                }));
    }
}
