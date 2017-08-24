package test_small.senble.china.com.appbank_repository.main;

import android.app.Activity;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import test_small.senble.china.com.appstub.IUserCase;
import test_small.senble.china.com.appstub.UserCaseHandler;

/**
 * Created by Administrator on 2017/8/24.
 */

public class GetMainPageDataTask<O> implements IUserCase<O> {

    private UserCaseHandler.RequestValue mRequestValue;
    private UserCaseHandler.ResponseValue mResponseValue;
    private UserCaseHandler.CallBack<O> mCallBack;
    private RxAppCompatActivity mRxAppCompatActivity;

    public GetMainPageDataTask(RequestValue requestValue, UserCaseHandler.CallBack<O> callBack, RxAppCompatActivity rxAppCompatActivity){
        mRequestValue = requestValue;
        mCallBack = callBack;
        mRxAppCompatActivity = rxAppCompatActivity;
    }


    @Override
    public void run() {
        //模拟执行网络请求首页数据
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserCaseHandler.CallBack<O> getCallBack() {
        return mCallBack;
    }

    @Override
    public Observable<O> getObservable() {
        return Observable.create(new ObservableOnSubscribe<O>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<O> observableEmitter) throws Exception {
                //执行用户操作请求
                run();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxAppCompatActivity.<O>bindUntilEvent(ActivityEvent.DESTROY));
    }


    public class RequestValue implements UserCaseHandler.RequestValue{

    }

    public class ResponseValue implements UserCaseHandler.ResponseValue{

    }
}
