package com.example.wanandroid.modules.main.ui.fragment;

import android.view.View;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.base.IView;

import butterknife.OnClick;

public class AboutFragment extends BaseFragment<BasePresenter> implements IView {

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void createPresenter() {

    }

    @Override
    protected void lazyLoading() {

    }


    @OnClick()
    void onClick(View view){

    }
}
