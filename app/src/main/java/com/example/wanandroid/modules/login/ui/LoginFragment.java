package com.example.wanandroid.modules.login.ui;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.core.event.RegisterEvent;
import com.example.wanandroid.modules.login.contract.LoginFragmentContract;
import com.example.wanandroid.modules.login.presenter.LoginFragmentPresenter;
import com.example.wanandroid.utils.ToastUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment<LoginFragmentPresenter> implements LoginFragmentContract.View {

    @BindView(R.id.go_register)
    LinearLayout mLinearLayout;
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void createPresenter() {
        mPresenter = new LoginFragmentPresenter();
    }

    @Override
    protected void lazyLoading() {

    }

    @OnClick({R.id.go_register, R.id.btn_login})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_register:
                ((LoginActivity) getActivity()).changeView(1);
                break;
            case R.id.btn_login:
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                if (username.isEmpty()) {
                    ToastUtils.showToast(getContext(), getString(R.string.username_not_empty));
                }
                if (password.isEmpty()) {
                    ToastUtils.showToast(getContext(), getString(R.string.password_not_empty));
                }
                mPresenter.login(username, password);
                break;
        }
    }

    @Override
    public void loginSuccess() {
        ToastUtils.showToast(getContext(), "login success");
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void registerSuccess(RegisterEvent registerEvent) {
        mUsername.setText(registerEvent.getUsername());
        mPassword.setText(registerEvent.getPassword());
    }
}
