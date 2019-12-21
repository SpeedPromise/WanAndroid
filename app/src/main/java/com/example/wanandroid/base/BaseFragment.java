package com.example.wanandroid.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.wanandroid.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IView {

    private boolean isViewPrepared;
    private boolean hasFetchData;

    private Unbinder unbinder;

    protected T mPresenter;

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void createPresenter();

    protected abstract void lazyLoading();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }



    private void lazyLoadingIfPrepared() {
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyLoading();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoadingIfPrepared();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mPresenter == null) {
            createPresenter();
        }

        if(mPresenter != null){
            mPresenter.attachView(this);
        }

        isViewPrepared = true;
        lazyLoadingIfPrepared();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewPrepared = false;
        hasFetchData = false;

        unbinder.unbind();

        if(mPresenter != null) {
            mPresenter.detachView();
        }
        hideLoading();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null) {
            mPresenter = null;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMsg(String Msg) {
        ToastUtils.showToast(getActivity(), Msg);
    }


}
