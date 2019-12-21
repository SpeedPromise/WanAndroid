package com.example.wanandroid.modules.Hierarchy.ui;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.wanandroid.R;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.modules.Hierarchy.bean.KnowledgeTreeData;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class KnowledgeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.knowledge_tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.knowledge_viewpager)
    ViewPager mViewPager;

    private Unbinder unbinder;

    private List<KnowledgeTreeData> mKnowledgeTreeDataList;
    private SparseArray<KnowledgeArticlesFragment> mFragments = new SparseArray<>();
    private KnowledgeArticlesFragment mCurrentFragment;
    private int curPos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);

        unbinder = ButterKnife.bind(this);

        initToolbar();
        initData();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initData() {
        KnowledgeTreeData knowledgeTreeData = (KnowledgeTreeData) getIntent().getSerializableExtra("Knowledge_Data");
        curPos = getIntent().getIntExtra("curPos", 0);
        if (knowledgeTreeData == null || knowledgeTreeData.getName() == null) {
            return;
        }
        mTitle.setText(knowledgeTreeData.getName());
        mKnowledgeTreeDataList = knowledgeTreeData.getChildren();
        if (mKnowledgeTreeDataList == null) {
            return;
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                KnowledgeArticlesFragment knowledgeArticlesFragment = mFragments.get(position);
                if (knowledgeArticlesFragment != null) {
                    return knowledgeArticlesFragment;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.KNOWLEDGE_ID, mKnowledgeTreeDataList.get(position).getId());
                    KnowledgeArticlesFragment fragment = KnowledgeArticlesFragment.newInstance(bundle);
                    mFragments.put(position, fragment);
                    return fragment;
                }

            }

            @Override
            public int getCount() {
                return mKnowledgeTreeDataList == null ? 0 : mKnowledgeTreeDataList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mKnowledgeTreeDataList.get(position).getName();
            }
        });

        mViewPager.setCurrentItem(curPos);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick(R.id.knowledge_floating_action_button)
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.knowledge_floating_action_button:
                backToTheTop();
                break;
        }
    }

    private void backToTheTop() {
        mCurrentFragment = mFragments.get(mViewPager.getCurrentItem());
        if (mCurrentFragment != null) {
            mCurrentFragment.backToTheTop();
        }
    }

    @Override
    public void onDestroy() {
        if (mFragments != null) {
            mFragments.clear();
            mFragments = null;
        }
        if (mKnowledgeTreeDataList != null) {
            mKnowledgeTreeDataList.clear();
            mKnowledgeTreeDataList = null;
        }
        super.onDestroy();
    }
}
