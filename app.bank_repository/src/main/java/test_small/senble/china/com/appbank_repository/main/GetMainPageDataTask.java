package test_small.senble.china.com.appbank_repository.main;

import android.util.Log;

import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test_small.senble.china.com.appbank_repository.api.Api;
import test_small.senble.china.com.appbank_repository.beans.Result;
import test_small.senble.china.com.lib.common.JsonRequestCase;
import test_small.senble.china.com.lib.common.http.RetrofitInstance;
import test_small.senble.china.com.lib.common.http.SslHelper;

/**
 * Created by Administrator on 2017/8/24.
 */

public class GetMainPageDataTask extends JsonRequestCase<GetMainPageDataTask.ResponseValue>{//

    public GetMainPageDataTask(RequestValue requestValue, CallBack<ResponseValue> callBack, LifecycleProvider<?> lifecycleProvider) {
        super(requestValue, callBack, lifecycleProvider);
    }

    @Override
    public ResponseValue runAsync(JsonRequestCase.RequestValue requestValues) {
        try {
//            return RetrofitInstance.SingleInstance.getInstance().create(Api.MainPageApi.class)
//                    .getMainPageInfo(requestValues.getJsonRequestBody()).execute().body();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            SslHelper sslHelper = new SslHelper();
            builder.sslSocketFactory(sslHelper.getSslSocketFactory(), sslHelper.getTrustManager())
                    .hostnameVerifier(sslHelper.getHostnameVerifier());

            Retrofit.Builder builder1 = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://www.pj.com/mdpst/")
                    .client(builder.build());
            Retrofit retrofit = builder1.build();
            return retrofit.create(Api.MainPageApi.class)
                    .getMainPageInfo(requestValues.getJsonRequestBody()).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("GetMainPageDataTask","数据加载异常----------------------");
        }
        return new ResponseValue();
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
