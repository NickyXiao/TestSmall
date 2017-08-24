package test_small.senble.china.com.appstub;

import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 统一接受处理所有与用户操作
 * Created by Administrator on 2017/8/24.
 */
public class UserCaseHandler {
    private IUserCase mUserCase;

    public UserCaseHandler(IUserCase userCase){
        mUserCase = userCase;
    }

    public <O> void executeUserTask(final IUserCase<O> userCase){
//        mUserCase.run();
//        userCase.getCallBack().success(userCase.get);
//        Observable.create(new ObservableOnSubscribe<ResponseValue>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<ResponseValue> e) throws Exception {
//                //执行用户操作请求
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
//                .subscribe(new Consumer<ResponseValue>() {
//                    @Override
//                    public void accept(ResponseValue responseValue) throws Exception {
//                        //回调
//                        userCase.getCallBack().success(responseValue);
//                    }
//                });

        userCase.getObservable().subscribe(new Consumer<O>() {
            @Override
            public void accept(O o) throws Exception {
                userCase.getCallBack().success(o);
            }
        });
    }

    public interface RequestValue{

    }

    public interface ResponseValue{

    }

    public interface ErrorValue{

    }

    public interface CallBack<O>{
        void success(O responseValue);
        void failed(ErrorValue errorValue);
    }
}
