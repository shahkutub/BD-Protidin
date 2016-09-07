package com.kalerkantho.Model;

/**
 * Created by AppBd on 8/29/2016.
 */
public class DetailsModel {
    private String code;
    private String status;
    private String msg;
    private String comments_count;
    private String is_liked;
    private String is_disliked;
    private String like_count;
    private String dislike_count;
    private CommonNewsItem news;

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

    public String getComments_count() {
        return comments_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    public String getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }

    public String getIs_disliked() {
        return is_disliked;
    }

    public void setIs_disliked(String is_disliked) {
        this.is_disliked = is_disliked;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getDislike_count() {
        return dislike_count;
    }

    public void setDislike_count(String dislike_count) {
        this.dislike_count = dislike_count;
    }

    public CommonNewsItem getNews() {
        return news;
    }

    public void setNews(CommonNewsItem news) {
        this.news = news;
    }
}
