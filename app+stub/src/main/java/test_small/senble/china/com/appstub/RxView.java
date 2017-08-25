package test_small.senble.china.com.appstub;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import test_small.senble.china.com.appstub.base.RxViewEvent;

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
