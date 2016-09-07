package com.kalerkantho.holder;

import com.kalerkantho.Model.CommonNewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlaptop on 8/29/2016.
 */
public class AllPhoto {
    private String code;
    private String status;
    private String msg;
    private List<CommonNewsItem> images = new ArrayList<CommonNewsItem>();

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

    public List<CommonNewsItem> getImages() {
        return images;
    }

    public void setImages(List<CommonNewsItem> images) {
        this.images = images;
    }
}
