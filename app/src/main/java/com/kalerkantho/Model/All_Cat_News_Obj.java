package com.kalerkantho.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlaptop on 8/14/2016.
 */
public class All_Cat_News_Obj {
    private String category_id;
    private String category_name;
    private List<CommonNewsItem> news= new ArrayList<CommonNewsItem>();

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<CommonNewsItem> getNews() {
        return news;
    }

    public void setNews(List<CommonNewsItem> news) {
        this.news = news;
    }
}
