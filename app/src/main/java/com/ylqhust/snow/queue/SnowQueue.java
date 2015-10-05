package com.ylqhust.snow.queue;

import android.widget.ImageView;

import com.ylqhust.snow.Utils.LoadBitmapHelper;

/**
 * Created by apple on 15/10/5.
 */
public class SnowQueue {
    public static void add(ImageView imageView,Object object)
    {
        LoadBitmapHelper loadBitmapHelper = new LoadBitmapHelper(imageView,object);
        loadBitmapHelper.load();
    }
    public static void add(ImageView imageView,Object object,int reqWidth,int reqHeight)
    {
        LoadBitmapHelper loadBitmapHelper = new LoadBitmapHelper(imageView,object);
        loadBitmapHelper.load(reqWidth,reqHeight);
    }
}
