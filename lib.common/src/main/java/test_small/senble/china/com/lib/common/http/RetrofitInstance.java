package test_small.senble.china.com.lib.common.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/1.
 */

public class RetrofitInstance {

    private RetrofitInstance(){

    }

    public static class SingleInstance{
        private static Retrofit retrofit;
        private static RetrofitInstance retrofitGetterInstance = new RetrofitInstance();

        static {

        }

        public static Retrofit getInstance(){
            return  retrofit;
        }
    }
}
