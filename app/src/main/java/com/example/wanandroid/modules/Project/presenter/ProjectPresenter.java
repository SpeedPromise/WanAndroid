package com.example.wanandroid.modules.Project.presenter;

import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.core.DataManager;
import com.example.wanandroid.modules.Project.bean.ProjectTreeData;
import com.example.wanandroid.modules.Project.contract.ProjectContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    @Override
    public void getProjectTreeData() {
        addSubscribe(apiService
                .getProjectTree()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(projectTreeData -> mView != null)
                .subscribeWith(new BaseObserver<List<ProjectTreeData>>(mView,
                        "Failed to get project tree data",
                        true) {
                    @Override
                    public void onSuccess(List<ProjectTreeData> projectTreeData) {
                        mView.showProjectTreeData(projectTreeData);
                    }
                })
        );
    }
}
