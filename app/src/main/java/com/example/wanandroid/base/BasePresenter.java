package com.example.wanandroid.base;


import com.example.wanandroid.core.DataManager;
import com.example.wanandroid.core.http.api.ApiService;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<T extends IView> implements IPresenter<T> {

    protected T mView;
    private DataManager mDataManager = new DataManager();
    public ApiService apiService = mDataManager.createServer();

    private CompositeDisposable compositeDisposable;

    @Override
    public void attachView(T view) {
        this.mView = view;
        registerEventBus();
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (compositeDisposable != null){
            compositeDisposable.clear();
        }
        unregisterEventBus();
    }

    @Override
    public void registerEventBus() {

    }

    @Override
    public void unregisterEventBus() {

    }

    @Override
    public void setLoginStatus(boolean loginStatus) {
        mDataManager.setLoginStatus(loginStatus);
    }

    @Override
    public boolean getLoginStatus() {
        return mDataManager.getLoginStatus();
    }

    @Override
    public String getLoginAccount() {
        return mDataManager.getLoginAccount();
    }

    @Override
    public void setLoginAccount(String account) {
        mDataManager.setLoginAccount(account);
    }

    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
