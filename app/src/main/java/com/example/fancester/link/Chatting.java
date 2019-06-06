package com.example.fancester.link;

import android.support.v7.widget.LinearLayoutCompat;
import android.widget.LinearLayout;

/**
 * Created by Lenovo on 04/03/2017.
 */

public class Chatting {
    private String str;
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
    private int chatID;
    private int iconID;

    public Chatting(String str, LinearLayout.LayoutParams params,int chatID, int iconID) {
        super();
        this.str = str;
        this.params.gravity = params.gravity;
        this.params.leftMargin = params.leftMargin;
        this.chatID = chatID;
        this.iconID = iconID;
    }
    public String getStr()
    {
        return str;
    }
    public LinearLayout.LayoutParams getParams() {
        return params;
    }
    public int getChatID() {
        return chatID;
    }
    public int getIconID() { return iconID; }
}
