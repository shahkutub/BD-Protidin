package com.kalerkantho.holder;

import com.kalerkantho.Model.CommonNewsItem;
import com.kalerkantho.Model.Paginator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlaptop on 8/28/2016.
 */
public class AllNirbahito {
    private String code;
    private String status;
    private String msg;
    private List<CommonNewsItem> my_news = new ArrayList<CommonNewsItem>();
    private Paginator paginator = new Paginator();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CommonNewsItem> getMy_news() {
        return my_news;
    }

    public void setMy_news(List<CommonNewsItem> my_news) {
        this.my_news = my_news;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }
}
