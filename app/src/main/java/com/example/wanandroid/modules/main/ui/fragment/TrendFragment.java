package com.example.wanandroid.modules.main.ui.fragment;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.modules.main.contract.TrendContract;
import com.example.wanandroid.modules.main.presenter.TrendPresenter;

public class TrendFragment extends BaseFragment<TrendPresenter> implements TrendContract.View {

    public static TrendFragment newInstance() {
        return new TrendFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trend;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void createPresenter() {
        mPresenter = new TrendPresenter();
    }

    @Override
    protected void lazyLoading() {

    }
}
