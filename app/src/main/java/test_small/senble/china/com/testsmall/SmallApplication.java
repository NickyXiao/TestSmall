package test_small.senble.china.com.testsmall;

import android.app.Application;

import net.wequick.small.Small;

/**
 * Created by Administrator on 2017/8/2.
 */

public class SmallApplication extends Application {
    public SmallApplication(){
        Small.preSetUp(this);
    }
}
