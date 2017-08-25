package test_small.senble.china.com.appstub.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import test_small.senble.china.com.appstub.RxView;

/**
 * Created by Administrator on 2017/8/10.
 */

public abstract class BaseFragment extends Fragment implements RxView<FragmentEvent>{

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return getInitialedView(inflater, container, savedInstanceState);
    }

    protected abstract View getInitialedView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    public void onRxViewAttach(android.app.Activity activity) {
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onRxViewCreate(@Nullable Bundle savedInstanceState) {
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    public void onRxViewCreated(View view, @Nullable Bundle savedInstanceState) {
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    public void onRxViewStart() {
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onRxViewResume() {
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onRxViewPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
    }

    @Override
    public void onRxViewStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
    }

    @Override
    public void onRxViewDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
    }

    @Override
    public void onRxViewDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
    }

    @Override
    public void onRxViewDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
    }


    /****
     * 以下是
     *
     *
     *
     *
     *
     *******/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onRxViewAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onRxViewCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onRxViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        onRxViewStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        onRxViewResume();
    }

    @Override
    public void onPause() {
        onRxViewPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        onRxViewStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        onRxViewDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        onRxViewDestroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        onRxViewDetach();
        super.onDetach();
    }
}
