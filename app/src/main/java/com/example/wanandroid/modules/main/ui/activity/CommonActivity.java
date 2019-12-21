package com.example.wanandroid.modules.main.ui.activity;

import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.wanandroid.R;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.base.IView;
import com.example.wanandroid.modules.main.ui.fragment.AboutFragment;
import com.example.wanandroid.modules.main.ui.fragment.CollectFragment;
import com.example.wanandroid.modules.main.ui.fragment.SettingFragment;
import com.example.wanandroid.modules.main.ui.fragment.TrendFragment;
import com.example.wanandroid.modules.main.ui.fragment.UsefulSitesFragment;

import butterknife.BindView;

public class CommonActivity extends BaseActivity<BasePresenter> implements IView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;

    Fragment curFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void initView() {
        String type = getIntent().getStringExtra(Constants.TYPE_FRAGMENT_KEY);
        String title = "";
        switch (type) {
            case Constants.TYPE_COLLECT:
                curFragment = CollectFragment.newInstance();
                title = getString(R.string.favorites);
                break;
            case Constants.TYPE_TREND:
                curFragment = TrendFragment.newInstance();
                title = getString(R.string.trend);
                break;
            case Constants.TYPE_SETTING:
                curFragment = SettingFragment.newInstance();
                title = getString(R.string.settings);
                break;
            case Constants.TYPE_ABOUT:
                curFragment = AboutFragment.newInstance();
                title = getString(R.string.about);
                break;
            case Constants.TYPE_USEFULSITES:
                curFragment = UsefulSitesFragment.newInstance();
                title = getString(R.string.action_websites);
                break;
            default:
                break;
        }

        if (curFragment == null) {
            finish();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.common_frame, curFragment)
                    .commit();

            mTitle.setText(title);
        }

    }

    @Override
    protected void createPresenter() {

    }

}
