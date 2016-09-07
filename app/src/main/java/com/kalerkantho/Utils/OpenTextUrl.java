package com.kalerkantho.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Adnan on 5/9/2016.
 */
public class OpenTextUrl {
    public static Context con;

    public OpenTextUrl(Context con){
        this.con = con;
    }

    public  void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span)
    {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        LinkSpan linkSpan = new LinkSpan(span.getURL());
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                // Do something with span.getURL() to handle the link click...

                Log.e("sdsd",">>>>>>>>>."+span.getURL());
            }
        };
        // strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.setSpan(linkSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        strBuilder.removeSpan(span);
    }

    public  void setTextViewHTML( TextView text, String html)
    {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for(URLSpan span : urls) {
            text.setMovementMethod(LinkMovementMethod.getInstance());

            makeLinkClickable(strBuilder, span);

        }
        text.setText(strBuilder);
    }

    public  void setTextViewHTMLChat( TextView text, String html)
    {
      //Log.e("st",html);
        if(html.contains("[")) {

            // [ داستن لانجDustin Lang ]
            //<p><a href=\"https://www.facebook.com/hashtag/%D9%84%D9%85%D8%A7%D8%B0%D8%A7_%D9%86%D8%AD%D8%AA%D8%A7%D8%AC_%D8%A7%D9%84%D8%AA%D9%86%D8%B8%D9%8A%D8%B1?source=feed_text&amp;story_id=641268322701692\"><span>‫<strong>#‏</strong></span><strong><span>لماذانحتاجالتنظير‬</span></strong></a><strong>؟</strong>
            // String data = html.substring(html.indexOf("[ "),html.indexOf("]"));
            //String data = html.substring(html.indexOf("[ ") + 2, html.indexOf(" ]") + 1);
           // Log.e("asdas:",">>>"+data);

          /*  if(!html.contains("<a href=")) {

                html = html.replaceAll("\\[", "<a href=\\\"");
                html = html.replaceAll("\\]", " \">[ Url ] </a> ");
            }*/
            //html.replaceAll(" \\]","\\\">"+"[adnan]"+"</a> ");

        }
       // Log.e("html:",">>>"+html);
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for(URLSpan span : urls) {
            text.setMovementMethod(LinkMovementMethod.getInstance());

            makeLinkClickable(strBuilder, span);

        }
        text.setText(strBuilder);
       // text.setText(Html.fromHtml(strBuilder.toString()));
        //text.setGravity(Gravity.RIGHT);
    }

    public  class LinkSpan extends URLSpan {
        private LinkSpan(String url) {
            super(url);
        }

        @Override
        public void onClick(View view) {
            String url = getURL();
            Log.e("fff",">>>"+url);

            if(url.contains("http://") || url.contains("https://")) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                con.startActivity(browserIntent);
            }
            else{

            }
        }
    }
}
