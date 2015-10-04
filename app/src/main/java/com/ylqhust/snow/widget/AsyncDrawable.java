package com.ylqhust.snow.widget;


import android.graphics.drawable.BitmapDrawable;

import com.ylqhust.snow.task.LoadBitmapTask;

import java.lang.ref.WeakReference;

/**
 * Created by apple on 15/10/4.
 */
public class AsyncDrawable extends BitmapDrawable
{
    private WeakReference<LoadBitmapTask> loadBitmapTaskWeakReference;

    public AsyncDrawable(LoadBitmapTask loadBitmapTask)
    {
        this.loadBitmapTaskWeakReference = new WeakReference<LoadBitmapTask>(loadBitmapTask);
    }

    public LoadBitmapTask getLoadBitmapTask()
    {
        if (loadBitmapTaskWeakReference != null)
        {
            return loadBitmapTaskWeakReference.get();
        }
        return null;
    }
}
