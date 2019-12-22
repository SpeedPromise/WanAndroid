package com.example.wanandroid.utils;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.example.wanandroid.R;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.modules.login.ui.LoginActivity;
import com.example.wanandroid.modules.main.bean.ArticleItemData;
import com.example.wanandroid.modules.main.ui.activity.ArticleDetailActivity;
import com.example.wanandroid.modules.main.ui.activity.CommonActivity;

import java.util.Random;

public class CommonUtils {

    public static void startArticleDetailActivity(Context context, int id, String title, String link,
                                                  boolean isCollected, boolean isShowCollectIcon,
                                                  int position, String eventBusTag) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(Constants.ARTICLE_ID, id);
        intent.putExtra(Constants.ARTICLE_TITLE, title);
        intent.putExtra(Constants.ARTICLE_LINK, link);
        intent.putExtra(Constants.ARTICLE_COLLECTED, isCollected);
        intent.putExtra(Constants.ARTICLE_ICON ,isShowCollectIcon);
        intent.putExtra(Constants.ARTICLE_POSITION, position);
        intent.putExtra(Constants.EVENT_BUS_TAG, eventBusTag);
        context.startActivity(intent);
    }

    public static void startNavFragment(Context context, String tag) {
        Intent intent = new Intent(context, CommonActivity.class);
        intent.putExtra(Constants.TYPE_FRAGMENT_KEY, tag);
        context.startActivity(intent);
    }

    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ToastUtils.showToast(context, context.getString(R.string.login_first));
    }

    public static int getRandomColor() {
        Random random = new Random();

        int red;
        int green;
        int blue;

        red = random.nextInt(190);
        green = random.nextInt(190);
        blue = random.nextInt(190);

        return Color.rgb(red, green, blue);
    }
}
