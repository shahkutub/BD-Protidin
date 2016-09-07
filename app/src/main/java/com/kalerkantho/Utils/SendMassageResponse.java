package com.kalerkantho.Utils;

import java.util.ArrayList;

/**
 * Created by AAPBD on 6/25/2016.
 */
public class SendMassageResponse {
    private String status;
  ArrayList<MassageresponseInfo>  response = new ArrayList<MassageresponseInfo>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<MassageresponseInfo> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<MassageresponseInfo> response) {
        this.response = response;
    }
}
