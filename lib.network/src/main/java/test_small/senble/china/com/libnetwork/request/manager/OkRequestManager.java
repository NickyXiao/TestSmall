package test_small.senble.china.com.libnetwork.request.manager;

import android.os.Handler;
import android.os.Looper;

import com.bumptech.glide.util.Util;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created by Administrator on 2017/4/20.
 */
public class OkRequestManager implements OkLifecycleListener {

//    private final OkLifecycle lifecycle;
//    private final OkSupportRequestManagerFragment.OkRequestManagerTreeNode treeNode;
    private final OkRequestTracker requestTracker;

    public OkRequestManager(final OkLifecycle lifecycle){
//        this.lifecycle=lifecycle;
//        this.treeNode = treeNode;
        this.requestTracker = new OkRequestTracker();

        //添加监听
        if (Util.isOnBackgroundThread()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    lifecycle.addListener(OkRequestManager.this);
                }
            });
        } else {
            lifecycle.addListener(this);
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        requestTracker.clearRequests();
    }

    public void startCallRequest(Call call, Callback callback){
        requestTracker.runRequest(call, callback);
    }
}
