package com.example.wanandroid.modules.Hierarchy.ui;

import android.content.Intent;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.modules.Hierarchy.bean.KnowledgeTreeData;
import com.example.wanandroid.modules.Hierarchy.contract.KnowledgeContract;
import com.example.wanandroid.modules.Hierarchy.presenter.KnowledgePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class KnowledgeFragment extends BaseFragment<KnowledgePresenter> implements KnowledgeContract.View {

    private static final String TAG = "KnowledgeFragment";

    @BindView(R.id.knowledge_recycler_view)
    RecyclerView mRecyclerView;

    private KnowledgeAdapter mAdapter;

    public static KnowledgeFragment newInstance() {
        return new KnowledgeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initView() {
        List<KnowledgeTreeData> knowledgeTreeDataList = new ArrayList<>();
        mAdapter = new KnowledgeAdapter(R.layout.item_knowledge, knowledgeTreeDataList);
        mAdapter.setOnItemClickListener(this::startKnowledgeActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void createPresenter() {
        mPresenter = new KnowledgePresenter();
    }

    @Override
    protected void lazyLoading() {
        mPresenter.getKnowledgeTree();
    }

    private void startKnowledgeActivity(KnowledgeTreeData bean, int position) {
        Intent intent = new Intent(getActivity(), KnowledgeActivity.class);
        intent.putExtra("Knowledge_Data", bean);
        intent.putExtra("curPos", position);
        startActivity(intent);
    }

    @Override
    public void showKnowledgeTree(List<KnowledgeTreeData> knowledgeTreeDataList) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.replaceData(knowledgeTreeDataList);
    }

    public void backToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
}
