package com.example.wanandroid.modules.WxArticle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.modules.WxArticle.contract.WxArticleListContract;
import com.example.wanandroid.modules.WxArticle.presenter.WxArticleListPresenter;
import com.example.wanandroid.modules.login.ui.LoginActivity;
import com.example.wanandroid.modules.main.bean.ArticleItemData;
import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.utils.CommonUtils;
import com.example.wanandroid.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WxArticleListFragment extends BaseFragment<WxArticleListPresenter> implements WxArticleListContract.View {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.home_recycler_view)
    RecyclerView mRecyclerView;

    private WxArticleAdapter mAdapter;

    private int id;

    public static WxArticleListFragment newInstance(Bundle bundle){
        WxArticleListFragment fragment = new WxArticleListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        List<ArticleItemData> mArticleList = new ArrayList<>();
        mAdapter = new WxArticleAdapter(R.layout.item_article, mArticleList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ArticleItemData item = mAdapter.getItem(position);
            if (item != null) {
                CommonUtils.startArticleDetailActivity(getContext(), mAdapter.getItem(position),true, position, Constants.WX_PAGER);
            }
        });
        mAdapter.setOnItemChildClickListener(((adapter, view, position) -> childClickEvent(view, position)));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        id = getArguments().getInt(Constants.WX_CHAPTER_ID);
        initRefreshLayout();
    }

    @Override
    protected void createPresenter() {
        mPresenter = new WxArticleListPresenter();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refreshLayout(id, false);
            refreshLayout.finishRefresh();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore();
        });
    }

    @Override
    protected void lazyLoading() {
        mPresenter.refreshLayout(id, true);
    }

    @Override
    public void showArticleList(ArticleListData articleListData, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            mAdapter.replaceData(articleListData.getDatas());
        }
        mAdapter.addData(articleListData.getDatas());
    }

    private void childClickEvent(View view, int position) {
        switch (view.getId()) {
            case R.id.tv_article_chapterName:
                //TO-DO
                break;
            case R.id.article_favorite:
                if (mPresenter.getLoginStatus()){
                if (mAdapter.getData().get(position).isCollect()) {
                    mPresenter.cancelCollectArticle(position, mAdapter.getData().get(position).getId());
                } else {
                    mPresenter.addCollectArticle(position, mAdapter.getData().get(position).getId());
                }
            } else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                ToastUtils.showToast(getActivity(), getString(R.string.login_first));
            }
                break;
            case R.id.tv_article_tag:
                //TO-DO
                break;
        }
    }

    public void backToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void showCollectSuccess(int position) {
        mAdapter.getData().get(position).setCollect(true);
        mAdapter.setData(position, mAdapter.getData().get(position));
        ToastUtils.showToast(getActivity(), getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectSuccess(int position) {
        mAdapter.getData().get(position).setCollect(false);
        mAdapter.setData(position, mAdapter.getData().get(position));
        ToastUtils.showToast(getActivity(), getString(R.string.cancel_collect));
    }
}
