package com.example.wanandroid.modules.main.bean;

import java.util.List;


public class ArticleListData {

    /**
     * curPage: 2
     * datas : ...
     * offset : 20
     * over : false
     * pageCount : 380
     * size : 20
     * total : 7599
     */


    private int curPage;  // 收藏列表参数
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<ArticleItemData> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int total) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ArticleItemData> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleItemData> datas) {
        this.datas = datas;
    }

}
