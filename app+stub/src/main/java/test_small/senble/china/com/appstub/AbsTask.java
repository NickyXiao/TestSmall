package test_small.senble.china.com.appstub;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public abstract class AbsTask implements IUserCase  {

    @Override
    public Observable getObservable() {
        return Observable.create(new ObservableOnSubscribe<UserCaseHandler.ResponseValue>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<UserCaseHandler.ResponseValue> e) throws Exception {
                //执行用户操作请求
                run();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxAppCompatActivity.<UserCaseHandler.ResponseValue>bindUntilEvent(ActivityEvent.DESTROY));
    }
}
