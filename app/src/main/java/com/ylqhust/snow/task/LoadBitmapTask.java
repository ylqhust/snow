package com.ylqhust.snow.task;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.ylqhust.snow.core.GetBitmap;
import com.ylqhust.snow.widget.AsyncDrawable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by apple on 15/10/4.
 */
public class LoadBitmapTask extends AsyncTask<Object,Integer,Bitmap>
{
    private WeakReference<ImageView> imageViewWeakReference;

    public Object objectID;
    private Handler handler = null;
    public LoadBitmapTask(ImageView imageView,Handler handler) {
        this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
        this.handler = handler;
    }

    @Override
    protected void onPreExecute()
    {
        handler.onBefore();
    }

    /**
     * params max length is 3,min length is 1
     * the params[0] is always urlString
     * the params[1] is the request width
     * the params[2] is the request height
     *
     * the params[1] and the param[2] is unneccssary
     * @param params
     * @return
     */
    @Override
    protected Bitmap doInBackground(Object... params)
    {
        Bitmap bitmap = null;
        objectID = params[0];
        try{
            GetBitmap getBitmap = Factory(params[0]);
            if (params.length == 3)
            {
                int width = (int)params[1];
                int height = (int)params[2];
                bitmap = getBitmap.getBitmap(width,height);
            }
            else
            {
                bitmap = getBitmap.getBitmap();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        if (isCancelled())
        {
            System.out.println("isCancelled");
            bitmap = null;
        }
        if (this.imageViewWeakReference != null && bitmap != null)
        {
            final ImageView imageView = imageViewWeakReference.get();
            final LoadBitmapTask loadBitmapTask = getLoadBitmapTask(imageView);
            if (this == loadBitmapTask && imageView != null )
            {
                imageView.setImageBitmap(bitmap);
            }
            handler.onSuccess(bitmap);
        }
        else
        {
            handler.onFailed();
        }
    }


    /**
     * get the LoadBitmapTask bind to the imageView
     * @param imageView
     * @return LoadBitmapTask
     */
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

    private GetBitmap Factory(Object object)
    {
        if (object instanceof String)
            return new GetBitmap((String)object);
        if (object instanceof URL)
            return new GetBitmap((URL)object);
        if (object instanceof File)
            return new GetBitmap((File)object);
        if (object instanceof InputStream)
            return new GetBitmap((InputStream)object);
        if (object instanceof byte[])
            return new GetBitmap((byte[])object);
        throw new IllegalArgumentException("argument is not a String ,URL,File,InputStream or byte[]");
    }
}
