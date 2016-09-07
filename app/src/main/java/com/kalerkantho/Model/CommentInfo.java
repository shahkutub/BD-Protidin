package com.kalerkantho.Model;

import android.text.TextUtils;

import com.kalerkantho.Utils.AppConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hp on 9/1/2016.
 */
public class CommentInfo {
    private String connent_id;
    private String comment_text;
    private String news_id;
    private String created_at;
    private String user_id;
    private String full_name;
    private String email;

    public String getConnent_id() {
        return connent_id;
    }

    public void setConnent_id(String connent_id) {
        this.connent_id = connent_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBanglaDateString() {
        String banglaDate="";

        if(!(TextUtils.isEmpty(created_at)))
        {


            // show date and time in bangla
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date ddd = null;
            try {

                ddd = dt.parse(created_at);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat dayFromate = new SimpleDateFormat("dd");
            SimpleDateFormat monthFromate = new SimpleDateFormat("MM");
//            SimpleDateFormat yearFromate = new SimpleDateFormat("yyyy");

            SimpleDateFormat hourFromate = new SimpleDateFormat("hh");
            SimpleDateFormat minuteFromate = new SimpleDateFormat("mm");

            String dayForm = dayFromate.format(ddd);
            String monthForm = monthFromate.format(ddd);
//            String yearForm = yearFromate.format(ddd);
            String hourForm = hourFromate.format(ddd);
            String minuteForm = minuteFromate.format(ddd);
            String monthName="";

            if(monthForm.equalsIgnoreCase("01") ){

                monthName = AppConstant.allMonth[0];

            }else if(monthForm.equalsIgnoreCase("02")){

                monthName = AppConstant.allMonth[1];

            }else if(monthForm.equalsIgnoreCase("03")){

                monthName = AppConstant.allMonth[2];

            }else if(monthForm.equalsIgnoreCase("04")){

                monthName = AppConstant.allMonth[3];

            }else if(monthForm.equalsIgnoreCase("05")){

                monthName = AppConstant.allMonth[4];

            }else if(monthForm.equalsIgnoreCase("06")){

                monthName = AppConstant.allMonth[5];

            }else if(monthForm.equalsIgnoreCase("07")){

                monthName = AppConstant.allMonth[6];

            }else if(monthForm.equalsIgnoreCase("08")){

                monthName = AppConstant.allMonth[7];

            }else if(monthForm.equalsIgnoreCase("09")){

                monthName = AppConstant.allMonth[8];

            }else if(monthForm.equalsIgnoreCase("10")){

                monthName = AppConstant.allMonth[9];

            }else if(monthForm.equalsIgnoreCase("11")){

                monthName = AppConstant.allMonth[10];

            }else if(monthForm.equalsIgnoreCase("12")){

                monthName = AppConstant.allMonth[11];

            }



            String day2Bangla = AppConstant.getDigitBanglaFromEnglish(String.valueOf(dayForm));
//            String year2Bangla = AppConstant.getDigitBanglaFromEnglish(String.valueOf(yearForm));
            String hour2Bangla = AppConstant.getDigitBanglaFromEnglish(String.valueOf(hourForm));
            String minute2Bangla = AppConstant.getDigitBanglaFromEnglish(String.valueOf(minuteForm));

            banglaDate= day2Bangla+" "+monthName+", "+hour2Bangla+":"+minute2Bangla;

        }

        return banglaDate;
    }
}
