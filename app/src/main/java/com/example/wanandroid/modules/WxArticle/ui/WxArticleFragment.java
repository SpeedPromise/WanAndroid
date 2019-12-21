package com.example.wanandroid.modules.WxArticle.ui;

import android.os.Bundle;
import android.util.SparseArray;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.modules.WxArticle.bean.WxArticleChapterData;
import com.example.wanandroid.modules.WxArticle.contract.WxArticleContract;
import com.example.wanandroid.modules.WxArticle.presenter.WxArticlePresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;

public class WxArticleFragment extends BaseFragment<WxArticlePresenter> implements WxArticleContract.View {

    @BindView(R.id.wx_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.wx_viewPaper)
    ViewPager mViewPager;

    private SparseArray<WxArticleListFragment> mFragments = new SparseArray<>();
    private List<WxArticleChapterData> mWxArticleChapters;
    private WxArticleListFragment mCurrentFragment;


    public static WxArticleFragment newInstance() {
        return new WxArticleFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wxarticle;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void createPresenter() {
        mPresenter = new WxArticlePresenter();
    }

    @Override
    protected void lazyLoading() {
        mPresenter.getWxArticleChapters();
    }

    @Override
    public void showWxArticleChapters(List<WxArticleChapterData> wxArticleChapters) {
        mWxArticleChapters = wxArticleChapters;
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                WxArticleListFragment wxArticleListFragment = mFragments.get(position);
                if (wxArticleListFragment != null) {
                    return wxArticleListFragment;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.WX_CHAPTER_ID, mWxArticleChapters.get(position).getId());
                    wxArticleListFragment = WxArticleListFragment.newInstance(bundle);
                    mFragments.put(position, wxArticleListFragment);
                    return wxArticleListFragment;

                }
            }

            @Override
            public int getCount() {
                return mWxArticleChapters == null ? 0 : mWxArticleChapters.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mWxArticleChapters.get(position).getName();
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void backToTheTop() {
        mCurrentFragment = mFragments.get(mViewPager.getCurrentItem());
        if (mCurrentFragment != null) {
            mCurrentFragment.backToTheTop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mFragments != null) {
            mFragments.clear();
            mFragments = null;
        }
        if (mWxArticleChapters != null){
            mWxArticleChapters.clear();
            mWxArticleChapters = null;
        }
    }
}
