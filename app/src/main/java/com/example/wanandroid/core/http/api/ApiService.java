package com.example.wanandroid.core.http.api;

import com.example.wanandroid.core.http.BaseResponse;
import com.example.wanandroid.modules.Hierarchy.bean.KnowledgeTreeData;
import com.example.wanandroid.modules.Project.bean.ProjectTreeData;
import com.example.wanandroid.modules.WxArticle.bean.WxArticleChapterData;
import com.example.wanandroid.modules.login.bean.LoginData;
import com.example.wanandroid.modules.main.bean.ArticleItemData;
import com.example.wanandroid.modules.main.bean.ArticleListData;
import com.example.wanandroid.modules.home.banner.BannerData;
import com.example.wanandroid.modules.main.bean.UsefulSiteData;
import com.example.wanandroid.modules.main.bean.UserInfoData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "https://www.wanandroid.com/";

    /**
     * 获取文章列表
     * @param pageNum
     * @return
     */
    @GET("article/list/{pageNum}/json")
    Observable<BaseResponse<ArticleListData>> getArticleList(@Path("pageNum") int pageNum);

    /**
     * 获取置顶文章
     * @return
     */
    @GET("article/top/json")
    Observable<BaseResponse<List<ArticleItemData>>> getTopArticles();

    /**
     * banner
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerData>>> getBannerData();

    /**
     * 知识体系
     * @return
     */
    @GET("tree/json")
    Observable<BaseResponse<List<KnowledgeTreeData>>> getKnowledgeTree();

    /**
     * 知识体系文章
     * @param page
     * @param cid
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<BaseResponse<ArticleListData>> getKnowledgeArticles(@Path("page") int page, @Query("cid") int cid);

    /**
     * 公众号列表
     * @return
     */
    @GET("wxarticle/chapters/json")
    Observable<BaseResponse<List<WxArticleChapterData>>> getWxArticleChapters();

    /**
     * 公众号文章
     * @param id
     * @param page
     * @return
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<BaseResponse<ArticleListData>> getWxArticles(@Path("id") int id, @Path("page") int page);

    /**
     * 项目分类
     * @return
     */
    @GET("project/tree/json")
    Observable<BaseResponse<List<ProjectTreeData>>> getProjectTree();

    /**
     * 项目列表数据
     * @return
     */
    @GET("project/list/{page}/json")
    Observable<BaseResponse<ArticleListData>> getProjectList(@Path("page") int page, @Query("cid") int cid);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginData>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginData>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 退出登录
     * @return
     */
    @GET("user/logout/json")
    Observable<BaseResponse<LoginData>> logout();


    /**
     * 收藏站内文章
     * @param id
     * @return
     */
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse<ArticleListData>> addCollectArticle(@Path("id") int id);

    /**
     * 收藏站外文章
     * @param title
     * @param author
     * @param link
     * @return
     */
    @POST("lg/collect/add/json")
    @FormUrlEncoded
    Observable<BaseResponse<ArticleListData>> addCollectOutsideArticle(@Field("title") String title, @Field("author") String author, @Field("link") String link);

    /**
     * 文章列表中取消收藏文章
     * @param id
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResponse<ArticleListData>> cancelCollectArticle(@Path("id") int id);

    /**
     * 收藏列表中取消收藏文章
     * @param id
     * @param originId
     * @return
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse<ArticleListData>> cancelCollectInPage(@Path("id") int id, @Field("originId") int originId);

    /**
     * 获取收藏列表
     * @param page
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    Observable<BaseResponse<ArticleListData>> getCollectList(@Path("page") int page);

    /**
     * 获取积分
     * @return
     */
    @GET("lg/coin/userinfo/json")
    Observable<BaseResponse<UserInfoData>> getUserInfo();

    /**
     * 获取常用网站
     * @return
     */
    @GET("friend/json")
    Observable<BaseResponse<List<UsefulSiteData>>> getUserfulSiteData();
}
