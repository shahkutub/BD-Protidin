package com.kalerkantho.Model;

/**
 * Created by hp on 8/30/2016.
 */
public class LoginResponse {

    private String status;
    private String msg;
    private  UserDetailsInfo userdetails = new UserDetailsInfo();
    private String token;

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

    public UserDetailsInfo getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(UserDetailsInfo userdetails) {
        this.userdetails = userdetails;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
