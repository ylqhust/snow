package com.ylqhust.snow.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by apple on 15/10/4.
 */
public class Utils {


    /**
     * transform inputStream to String use specila Charset
     * @param is
     * @param charset
     * @return String
     * @throws IOException
     */
    public static String IsToString(InputStream is,Charset charset) throws IOException
    {
        return new String(IsToByte(is),charset);
    }

    /**
     * transform inputSteam to String use default charset
     * @param is
     * @return
     * @throws IOException
     */
    public static String IsToString(InputStream is) throws IOException
    {
        return IsToString(is, Charset.defaultCharset());
    }

    /**
     * transform inputStream to byte[]
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] IsToByte(InputStream is) throws IOException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len;
        byte[] bytes = new byte[1024];
        while((len = is.read(bytes))!=-1)
        {
            outputStream.write(bytes,0,len);
        }
        byte[] result = outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}
