package test_small.senble.china.com.libnetwork.request.manager;

/**
 * Created by Administrator on 2017/4/20.
 */
public interface OkLifecycleListener {
    void onStart();
    /**
     * Callback for when {@link android.app.Fragment#onDestroy()}} or {@link android.app.Activity#onDestroy()} is
     * called.
     */
    void onDestroy();
}
