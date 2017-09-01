package test_small.senble.china.com.lib.common.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/1.
 */

public class RetrofitInstance {
    private Retrofit retrofit;
    private RetrofitInstance(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        SslHelper sslHelper = new SslHelper();
        builder.sslSocketFactory(sslHelper.getSslSocketFactory(), sslHelper.getTrustManager())
                .hostnameVerifier(sslHelper.getHostnameVerifier());

        Retrofit.Builder builder1 = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.pj.com/mdpst")
                .client(builder.build());
        retrofit = builder1.build();
    }

    public static class SingleInstance{
        private static RetrofitInstance retrofitGetterInstance = new RetrofitInstance();
        public static Retrofit getInstance(){
            return  retrofitGetterInstance.retrofit;
        }
    }
}
