package test_small.senble.china.com.lib.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by Administrator on 2017/8/24.
 */

public interface RxView<T> extends LifecycleProvider<T> {
    void onRxViewAttach(android.app.Activity activity);
    void onRxViewCreate(@Nullable Bundle savedInstanceState);
    void onRxViewCreated(View view, @Nullable Bundle savedInstanceState);
    void onRxViewStart();
    void onRxViewResume();
    void onRxViewPause();
    void onRxViewStop();
    void onRxViewDestroyView();
    void onRxViewDestroy();
    void onRxViewDetach();
}
