package test_small.senble.china.com.lib.common;

import com.trello.rxlifecycle2.android.FragmentEvent;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/9/1.
 */

public abstract class JsonRequestCase<R> implements IUserCase<JsonRequestCase.RequestValue, R, RxView> {

    private RequestValue mRequestValue;
    private RxView<FragmentEvent> mRxView;
    private CallBack<R> mCallBack;

    public JsonRequestCase(RequestValue requestValue, CallBack<R> callBack, RxView<FragmentEvent> rxView){
        mRxView = rxView;
        mRequestValue = requestValue;
        mCallBack = callBack;
    }

    @Override
    public Observable<R> generateObservable(final RequestValue requestValues) {
        return Observable.create(new ObservableOnSubscribe<R>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<R> e) throws Exception {
                //执行耗时任务，发送执行结果
                e.onNext(runAsync(requestValues));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxView.<R>bindToLifecycle());}

    @Override
    public CallBack<R> getCallBack() {
        return mCallBack;
    }

    @Override
    public RxView getBindedRxView() {
        return mRxView;
    }

    @Override
    public RequestValue getRequestValue() {
        return mRequestValue;
    }

    public static class RequestValue{
        private Map values;
        public RequestValue(Map values){
            this.values = values;
        }

        public RequestBody getJsonRequestBody(){
            return RequestBody.create(MediaType.parse("application/json"), new JSONObject(values).toString());
        }
    }
}
