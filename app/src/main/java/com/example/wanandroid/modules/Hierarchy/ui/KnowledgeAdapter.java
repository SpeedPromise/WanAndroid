package com.example.wanandroid.modules.Hierarchy.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.modules.Hierarchy.bean.KnowledgeTreeData;
import com.example.wanandroid.utils.CommonUtils;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class KnowledgeAdapter extends BaseQuickAdapter<KnowledgeTreeData, BaseViewHolder> {

    private OnItemClickListener mOnItemClickListener = null;

    public KnowledgeAdapter(int layoutResId, @Nullable List<KnowledgeTreeData> data) {
        super(layoutResId, data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, KnowledgeTreeData item) {
        helper.setText(R.id.tv_name, item.getName());
        FlexboxLayout fb = helper.getView(R.id.fb);
        for (int i = 0; i < item.getChildren().size(); i++) {
            KnowledgeTreeData childItem = item.getChildren().get(i);
            TextView child = (TextView) LayoutInflater.from(fb.getContext()).inflate(R.layout.item_knowledge_tv, fb,false);
            child.setText(childItem.getName());
            child.setTextColor(CommonUtils.getRandomColor());
            int finalI = i;
            child.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(item, finalI);
                }
            });
            fb.addView(child);
        }
        helper.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(item, 0);
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(KnowledgeTreeData bean, int pos);
    }

}
