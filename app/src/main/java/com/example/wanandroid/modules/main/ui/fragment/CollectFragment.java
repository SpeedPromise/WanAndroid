package com.example.wanandroid.modules.main.ui.fragment;

import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.core.event.CollectEvent;
import com.example.wanandroid.modules.main.bean.ArticleItemData;
import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.main.contract.CollectContract;
import com.example.wanandroid.modules.main.presenter.CollectPresenter;
import com.example.wanandroid.modules.main.adapter.CollectListAdapter;
import com.example.wanandroid.utils.CommonUtils;
import com.example.wanandroid.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollectFragment extends BaseFragment<CollectPresenter> implements CollectContract.View {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.home_recycler_view)
    RecyclerView mRecyclerView;

    private CollectListAdapter mAdapter;

    public static CollectFragment newInstance() {
        return new CollectFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        List<ArticleItemData> mArticleList = new ArrayList<>();
        mAdapter = new CollectListAdapter( R.layout.item_article, mArticleList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() < position) {
                return;
            }
            CommonUtils.startArticleDetailActivity(getContext(),
                    mAdapter.getItem(position).getId(),
                    mAdapter.getItem(position).getTitle(),
                    mAdapter.getItem(position).getLink(),
                    mAdapter.getItem(position).isCollect(),
                    true, position, Constants.MAIN_PAGER);

        });
        mAdapter.setOnItemChildClickListener(((adapter, view, position) -> childClickEvent(view, position)));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refreshLayout(false);
            refreshLayout.finishRefresh();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore();
        });
    }

    @Override
    protected void createPresenter() {
        mPresenter = new CollectPresenter();
    }

    @Override
    protected void lazyLoading() {
        mPresenter.refreshLayout(true);
    }

    private void childClickEvent(View view, int position) {
        switch (view.getId()) {
            case R.id.tv_article_chapterName:
                //TO-DO
                break;
            case R.id.article_favorite:
                mPresenter.cancelCollectInPage(position, mAdapter.getData().get(position).getId(), mAdapter.getData().get(position).getOriginId());
                break;
            case R.id.tv_article_tag:
                //TO-DO
                break;
        }
    }

    @Override
    public void showCollectList(ArticleListData articleListData, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            mAdapter.replaceData(articleListData.getDatas());
        } else {
            mAdapter.addData(articleListData.getDatas());
        }
    }

    @Override
    public void showCollectSuccess(int position) {
        ToastUtils.showToast(getActivity(), getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectSuccess(int position) {
        mAdapter.remove(position);
        ToastUtils.showToast(getActivity(), getString(R.string.cancel_collect));
    }
}
