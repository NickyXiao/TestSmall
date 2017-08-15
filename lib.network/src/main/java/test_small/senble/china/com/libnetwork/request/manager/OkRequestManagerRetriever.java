package test_small.senble.china.com.libnetwork.request.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/20.
 */
public class OkRequestManagerRetriever implements Handler.Callback{
    private static final String TAG = "ORMRetriever";
    static final String FRAGMENT_TAG = "com.sz.p2p.pjb.module.network.request.manager.OkSupportRequestManagerFragment";

    private static final int ID_REMOVE_FRAGMENT_MANAGER = 1;
    private static final int ID_REMOVE_SUPPORT_FRAGMENT_MANAGER = 2;

    /** The singleton instance of RequestManagerRetriever. */
    private static final OkRequestManagerRetriever INSTANCE = new OkRequestManagerRetriever();
    /** Main thread handler to handle cleaning up pending fragment maps. */
    private final Handler handler;

    // Visible for testing.
    /** Pending adds for SupportRequestManagerFragments. */
    final Map<FragmentManager, OkSupportRequestManagerFragment> pendingSupportRequestManagerFragments =
            new HashMap<FragmentManager, OkSupportRequestManagerFragment>();

    /** The top application level RequestManager. */
    private volatile OkRequestManager applicationManager;

    private OkRequestManager getApplicationManager(Application application) {
        // Either an application context or we're on a background thread.
        if (applicationManager == null) {
            synchronized (this) {
                if (applicationManager == null) {
                    // Normally pause/resume is taken care of by the fragment we add to the fragment or activity.
                    // However, in this case since the manager attached to the application will not receive lifecycle
                    // events, we must force the manager to start resumed using ApplicationLifecycle.
                    applicationManager = new OkRequestManager(new ApplicationLifecycle());
                }
            }
        }
        return applicationManager;
    }

    /**
     * Retrieves and returns the RequestManagerRetriever singleton.
     */
    public static OkRequestManagerRetriever get() {
        return INSTANCE;
    }

    // Visible for testing.
    OkRequestManagerRetriever() {
        handler = new Handler(Looper.getMainLooper(), this /* Callback */);
    }

    public OkRequestManager get(Application application) {
        return getApplicationManager(application);
    }

    public OkRequestManager get(FragmentActivity activity) {
        assertNotDestroyed(activity);
        FragmentManager fm = activity.getSupportFragmentManager();
        return supportFragmentGet(activity, fm);
    }

    public OkRequestManager get(Fragment fragment) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        }
        FragmentManager fm = fragment.getChildFragmentManager();
        return supportFragmentGet(fragment.getActivity(), fm);
    }

    /**
     * 构造Fragment并添加到fm中
     * @param context
     * @param fm
     * @return OkRequestManager
     */
    OkRequestManager supportFragmentGet(Context context, FragmentManager fm) {
        OkSupportRequestManagerFragment current = getSupportRequestManagerFragment(fm);
        OkRequestManager requestManager = current.getRequestManager();
        if (requestManager == null) {
            requestManager = new OkRequestManager(current.getLifecycle());
            current.setRequestManager(requestManager);
        }
        return requestManager;
    }

    /**
     * 添加Fragment到fm
     * @param fm
     * @return
     */
    OkSupportRequestManagerFragment getSupportRequestManagerFragment(final FragmentManager fm) {
        OkSupportRequestManagerFragment current = (OkSupportRequestManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            current = pendingSupportRequestManagerFragments.get(fm);
            if (current == null) {
                current = new OkSupportRequestManagerFragment();
                pendingSupportRequestManagerFragments.put(fm, current);
                fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
                handler.obtainMessage(ID_REMOVE_SUPPORT_FRAGMENT_MANAGER, fm).sendToTarget();
            }
        }
        return current;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void assertNotDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        boolean handled = true;
        Object removed = null;
        Object key = null;
        switch (msg.what) {
            case ID_REMOVE_FRAGMENT_MANAGER:
//                android.app.FragmentManager fm = (android.app.FragmentManager) message.obj;
//                key = fm;
//                removed = pendingRequestManagerFragments.remove(fm);
                break;
            case ID_REMOVE_SUPPORT_FRAGMENT_MANAGER:
                FragmentManager supportFm = (FragmentManager) msg.obj;
                key = supportFm;
                removed = pendingSupportRequestManagerFragments.remove(supportFm);
                break;
            default:
                handled = false;
        }
        if (handled && removed == null && Log.isLoggable(TAG, Log.WARN)) {
            Log.w(TAG, "Failed to remove expected request manager fragment, manager: " + key);
        }
        return handled;
    }

    class ApplicationLifecycle implements OkLifecycle {
        @Override
        public void addListener(OkLifecycleListener listener) {
            listener.onStart();
        }
    }
}
