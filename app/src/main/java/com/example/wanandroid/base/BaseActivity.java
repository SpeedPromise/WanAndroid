package com.example.wanandroid.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroid.R;
import com.example.wanandroid.utils.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements IView{
    private Unbinder unbinder;

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ImmersionBar.with(this)
                .statusBarView(findViewById(R.id.status_bar_view))
                .keyboardEnable(true)
                .init();

        unbinder = ButterKnife.bind(this);
        onViewCreated();
        initToolbar();
        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initToolbar();

    protected abstract void initView();

    protected abstract void createPresenter();

    protected void onViewCreated(){

        if (mPresenter == null) {
            createPresenter();
        }

        if(mPresenter != null){
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null ) {
            unbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        hideLoading();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMsg(String Msg) {
        ToastUtils.showToast(this, Msg);
    }


}
