package com.example.wanandroid.modules.Hierarchy.contract;

import com.example.wanandroid.base.IPresenter;
import com.example.wanandroid.base.IView;
import com.example.wanandroid.modules.Hierarchy.bean.KnowledgeTreeData;

import java.util.List;

public interface KnowledgeContract {
    interface View extends IView {
        void showKnowledgeTree(List<KnowledgeTreeData> knowledgeTreeData);
    }

    interface Presenter extends IPresenter<View> {
        void getKnowledgeTree();
    }
}
