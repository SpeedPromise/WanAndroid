package com.example.wanandroid.modules.main.presenter;

import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.core.BaseObserver;
import com.example.wanandroid.modules.main.bean.UsefulSiteData;
import com.example.wanandroid.modules.main.contract.UsefulSitesContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UsefulSitesPresenter extends BasePresenter<UsefulSitesContract.View> implements UsefulSitesContract.Presenter {
    @Override
    public void getUsefulSites() {
        addSubscribe(apiService.getUserfulSiteData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<List<UsefulSiteData>>(mView,
                        "Failed to get usefulsite data",
                        false) {
                    @Override
                    public void onSuccess(List<UsefulSiteData> usefulSiteData) {
                        mView.showUsefulSites(usefulSiteData);
                    }
                })
        );
    }
}
