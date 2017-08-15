package test_small.senble.china.com.libnetwork.request;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.io.Serializable;

/**
 * 请求的持有者，可用来取消请求，封装了请求的取消操作逻辑
 * Created by Administrator on 2017/4/17.
 */
public interface ISyncRequestHandler extends Serializable {
    void cancelRequest();
    void beginRequest(Fragment fragment);
    void beginRequest(FragmentActivity fragmentActivity);
    void beginRequest(Application application);
}
