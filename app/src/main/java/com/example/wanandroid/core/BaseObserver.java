package com.example.wanandroid.core;

import android.util.Log;

import com.example.wanandroid.base.IView;
import com.example.wanandroid.core.http.BaseResponse;

import io.reactivex.observers.ResourceObserver;

public abstract class BaseObserver<T> extends ResourceObserver<BaseResponse<T>> {

    private static final String TAG = "BaseObserver";

    private IView mView;
    private String mErrorMsg;
    private boolean isShowLoading = true;

    protected BaseObserver(IView view) {
        this.mView = view;
    }

    protected BaseObserver(IView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected BaseObserver(IView view, String errorMsg, boolean isShowLoading) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowLoading = isShowLoading;
    }

    public abstract void onSuccess(T t);

    public void onFailure(int code, String msg) {
        mView.showErrorMsg(msg);
    }

    @Override
    protected void onStart() {
        if (isShowLoading) {
            mView.showLoading();
        }
    }

    @Override
    public void onNext(BaseResponse<T> tBaseResponse) {
        if (tBaseResponse.getErrorCode() == BaseResponse.SUCCESS) {
            if (isShowLoading) {
                mView.hideLoading();
            }
            onSuccess(tBaseResponse.getData());
        } else {
            if (isShowLoading) {
                mView.hideLoading();
            }
            onFailure(tBaseResponse.getErrorCode(), tBaseResponse.getErrorMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        } else {
            Log.d(TAG, "onError: "+e.toString());
            mView.showErrorMsg(mErrorMsg);
        }
        if (isShowLoading) {
            mView.hideLoading();
        }
    }

    @Override
    public void onComplete() {
        if (mView == null) {
            return;
        }
    }
}
