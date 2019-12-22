package com.example.wanandroid.modules.main.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.core.event.CollectEvent;
import com.example.wanandroid.modules.login.ui.LoginActivity;
import com.example.wanandroid.modules.main.bean.ArticleItemData;
import com.example.wanandroid.modules.main.contract.ArticleDetailContract;
import com.example.wanandroid.modules.main.presenter.ArticleDetailPresenter;
import com.example.wanandroid.utils.ToastUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;

import org.simple.eventbus.EventBus;

import java.lang.reflect.Method;

import butterknife.BindView;

public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenter> implements ArticleDetailContract.View {

    @BindView(R.id.container)
    CoordinatorLayout mContainer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;

    private ArticleItemData articleItem;
    private int articleId;
    private String articleLink;
    private String title;
    private boolean isShowCollectIcon;
    private boolean isCollected;
    private int itemPos;
    private String eventBusTag;
    private MenuItem mCollectItem;

    private AgentWeb mAgentWeb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new ArticleDetailPresenter();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            mTitle.setText(title);
        }

        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void initView() {
        getBundleData();

        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTitle.setText(title);
            }
        };
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(-1, -1);
        layoutParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mContainer, layoutParams)
                .useDefaultIndicator()
                .setWebChromeClient(webChromeClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .createAgentWeb()
                .ready()
                .go(articleLink);

    }

    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        articleId = bundle.getInt(Constants.ARTICLE_ID);
        articleLink = bundle.getString(Constants.ARTICLE_LINK);
        title = bundle.getString(Constants.ARTICLE_TITLE);
        isCollected = bundle.getBoolean(Constants.ARTICLE_COLLECTED);
        isShowCollectIcon = bundle.getBoolean(Constants.ARTICLE_ICON);
        itemPos = bundle.getInt(Constants.ARTICLE_POSITION);
        eventBusTag = bundle.getString(Constants.EVENT_BUS_TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_detail_menu, menu);
        mCollectItem = menu.findItem(R.id.item_collect);
        mCollectItem.setVisible(isShowCollectIcon);
        mCollectItem.setIcon(isCollected ? R.drawable.ic_like_white : R.drawable.ic_like_not_white);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_collect:
                if (mPresenter.getLoginStatus()){
                    if (isCollected) {
                        mPresenter.cancelCollectArticle(itemPos, articleId);
                    } else {
                        mPresenter.addCollectArticle(itemPos, articleId);
                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    ToastUtils.showToast(this, getString(R.string.login_first));
                }
                break;
            case R.id.item_share:
                break;
            case R.id.item_browser:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(articleLink)));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 让菜单同时显示图标和文字
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void showArticle() {
        Intent intent = new Intent(Intent.ACTION_SEND);


        startActivity(intent);
    }

    @Override
    public void showError() {
        ToastUtils.showToast(this, getString(R.string.write_permission_rejected));
    }

    @Override
    public void showCollectSuccess(int position) {
        isCollected = true;
        mCollectItem.setIcon(R.drawable.ic_like_white);
        EventBus.getDefault().post(new CollectEvent(true, position), eventBusTag);
    }

    @Override
    public void showCancelCollectSuccess(int position) {
        isCollected = false;
        mCollectItem.setIcon(R.drawable.ic_like_not);
        EventBus.getDefault().post(new CollectEvent(false, position), eventBusTag);
    }
}
