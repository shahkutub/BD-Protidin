package com.kalerkantho.holder;

import com.kalerkantho.Model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlaptop on 8/22/2016.
 */
public class AllCategory {
    private int code;
    private int status;
    private String msg;
    private List<Category> category_list = new ArrayList<Category>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Category> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<Category> category_list) {
        this.category_list = category_list;
    }
}
