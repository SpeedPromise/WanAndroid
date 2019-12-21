package com.example.wanandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.modules.Hierarchy.ui.KnowledgeFragment;
import com.example.wanandroid.modules.Project.ui.ProjectFragment;
import com.example.wanandroid.modules.WxArticle.ui.WxArticleFragment;
import com.example.wanandroid.modules.home.ui.HomeFragment;
import com.example.wanandroid.modules.main.bean.UserInfoData;
import com.example.wanandroid.modules.main.contract.MainContract;
import com.example.wanandroid.modules.main.presenter.MainPresenter;
import com.example.wanandroid.modules.main.ui.activity.CommonActivity;
import com.example.wanandroid.utils.CommonUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private static final String TAG = "MainActivity";

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.nav_bottom)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.fragment_group)
    FrameLayout mFrameGroup;

    private HomeFragment mHomeFragment;
    private KnowledgeFragment mKnowledgeFragment;
    private WxArticleFragment mWxArticleFragment;
    private ProjectFragment mProjectFragment;

    TextView mAccount;
    TextView mPoints;
    private FragmentManager fm;
    private String mCurrentFgTag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new MainPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentFgTag = savedInstanceState.getString(Constants.CURRENT_FRAGMENT_TAG);
        }
        super.onCreate(savedInstanceState);

//        startActivity(new Intent(this, LoginActivity.class));
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(Constants.CURRENT_FRAGMENT_TAG, mCurrentFgTag);
    }

    @Override
    protected void initView() {
        fm = getSupportFragmentManager();
        initDrawer();
        initNavigationView();
        initBottomNavigationView();
        initFragments();

        mPresenter.getUserInfo();
        if (mPresenter.getLoginStatus()) {
            handleLoginSuccess();
        } else {
            handleLogoutSuccess();
        }
    }

    private void initFragments() {
        mHomeFragment = HomeFragment.newInstance();
        mKnowledgeFragment = KnowledgeFragment.newInstance();
        mWxArticleFragment = WxArticleFragment.newInstance();
        mProjectFragment = ProjectFragment.newInstance();

        FragmentTransaction ft = fm.beginTransaction();
        if (!mHomeFragment.isAdded()) {
            ft.add(R.id.fragment_group, mHomeFragment, Constants.TYPE_HOME);
//                    .hide(mHomeFragment);
        }
        if (!mKnowledgeFragment.isAdded()) {
            ft.add(R.id.fragment_group, mKnowledgeFragment, Constants.TYPE_HIERARCHY)
                    .hide(mKnowledgeFragment);
        }
        if (!mWxArticleFragment.isAdded()) {
            ft.add(R.id.fragment_group, mWxArticleFragment, Constants.TYPE_WX_ARTICLE)
                    .hide(mWxArticleFragment);
        }
        if (!mProjectFragment.isAdded()) {
            ft.add(R.id.fragment_group, mProjectFragment, Constants.TYPE_PROJECTS)
                    .hide(mProjectFragment);
        }
        ft.commit();

        mCurrentFgTag = Constants.TYPE_HOME;
//        showFragment(Constants.TYPE_HOME);
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            mTitle.setText(R.string.home);
        }
    }

    private void showFragment(String name) {
        if (mCurrentFgTag != null && mCurrentFgTag.equals(name)) {
            return;
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        Fragment currentFragment = fm.findFragmentByTag(mCurrentFgTag);
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }
        Fragment fragment = fm.findFragmentByTag(name);
        if (fragment == null) {
            switch (name) {
                case Constants.TYPE_HOME:
                    fragment = HomeFragment.newInstance();
                    break;
                case Constants.TYPE_HIERARCHY:
                    fragment = KnowledgeFragment.newInstance();
                    break;
                case Constants.TYPE_WX_ARTICLE:
                    fragment = WxArticleFragment.newInstance();
                    break;
                case Constants.TYPE_PROJECTS:
                    fragment = ProjectFragment.newInstance();
                    break;
            }
        }
        if (fragment.isAdded()) {
            ft.show(fragment);
        } else {
            ft.add(R.id.fragment_group, fragment, name);
        }
        ft.commit();
        mCurrentFgTag = name;
    }


    private void initDrawer() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_favorites:
                    if (mPresenter.getLoginStatus())    {
                        CommonUtils.startNavFragment(this, Constants.TYPE_COLLECT);
                    } else {
                        CommonUtils.startLoginActivity(this);

                    }
                    break;
                case R.id.menu_trend:
                    CommonUtils.startNavFragment(this, Constants.TYPE_TREND);
                    break;
                case R.id.menu_setting:
                    CommonUtils.startNavFragment(this, Constants.TYPE_SETTING);
                    break;
                case R.id.menu_about:
                    CommonUtils.startNavFragment(this, Constants.TYPE_ABOUT);
                    break;
                case R.id.menu_logout:
                    mPresenter.logout();
                default:
                    break;
            }
            return true;
        });

        mAccount = mNavigationView.getHeaderView(0).findViewById(R.id.login_account);
        mPoints = mNavigationView.getHeaderView(0).findViewById(R.id.my_points);
    }

    private void initBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    mTitle.setText(R.string.home);
                    showFragment(Constants.TYPE_HOME);
                    break;
                case R.id.nav_hierarchy:
                    mTitle.setText(R.string.hierarchy);
                    showFragment(Constants.TYPE_HIERARCHY);
                    break;
                case R.id.nav_articles:
                    mTitle.setText(R.string.wx_article);
                    showFragment(Constants.TYPE_WX_ARTICLE);
                    break;
                case R.id.nav_project:
                    mTitle.setText(R.string.project);
                    showFragment(Constants.TYPE_PROJECTS);
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_websites:
                CommonUtils.startNavFragment(this, Constants.TYPE_USEFULSITES);
                break;
            case R.id.action_search:
                break;
            default:
                break;

        }
        return true;
    }

    @OnClick(R.id.floating_btn)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.floating_btn:
                backToTheTop();
                break;
            default:
                break;
        }
    }

    private void backToTheTop() {
        switch (mCurrentFgTag) {
            case Constants.TYPE_HOME:
                mHomeFragment.backToTheTop();
                break;
            case Constants.TYPE_HIERARCHY:
                mKnowledgeFragment.backToTheTop();
                break;
            case Constants.TYPE_WX_ARTICLE:
                mWxArticleFragment.backToTheTop();
                break;
            case Constants.TYPE_PROJECTS:
                mProjectFragment.backToTheTop();
                break;
            default:
                break;
        }
    }

    @Override
    public void handleLoginSuccess() {
        mAccount.setText(mPresenter.getLoginAccount());
        mAccount.setOnClickListener(null);
        mNavigationView.getMenu().findItem(R.id.menu_logout).setVisible(true);
    }

    @Override
    public void handleLogoutSuccess(){
        mAccount.setText(getString(R.string.login));
        mAccount.setOnClickListener(v -> CommonUtils.startLoginActivity(this));
        mNavigationView.getMenu().findItem(R.id.menu_logout).setVisible(false);
    }

    @Override
    public void setCoinCount(UserInfoData userInfoData) {
        mPoints.setText(userInfoData.getCoinCount() + "");
    }
}
