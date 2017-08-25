package test_small.senble.china.com.appstub;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public abstract class AbsTask<I, T, B extends RxView> implements IUserCase<T>  {
    protected I mRequestValue;
    protected UserCaseHandler.ResponseValue mResponseValue;
    protected  RxView<T> mRxView;
    protected UserCaseHandler.CallBack<T> mCallBack;

    public AbsTask(I requestValue, UserCaseHandler.CallBack<T> callBack, B rxView){
        mRxView = rxView;
        mRequestValue = requestValue;
        mCallBack = callBack;
    }

    @Override
    public Observable<T> getObservable() {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
//                e.onNext(run());
                run();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxView.<T>bindToLifecycle());
    }
}
