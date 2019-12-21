package com.example.wanandroid.modules.main.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.modules.main.bean.UsefulSiteData;
import com.example.wanandroid.modules.main.bean.UserInfoData;
import com.example.wanandroid.modules.main.contract.UsefulSitesContract;
import com.example.wanandroid.modules.main.presenter.UsefulSitesPresenter;
import com.example.wanandroid.modules.main.ui.activity.ArticleDetailActivity;
import com.example.wanandroid.utils.CommonUtils;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UsefulSitesFragment extends BaseFragment<UsefulSitesPresenter> implements UsefulSitesContract.View {

    @BindView(R.id.fb)
    FlexboxLayout fb;

    private List<UserInfoData> mData;

    public static UsefulSitesFragment newInstance() {
        return new UsefulSitesFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_userfulsite;
    }

    @Override
    protected void initView() {
        mData = new ArrayList<>();
    }

    @Override
    protected void createPresenter() {
        mPresenter = new UsefulSitesPresenter();
    }

    @Override
    protected void lazyLoading() {
        mPresenter.getUsefulSites();
    }

    @Override
    public void showUsefulSites(List<UsefulSiteData> data) {
        for (int i = 0; i < data.size(); i++) {
            TextView child = (TextView) LayoutInflater.from(fb.getContext()).inflate(R.layout.item_knowledge_tv, fb,false);
            UsefulSiteData item = data.get(i);
            child.setText(item.getName());
            child.setTextColor(CommonUtils.getRandomColor());
            child.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                intent.putExtra(Constants.ARTICLE_ID, item.getId());
                intent.putExtra(Constants.ARTICLE_TITLE, item.getName());
                intent.putExtra(Constants.ARTICLE_LINK, item.getLink());
                intent.putExtra(Constants.EVENT_BUS_TAG, Constants.TAG_DEFAULT);
                startActivity(intent);
            });
            fb.addView(child);
        }
    }
}
