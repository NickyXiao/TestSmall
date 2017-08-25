package test_small.senble.china.com.appbank_repository.main;

import android.app.Activity;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import test_small.senble.china.com.appstub.AbsTask;
import test_small.senble.china.com.appstub.IUserCase;
import test_small.senble.china.com.appstub.RxView;
import test_small.senble.china.com.appstub.UserCaseHandler;

/**
 * Created by Administrator on 2017/8/24.
 */

public class GetMainPageDataTask extends AbsTask<GetMainPageDataTask.RequestValue, GetMainPageDataTask.ResponseValue, RxView>{

    public GetMainPageDataTask(RequestValue requestValue, UserCaseHandler.CallBack<ResponseValue> callBack, RxView rxView){
        super(requestValue, callBack, rxView);
    }

    @Override
    public ResponseValue run() {
        //模拟执行网络请求首页数据
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        Retrofit.Builder builder1 = new Retrofit.Builder().client(builder.build());
        Retrofit retrofit = builder1.baseUrl("https://www.pj.com").build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), new JSONObject(mRequestValue.getValues()).toString());
        return retrofit.create(IMainPageInfoAPI.class).getMainPageInfo(requestBody);
    }

    @Override
    public UserCaseHandler.CallBack<ResponseValue> getCallBack() {
        return mCallBack;
    }

    public static class RequestValue implements UserCaseHandler.RequestValue{
        private Map values;
        public RequestValue(Map values){
            this.values = values;
        }

        public Map getValues(){
            return values;
        }
    }

    public class ResponseValue implements UserCaseHandler.ResponseValue{

    }
}
