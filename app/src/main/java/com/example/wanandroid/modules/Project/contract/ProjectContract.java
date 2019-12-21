package com.example.wanandroid.modules.Project.contract;

import com.example.wanandroid.base.IPresenter;
import com.example.wanandroid.base.IView;
import com.example.wanandroid.modules.Project.bean.ProjectTreeData;

import java.util.List;

public interface ProjectContract {
    interface View extends IView {
        void showProjectTreeData(List<ProjectTreeData> projectTreeData);
    }

    interface Presenter extends IPresenter<View> {
        void getProjectTreeData();
    }
}
