package com.ylqhust.snow.task;

import android.os.AsyncTask;

import com.ylqhust.snow.Utils.Utils;
import com.ylqhust.snow.core.GetInputStream;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by apple on 15/10/4.
 */
public class ByteArrayTask extends AsyncTask<Object,Integer,byte[]>
{
    private Handler handler;

    public ByteArrayTask(Handler handler)
    {
        this.handler = handler;
    }
    @Override
    protected void onPreExecute()
    {
        handler.onBefore();
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected byte[] doInBackground(Object... params)
    {
        Object object = params[0];
        byte[] bytes = null;
        GetInputStream getInputStream = null;
        try
        {
            getInputStream = Factory(object);
            InputStream is = getInputStream.getIs();
            bytes = Utils.IsToByte(is);
            is.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            getInputStream.close();
        }
        return bytes;
    }

    @Override
    protected void onPostExecute(byte[] bytes)
    {
        if (bytes == null)
        {
            handler.onFailed();
            return;
        }
        handler.onSuccess(bytes);
    }


    private GetInputStream Factory(Object object)
    {
        if (object instanceof String)
        {
            return new GetInputStream((String)object);
        }
        if (object instanceof URL)
        {
            return new GetInputStream((URL)object);
        }
        throw new IllegalArgumentException("params[0] is not a urlString or url");
    }
}
