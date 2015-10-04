package com.ylqhust.snow.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.ylqhust.snow.core.GetBitmap;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by apple on 15/10/4.
 */
public class BitmapTask extends AsyncTask<Object,Integer,Bitmap> {
    private Handler handler = null;

    public BitmapTask(Handler handler)
    {
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
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        if (bitmap == null)
        {
            System.out.println("BitmapTaskFailed");
            handler.onFailed();
            return;
        }
        handler.onSuccess(bitmap);
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
