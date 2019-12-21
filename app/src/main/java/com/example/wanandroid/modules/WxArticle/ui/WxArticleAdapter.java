package com.example.wanandroid.modules.WxArticle.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.modules.main.bean.ArticleItemData;

import java.util.List;

public class WxArticleAdapter extends BaseQuickAdapter<ArticleItemData, BaseViewHolder> {

    public WxArticleAdapter(int layoutResId, @Nullable List<ArticleItemData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleItemData item) {
        helper.setText(R.id.tv_article_author, !item.getAuthor().isEmpty() ? item.getAuthor() : item.getShareUser())
                .setText(R.id.tv_article_niceDate, item.getNiceDate())
                .setText(R.id.tv_article_title, item.getTitle())
                .setText(R.id.tv_article_chapterName, item.getSuperChapterName() + "/" + item.getChapterName())
                .setImageResource(R.id.article_favorite, item.isCollect() ? R.drawable.ic_like : R.drawable.ic_like_not);

        helper.getView(R.id.tv_article_fresh).setVisibility(item.isFresh() ? View.VISIBLE : View.GONE);
        helper.getView(R.id.tv_article_top).setVisibility(item.getType() == 1 ? View.VISIBLE : View.GONE);

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
