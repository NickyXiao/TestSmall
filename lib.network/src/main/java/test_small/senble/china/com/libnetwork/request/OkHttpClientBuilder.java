package test_small.senble.china.com.libnetwork.request;


import okhttp3.OkHttpClient;
import test_small.senble.china.com.libnetwork.request.ssl.SslHelper;

/**
 * Created by Administrator on 2017/4/10.
 */
class OkHttpClientBuilder{
    private static class Inner{
        private static OkHttpClient.Builder builder;
        static{
            try{
                builder = new OkHttpClient.Builder();
                SslHelper sslHelper = new SslHelper();
                builder.sslSocketFactory(sslHelper.getSslSocketFactory(), sslHelper.getTrustManager())
                        .hostnameVerifier(sslHelper.getHostnameVerifier());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static OkHttpClient.Builder getSingleInstance(){
        return Inner.builder;
    }
}
