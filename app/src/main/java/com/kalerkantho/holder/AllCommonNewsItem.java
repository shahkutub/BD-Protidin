package com.kalerkantho.holder;

import com.kalerkantho.Model.CommonNewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlaptop on 8/14/2016.
 */
public class AllCommonNewsItem {

    private String type;
    private String category_title;
    private String category_id;

    public String getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }

    private String newsCategory;
    private CommonNewsItem news_obj = new CommonNewsItem();
    private List<CommonNewsItem> list_news_obj = new ArrayList<CommonNewsItem>();


    public List<CommonNewsItem> getList_news_obj() {
        return list_news_obj;
    }

    public void setList_news_obj(List<CommonNewsItem> list_news_obj) {
        this.list_news_obj = list_news_obj;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public CommonNewsItem getNews_obj() {
        return news_obj;
    }

    public void setNews_obj(CommonNewsItem news_obj) {
        this.news_obj = news_obj;
    }
}
