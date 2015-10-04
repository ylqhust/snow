package com.ylqhust.snow.task;

import java.util.Objects;

/**
 * Created by apple on 15/10/4.
 */
public interface Handler {
    /**
     * execute the method before task execute
     * this method executed in UI thread
     */
    public void onBefore();

    /**
     * execute the method after task execute success
     * this method executed in UI thread
     * @param object
     */
    public void onSuccess(Object object);

    /**
     * execute the method when a error happend
     * this method executed in UI thread
     */
    public void onFailed();
}
