package test_small.senble.china.com.lib.common;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import test_small.senble.china.com.lib.common.data.source.IDataPolicy;

/**
 * Created by Administrator on 2017/8/24.
 */

public abstract class AbsNetworkRequestTask<I, R, B extends RxView<R>> implements IUserCase<I, R> {
    protected I mRequestValue;
    protected RxView<R> mRxView;
    protected CallBack<R> mCallBack;

    public AbsNetworkRequestTask(I requestValue, CallBack<R> callBack, B rxView){
        mRxView = rxView;
        mRequestValue = requestValue;
        mCallBack = callBack;
    }

//    @Override
//    public Observable<R> getObservable(I mRequestValue) {
//         Observable<R> observable = Observable.create(new ObservableOnSubscribe<R>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<R> e) throws Exception {
//                //执行耗时任务，发送执行结果
//                e.onNext(run());
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(mRxView.<R>bindToLifecycle());
//        return extraOption(observable);
//    }

    @Override
    public Observable<R> extraOption(Observable observable) {
        return observable;
    }
}
