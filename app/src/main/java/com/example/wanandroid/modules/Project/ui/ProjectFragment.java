package com.example.wanandroid.modules.Project.ui;

import android.os.Bundle;
import android.util.SparseArray;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.modules.Project.bean.ProjectTreeData;
import com.example.wanandroid.modules.Project.contract.ProjectContract;
import com.example.wanandroid.modules.Project.presenter.ProjectPresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends BaseFragment<ProjectPresenter> implements ProjectContract.View {

    @BindView(R.id.project_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.project_viewPaper)
    ViewPager mViewPager;

    private SparseArray<ProjectListFragment> mFragments = new SparseArray<>();
    private List<ProjectTreeData> mProjectTreeData;
    private ProjectListFragment mCurrentFragment;

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void createPresenter() {
        mPresenter = new ProjectPresenter();
    }

    @Override
    protected void lazyLoading() {
        mPresenter.getProjectTreeData();
    }

    @Override
    public void showProjectTreeData(List<ProjectTreeData> projectTreeData) {
        mProjectTreeData = projectTreeData;
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                ProjectListFragment projectListFragment = mFragments.get(position);
                if (projectListFragment != null) {
                    return projectListFragment;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.PROJECT_ID, mProjectTreeData.get(position).getId());
                    projectListFragment = ProjectListFragment.newInstance(bundle);
                    mFragments.put(position, projectListFragment);
                    return projectListFragment;
                }
            }

            @Override
            public int getCount() {
                return mProjectTreeData == null ? 0 : mProjectTreeData.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mProjectTreeData.get(position).getName();
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
        if (mProjectTreeData != null){
            mProjectTreeData.clear();
            mProjectTreeData = null;
        }
    }
}
