package com.example.wanandroid.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wanandroid.app.WanAndroidApp;
import com.example.wanandroid.app.constants.Constants;
import com.example.wanandroid.core.http.api.ApiService;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataManager {

    private ApiService apiService = createServer();

    private final SharedPreferences mPreferences =
            WanAndroidApp.getContext().getSharedPreferences(Constants.MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);

    public ApiService createServer() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new PersistentCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(WanAndroidApp.getContext().getApplicationContext())));
        OkHttpClient client = builder.build();

        return new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }

    public void setLoginStatus(boolean loginStatus) {
        mPreferences.edit().putBoolean(Constants.LOGIN_STATUS, loginStatus).apply();
    }

    public boolean getLoginStatus() {
        return mPreferences.getBoolean(Constants.LOGIN_STATUS, false);
    }

    public String getLoginAccount() {
        return mPreferences.getString(Constants.ACCOUNT, "");
    }

    public void setLoginAccount(String account) {
        mPreferences.edit().putString(Constants.ACCOUNT, account).apply();
    }

}
