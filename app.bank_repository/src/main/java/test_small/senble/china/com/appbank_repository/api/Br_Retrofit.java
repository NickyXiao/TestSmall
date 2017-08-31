package test_small.senble.china.com.appbank_repository.api;

import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/8/30.
 */

public class Br_Retrofit {

    private Retrofit retrofit;
    private Br_Retrofit(){
//        Retrofit retrofit1=null;
//        retrofit = new Retrofit.Builder(retrofit1).build();
    }

    public static class SingleInstance{
        private Br_Retrofit br_retrofit = new Br_Retrofit();
        public Br_Retrofit getInstance(){
            return  br_retrofit;
        }
    }
}
