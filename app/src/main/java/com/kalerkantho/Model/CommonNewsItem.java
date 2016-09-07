package com.kalerkantho.Model;

import android.text.TextUtils;

import com.kalerkantho.Utils.AppConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wlaptop on 8/14/2016.
 */
public class CommonNewsItem {
    private String id;
    private String cat_id;
    private String title;
    private String image;
    private String datetime;
    private String summery;
    private String caption;
    private String start_time;
    private String category;
    private String url;
    private String category_name;
    private String details;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getBanglaDateString() {
        String banglaDate="";

        if(!(TextUtils.isEmpty(datetime)))
        {


            // show date and time in bangla
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date ddd = null;
            try {

                ddd = dt.parse(datetime);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat dayFromate = new SimpleDateFormat("dd");
            SimpleDateFormat monthFromate = new SimpleDateFormat("MM");
            SimpleDateFormat yearFromate = new SimpleDateFormat("yyyy");

            SimpleDateFormat hourFromate = new SimpleDateFormat("hh");
            SimpleDateFormat minuteFromate = new SimpleDateFormat("mm");

            String dayForm = dayFromate.format(ddd);
            String monthForm = monthFromate.format(ddd);
            String yearForm = yearFromate.format(ddd);
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
            String year2Bangla = AppConstant.getDigitBanglaFromEnglish(String.valueOf(yearForm));
            String hour2Bangla = AppConstant.getDigitBanglaFromEnglish(String.valueOf(hourForm));
            String minute2Bangla = AppConstant.getDigitBanglaFromEnglish(String.valueOf(minuteForm));

            banglaDate= day2Bangla+" "+monthName+", "+year2Bangla+" "+hour2Bangla+"."+minute2Bangla;

        }

        return banglaDate;
    }
}
