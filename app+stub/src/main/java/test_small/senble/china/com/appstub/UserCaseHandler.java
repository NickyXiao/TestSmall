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
    public <T> void executeUserTask(final IUserCase<T> userCase){
        userCase.getObservable().subscribe(new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                userCase.getCallBack().success(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                userCase.getCallBack().failed(new ErrorValue() {});
            }
        });
    }

    public interface RequestValue{}

    public interface ResponseValue{}

    public interface ErrorValue{}

    public interface CallBack<T>{
        void success(T responseValue);
        void failed(ErrorValue errorValue);
    }
}
