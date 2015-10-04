package com.ylqhust.snow.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.ylqhust.snow.Utils.Utils;

import java.io.*;
import java.net.URL;
/**
 * Created by apple on 15/10/4.
 */
public class GetBitmap {
    private final int SOURCE_SIZE = -1;

    private String urlString = null;
    private URL url = null;
    private File filePath = null;
    private InputStream is = null;
    private byte[] bitmapBytes = null;

    private boolean CloseIs = false;

    public GetBitmap(String urlString) {
        this.urlString = urlString;
    }

    public GetBitmap(URL url) {

        this.url = url;
    }

    public GetBitmap(InputStream is) {

        this.is = is;
    }

    public GetBitmap(File filePath) {

        this.filePath = filePath;
    }

    public GetBitmap(byte[] bitmapBytes) {

        this.bitmapBytes = bitmapBytes;
    }

    /**
     *
     * @return source size Bitmap
     */
    public Bitmap getBitmap() throws IOException {
        return getBitmap(SOURCE_SIZE,SOURCE_SIZE);
    }

    /**
     *
     * @param reqWidth
     * @param reqHeight
     * @return get special size Bitmap
     */
    public Bitmap getBitmap(int reqWidth,int reqHeight) throws IOException {
        if (this.url != null)
            return getBitmapByUrl(reqWidth,reqHeight);
        if (this.urlString != null)
            return getBitmapByUrlString(reqWidth, reqHeight);
        if (this.filePath != null)
            return getBitmapByFile(reqWidth, reqHeight);
        if (this.is != null)
            return getBitmapByIs(reqWidth,reqHeight);
        if (this.bitmapBytes != null)
            return getBitmapByBytes(reqWidth,reqHeight);
        throw new IllegalArgumentException("argument is null");
    }




    private Bitmap getBitmapByUrlString(int reqWidth, int reqHeight) throws IOException {
        GetInputStream getInputStream = new GetInputStream(urlString);
        is = getInputStream.getIs();
        this.CloseIs = true;
        //getInputStream.close();
        return getBitmapByIs(reqWidth,reqHeight);
    }

    /**
     *
     * @param reqWidth
     * @param reqHeight
     * @return Bitmap or null if the url is null
     * @throws IOException
     */
    private Bitmap getBitmapByUrl(int reqWidth, int reqHeight) throws IOException
    {
        GetInputStream getInputStream = new GetInputStream(url);
        is = getInputStream.getIs();
        this.CloseIs = true;
        //getInputStream.close();
        return getBitmapByIs(reqWidth, reqHeight);
    }
    /**
     *
     * @param reqWidth
     * @param reqHeight
     * @return Bitmap or null if the filePath is null
     * @throws IOException
     */
    private Bitmap getBitmapByFile(int reqWidth, int reqHeight) throws IOException {
        if (filePath == null)
            return null;
        is = new FileInputStream(filePath);
        this.CloseIs = true;
        return getBitmapByIs(reqWidth,reqWidth);
    }


    /**
     *
     * @param reqWidth
     * @param reqHeight
     * @return Bitmap or null if the is is null
     * @throws IOException
     */
    private Bitmap getBitmapByIs(int reqWidth, int reqHeight) throws IOException
    {
        if (is == null)
            return null;
        bitmapBytes = Utils.IsToByte(is);
        if (this.CloseIs)
            is.close();
        return getBitmapByBytes(reqWidth,reqHeight);
    }

    /**
     *
     * @param reqWidth
     * @param reqHeight
     * @return Bitmap or null if the bitmapBytes is null
     */
    private Bitmap getBitmapByBytes(int reqWidth,int reqHeight)
    {
        if (bitmapBytes == null)
            return null;
        if (reqHeight == SOURCE_SIZE && reqWidth == SOURCE_SIZE)
        {
            return BitmapFactory.decodeByteArray(bitmapBytes,0,bitmapBytes.length);
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bitmapBytes,0,bitmapBytes.length,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bitmapBytes,0,bitmapBytes.length,options);
    }


    /**
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return inSampleSize
     */
    private int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight)
    {
        final int outWidth = options.outWidth;
        final int outHeight = options.outHeight;
        int inSampleSize = 1;
        if (outHeight > reqHeight || outWidth > reqWidth)
        {
            final int halfHeight = outHeight / 2;
            final int halfWidth = outWidth / 2;

            while ((halfHeight/inSampleSize) > reqHeight
                    && (halfWidth/inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
