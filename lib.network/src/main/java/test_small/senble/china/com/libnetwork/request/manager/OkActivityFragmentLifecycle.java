package test_small.senble.china.com.libnetwork.request.manager;

import com.bumptech.glide.util.Util;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Created by Administrator on 2017/4/20.
 */
public class OkActivityFragmentLifecycle implements OkLifecycle {

    private boolean isDestroyed = false;

    private final Set<OkLifecycleListener> lifecycleListeners =
            Collections.newSetFromMap(new WeakHashMap<OkLifecycleListener, Boolean>());

    @Override
    public void addListener(OkLifecycleListener listener) {
        lifecycleListeners.add(listener);
    }

    void onDestroy() {
        isDestroyed = true;
        for (OkLifecycleListener lifecycleListener : Util.getSnapshot(lifecycleListeners)) {
            lifecycleListener.onDestroy();
        }
    }
}
