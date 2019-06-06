package com.example.fancester.link;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

/**
 * people.
 */

public class People {
    private String nama;
    private String status;
    private int iconID;
    private String number;

    public People(String nama, String status, int iconID, String number) {
        super();
        this.nama = nama;
        this.status = status;
        this.iconID = iconID;
        this.number = number;
    }

    public String getNama() {
        return nama;
    }
    public String getStatus() {
        return status;
    }
    public int geticonID() { return iconID; }
    public String getNumber() {return number;}
}
