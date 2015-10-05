package com.ylqhust.snow.Utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.ylqhust.snow.task.Handler;
import com.ylqhust.snow.task.LoadBitmapTask;
import com.ylqhust.snow.widget.AsyncDrawable;

import java.net.URL;
import java.io.*;
/**
 * Created by apple on 15/10/4.
 */
public class LoadBitmapHelper implements Handler
{

    private ImageView imageView;

    private Object object;


    public LoadBitmapHelper(ImageView imageView, Object object) {
        this.imageView = imageView;
        this.object = object;
    }

    /**
     * execute the method to load a source size Bitmap to ImageView
     */
    public void load()
    {
        if (cancelPotentialWork(object,imageView))
        {
            LoadBitmapTask loadBitmapTask = new LoadBitmapTask(imageView,this);
            AsyncDrawable asyncDrawable = new AsyncDrawable(loadBitmapTask);
            imageView.setImageDrawable(asyncDrawable);
            loadBitmapTask.execute(object);
        }
    }

    /**
     * load a specila size bitmap to imageview
     * @param reqWidth
     * @param reqHeight
     */

    public void load(int reqWidth,int reqHeight)
    {
        if (cancelPotentialWork(object,imageView))
        {
            LoadBitmapTask loadBitmapTask = new LoadBitmapTask(imageView,this);
            AsyncDrawable asyncDrawable = new AsyncDrawable(loadBitmapTask);
            imageView.setImageDrawable(asyncDrawable);
            loadBitmapTask.execute(object,reqWidth,reqHeight);
        }
    }
    /**
     * cancle potential task
     * @param object
     * @param imageView
     * @return
     */
    private boolean cancelPotentialWork(Object object,ImageView imageView)
    {
        final LoadBitmapTask loadBitmapTask = getLoadBitmapTask(imageView);
        if (loadBitmapTask != null)
        {
            final Object objectID = loadBitmapTask.objectID;
            if (objectID == null || !CheckEqual(object,objectID))
            {
                loadBitmapTask.cancel(true);
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    private LoadBitmapTask getLoadBitmapTask(ImageView imageView)
    {
        if (imageView != null)
        {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable)
            {
                final AsyncDrawable asyncDrawable = (AsyncDrawable)drawable;
                return asyncDrawable.getLoadBitmapTask();
            }
        }
        return null;
    }

    private boolean CheckEqual(Object object,Object objectID)
    {
        if (object instanceof String && objectID instanceof String)
        {
            String s1 = (String)object;
            String s2 = (String)objectID;
            return s1.equals(s2);
        }
        if (object instanceof URL && objectID instanceof URL)
        {
            String s1 = ((URL)object).toString();
            String s2 = ((URL)objectID).toString();
            return s1.equals(s2);
        }
        if (object instanceof File && objectID instanceof File)
        {
            String s1 = ((File)object).toString();
            String s2 = ((File)objectID).toString();
            return s1.equals(s2);
        }
        return false;
    }

    @Override
    public void onBefore()
    {
        System.out.println("onBefore");
    }

    @Override
    public void onSuccess(Object object)
    {
        System.out.println("onSuccess");
    }

    @Override
    public void onFailed()
    {
        System.out.println("onFailed");
    }
}
