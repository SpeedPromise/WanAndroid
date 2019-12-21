package com.example.wanandroid.modules.login.ui;

import android.view.View;
import android.widget.EditText;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.modules.login.contract.RegisterFragmentContract;
import com.example.wanandroid.modules.login.presenter.RegisterFragmentPresenter;
import com.example.wanandroid.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragment<RegisterFragmentPresenter> implements RegisterFragmentContract.View {

    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.password2)
    EditText mPassword2;


    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void createPresenter() {
        mPresenter = new RegisterFragmentPresenter();
    }
    @OnClick({R.id.go_register, R.id.btn_register})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_register:
                ((LoginActivity) getActivity()).changeView(0);
                break;
            case R.id.btn_register:
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String password2 = mPassword2.getText().toString();
                mPresenter.register(username, password, password2);
                break;
        }
    }

    @Override
    protected void lazyLoading() {

    }

    @Override
    public void registerSuccess() {
        ToastUtils.showToast(getContext(), "login success");
        ((LoginActivity) getActivity()).changeView(0);
    }
}
