package test_small.senble.china.com.appbank_repository.main;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test_small.senble.china.com.appbank_repository.api.Api;
import test_small.senble.china.com.appbank_repository.beans.Result;
import test_small.senble.china.com.lib.common.AbsNetworkRequestTask;
import test_small.senble.china.com.lib.common.IUserCase;
import test_small.senble.china.com.lib.common.RxView;
import test_small.senble.china.com.lib.common.data.source.IDataPolicy;

/**
 * Created by Administrator on 2017/8/24.
 */

public class GetMainPageDataTask extends AbsNetworkRequestTask<GetMainPageDataTask.RequestValue,
        GetMainPageDataTask.ResponseValue, RxView<GetMainPageDataTask.ResponseValue>> {

    public GetMainPageDataTask(RequestValue requestValue, CallBack<ResponseValue> callBack, RxView rxView){
        super(requestValue, callBack, rxView);
    }

    @Override
    public ResponseValue run() {
        //模拟执行网络请求首页数据
//        DataRepository.SingleInstance.getInstance().



        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Retrofit.Builder builder1 = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(builder.build());
        Retrofit retrofit = builder1.baseUrl("https://www.pj.com/mdpst").build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), new JSONObject(mRequestValue.getValues()).toString());
////        try {
////            Class cls = Class.forName("");
////            Method method = cls.getDeclaredMethod("getMainPageInfo",RequestBody.class);
////
////            Object obj = method.invoke(retrofit.create(Api.MainPageApi.class),requestBody);
////            if(obj instanceof Call){
////                return (ResponseValue) ((Call) obj).execute().body();
////            }
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        } catch (NoSuchMethodException e) {
////            e.printStackTrace();
////        } catch (InvocationTargetException e) {
////            e.printStackTrace();
////        } catch (IllegalAccessException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//
        try {
            return retrofit.create(Api.MainPageApi.class).getMainPageInfo(requestBody).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TestRetrofit","失败----------------------");
        }

        return null;
    }

    @Override
    public CallBack<ResponseValue> getCallBack() {
        return mCallBack;
    }

    @Override
    public Observable getObservable(RequestValue requestValues) {
        Observable<ResponseValue> observable = Observable.create(new ObservableOnSubscribe<ResponseValue>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ResponseValue> e) throws Exception {
                //执行耗时任务，发送执行结果
                e.onNext(run());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxView.<ResponseValue>bindToLifecycle());

        return observable;
    }

    @Override
    public void setPolicy(IDataPolicy dataPolicy) {

    }

    public static class RequestValue{
        private Map values;
        public RequestValue(Map values){
            this.values = values;
        }

        public Map getValues(){
            return values;
        }
    }


    public class ResponseValue{
        private Result result;
        public void setResult(Result result) {
            this.result = result;
        }
        public Result getResult() {
            return result;
        }
    }
}
