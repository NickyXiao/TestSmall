package test_small.senble.china.com.libnetwork.request;

import android.app.Activity;

/**
 * Created by Administrator on 2017/4/26.
 */
public interface IOkRequestError {
    void dealError(Activity curActivity);
    int getStatusCode();
    String getResponseStr();
}
