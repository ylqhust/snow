package com.ylqhust.snow.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by apple on 15/10/4.
 */

public class GetInputStream {

    public static final int PHONE_DEVICE    = 0x00000001;
    public static final int COMPUTER_DEVICE = 0x00000010;


    private int Device_Type = PHONE_DEVICE;
    private int TimeOut = 5000;

    private String urlString = null;
    private URL url = null;

    private InputStream is;
    private HttpURLConnection conn;

    public GetInputStream(String urlString) {
        this.urlString = urlString;
    }

    public GetInputStream(URL url) {
        this.url = url;
    }



    public void setTimeOut(int timeout)
    {
        this.TimeOut = timeout;
    }

    public void setDeviceType(int device_type)
    {
        if (device_type != PHONE_DEVICE && device_type != COMPUTER_DEVICE)
            throw new IllegalArgumentException("Wrong Device Type");
        this.Device_Type = device_type;
    }

    public InputStream getIs() throws IOException
    {
        if (urlString != null)
            return getIsByUrlString();
        if (url != null)
            return getIsByUrl();
        throw new IllegalArgumentException("argument is null");
    }

    private InputStream getIsByUrlString() throws IOException
    {
        url = new URL(urlString);
        return getIsByUrl();
    }
    private InputStream getIsByUrl() throws IOException
    {
        conn = (HttpURLConnection) url.openConnection();
        setProperty(conn,url);
        int code  = conn.getResponseCode();
        if (code == 200)
        {
            is = conn.getInputStream();
            return is;
        }
        return null;
    }

    //set default property
    public void setProperty(HttpURLConnection conn,URL url) throws ProtocolException
    {
        conn.setRequestProperty("Cache-Control","max-age=0");
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Charset", "utf-8, iso-8859-1, utf-16, *;q=0.7");
        conn.setRequestProperty("Accept-Language", "zh-CN, en-US");
        conn.setRequestProperty("Host", url.getHost());
        if (Device_Type == PHONE_DEVICE)
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.2.1; en-us; M040 Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        else
            conn.setRequestProperty("User_Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(TimeOut);
        return;
    }

    public void close()
    {
        if (conn != null)
            conn.disconnect();
        return;
    }
}
