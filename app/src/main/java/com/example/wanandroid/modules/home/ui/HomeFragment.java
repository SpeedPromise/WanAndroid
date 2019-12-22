package com.example.wanandroid.modules.home.ui;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.modules.home.banner.BannerGlideImageLoader;
import com.example.wanandroid.modules.home.contract.HomeContract;
import com.example.wanandroid.modules.login.ui.LoginActivity;
import com.example.wanandroid.modules.main.bean.ArticleItemData;
import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.home.presenter.HomePresenter;
import com.example.wanandroid.modules.home.banner.BannerData;
import com.example.wanandroid.utils.CommonUtils;
import com.example.wanandroid.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    private static final String TAG = "HomeFrament";

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.home_recycler_view)
    RecyclerView mRecyclerView;


    private Banner mBanner;
    private ArticleListAdapter mAdapter;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        List<ArticleItemData> mArticleList = new ArrayList<>();
        mAdapter = new ArticleListAdapter(R.layout.item_article, mArticleList);
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

        LinearLayout mHeaderGroup = (LinearLayout) getLayoutInflater().inflate(R.layout.head_banner, null);
        mBanner = mHeaderGroup.findViewById(R.id.head_banner);
        mHeaderGroup.removeView(mBanner);
        mAdapter.setHeaderView(mBanner);
        mRecyclerView.setAdapter(mAdapter);

        initRefreshLayout();
    }

    @Override
    protected void createPresenter() {
        mPresenter = new HomePresenter();
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
    public void showArticleList(ArticleListData data, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            mAdapter.replaceData(data.getDatas());
        } else {
            mAdapter.addData(data.getDatas());
        }
    }

    @Override
    public void showBanner(List<BannerData> bannerDataList) {
        List<String> mTitleList = new ArrayList<>();
        List<String> mImageList = new ArrayList<>();
        List<String> mUrlList = new ArrayList<>();
        List<Integer> mIdList = new ArrayList<>();

        for (BannerData bannerData : bannerDataList) {
            mTitleList.add(bannerData.getTitle());
            mImageList.add(bannerData.getImagePath());
            mUrlList.add(bannerData.getUrl());
            mIdList.add(bannerData.getId());
        }
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        mBanner.setImageLoader(new BannerGlideImageLoader());
        mBanner.setImages(mImageList);
        mBanner.setBannerAnimation(Transformer.DepthPage);
        mBanner.setBannerTitles(mTitleList);
        mBanner.isAutoPlay(true);
        mBanner.setDelayTime(2500);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setOnBannerListener(i ->
                CommonUtils.startArticleDetailActivity(getActivity(),
                        mIdList.get(i), mTitleList.get(i), mUrlList.get(i),
                        false, false, -1, Constants.TAG_DEFAULT));
        mBanner.start();
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
                if (mPresenter.getLoginStatus()) {
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
