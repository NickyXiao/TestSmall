package test_small.senble.china.com.libnetwork.events;

import android.app.Activity;

/**
 * Created by Administrator on 2017/4/26.
 */
public class ForceLogoutEvent {
    private Activity currentFrontActivity;
    public ForceLogoutEvent(Activity currentFrontActivity){
        this.currentFrontActivity = currentFrontActivity;
    }

    public Activity getCurrentFrontActivity(){
        return this.currentFrontActivity;
    }
}
