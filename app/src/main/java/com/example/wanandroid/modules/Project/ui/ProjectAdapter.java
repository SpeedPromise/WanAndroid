package com.example.wanandroid.modules.Project.ui;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.modules.main.bean.ArticleItemData;
import com.example.wanandroid.utils.GlideApp;

import java.util.List;

public class ProjectAdapter extends BaseQuickAdapter<ArticleItemData, BaseViewHolder> {

    public ProjectAdapter(int layoutResId, @Nullable List<ArticleItemData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleItemData item) {
        helper.setText(R.id.tv_article_author, !item.getAuthor().isEmpty() ? item.getAuthor() : item.getShareUser())
                .setText(R.id.tv_article_niceDate, item.getNiceDate())
                .setText(R.id.tv_article_title, item.getTitle())
                .setText(R.id.tv_article_chapterName, item.getSuperChapterName() + "/" + item.getChapterName())
                .setImageResource(R.id.article_favorite, item.isCollect() ? R.drawable.ic_like : R.drawable.ic_like_not);

        if (!item.getEnvelopePic().isEmpty()) {
            helper.getView(R.id.envelopePic).setVisibility(View.VISIBLE);
        }
        GlideApp.with(mContext)
                .load(item.getEnvelopePic())
                .placeholder(R.drawable.bg_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into((ImageView) helper.getView(R.id.envelopePic));

        if (item.getTags().size() > 0) {
            helper.setText(R.id.tv_article_tag, item.getTags().get(0).getName())
                    .getView(R.id.tv_article_tag).setVisibility(View.VISIBLE);

        } else {
            helper.getView(R.id.tv_article_tag).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.tv_article_chapterName);
        helper.addOnClickListener(R.id.article_favorite);
        helper.addOnClickListener(R.id.tv_article_tag);
    }
}
