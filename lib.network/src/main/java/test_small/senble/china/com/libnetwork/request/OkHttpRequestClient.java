package test_small.senble.china.com.libnetwork.request;


import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/6.
 */
public class OkHttpRequestClient implements INetworkRequestClient {
    private final static String Tag = "OkHttpRequestClient";
    private final static boolean DEBUG = true;

    /**
     * 这个要求单例
     */
    private OkHttpClient.Builder okHttpClientBuilder;
    private OkHttpClient okHttpClient;
    private Handler handler;

    private IOkRequestError dataDealError;

    private OkHttpRequestClient() {
        handler = new Handler(Looper.getMainLooper());

        okHttpClientBuilder = OkHttpClientBuilder.getSingleInstance();//实例化builder
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);//读取超时
        okHttpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);//连接超时
        okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS);//写入超时


        okHttpClient = okHttpClientBuilder.build();

        dataDealError = new OkRequestError(0, "{\"errorCode\":-1,\"errorMessage\":\"对不起~数据获取出错啦\"}");
    }

    private static class Inner {
        private static OkHttpRequestClient okHttpRequestClient = new OkHttpRequestClient();
    }

    public static OkHttpRequestClient getSingleInstance() {
        return Inner.okHttpRequestClient;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * 添加请求拦截器
     *
     * @param interceptor
     */
    public void addInterception(Interceptor interceptor) {
        okHttpClientBuilder.addInterceptor(interceptor);
    }

    @Override
    public INetworkRequestClient addHeaders(final HashMap<String, String> headers) {
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder();
                for (Map.Entry<String, String> item :
                        headers.entrySet()) {
                    requestBuilder.header(item.getKey(), item.getValue());
                }
                requestBuilder.method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        okHttpClient = okHttpClientBuilder.build();
        return this;
    }

    @Override
    public ISyncRequestHandler getRequestBundleLife(String url, HashMap<String, String> requestHeaders, CallBackListener callBackListener) {
        if (callBackListener == null) return null;//无回调就不请求
        //定制请求头部
        Request.Builder requestBuilder = new Request.Builder();
        if (null != requestHeaders) {
            for (Map.Entry<String, String> item : requestHeaders.entrySet()) {
                requestBuilder.header(item.getKey(), item.getValue());
            }
        }
        Request request = requestBuilder.url(url).get().build();
        Callback callback = generateCallback(callBackListener);
        Call call = okHttpClient.newCall(request);
        ISyncRequestHandler mHandler = new OkHttpSyncRequestHandler(call,callback);
        callBackListener.onStart(mHandler);//请求开始
        return mHandler;
    }

    @Override
    public ISyncRequestHandler jsonRequestBundleLife(String url, String requestParams, HashMap<String, String> requestHeaders, CallBackListener callBackListener) {
        if (callBackListener == null) return null;//无回调就不请求
        if(TextUtils.isEmpty(requestParams)){
            requestParams="{}";
        }
        Call call = generatePostCall(url, "application/json; charset=utf-8", requestParams, requestHeaders);
        Callback callBack = generateCallback(callBackListener);
        ISyncRequestHandler mHandler = new OkHttpSyncRequestHandler(call,callBack);
        callBackListener.onStart(mHandler);//请求开始
        return mHandler;
    }

    @Override
    public ISyncRequestHandler postRequestBundleLife(String url, String contentType, HashMap<String,String> requestParams, HashMap<String, String> requestHeaders, CallBackListener callBackListener) {
        if (callBackListener == null) return null;//无回调就不请求

        FormBody.Builder builder = new FormBody.Builder();
        if(null != requestParams && requestParams.size() > 0){
            for(Map.Entry<String,String> entry : requestParams.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        RequestBody requestBody = builder.build();
        //定制请求头部
        Request.Builder requestBuilder = new Request.Builder();
        if (null != requestHeaders) {
            for (Map.Entry<String, String> item : requestHeaders.entrySet()) {
                requestBuilder.header(item.getKey(), item.getValue());
            }
        }
        Request request = requestBuilder.url(url)//请求url
                .addHeader("Content-Type",contentType)
                .post(requestBody)//请求参数
                .build();//创建请求
        ISyncRequestHandler mHandler = new OkHttpSyncRequestHandler(okHttpClient.newCall(request), generateCallback(callBackListener));
        callBackListener.onStart(mHandler);//请求开始
        return mHandler;
    }

    private Call generatePostCall(String url, String contentType, String requestParams, HashMap<String, String> requestHeaders){
        RequestBody requestBody = RequestBody.create(MediaType.parse(contentType), requestParams);
        //定制请求头部
        Request.Builder requestBuilder = new Request.Builder();
        if (null != requestHeaders) {
            for (Map.Entry<String, String> item : requestHeaders.entrySet()) {
                requestBuilder.header(item.getKey(), item.getValue());
            }
        }
        Request request = requestBuilder.url(url)//请求url
                .post(requestBody)//请求参数
                .build();//创建请求
        return okHttpClient.newCall(request);
    }

    private Callback generateCallback(final CallBackListener callBackListener){
        return new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(!call.isCanceled()){
                            callBackListener.onError(dataDealError);//失败
                            if(DEBUG){
                                Log.e(Tag, e.getMessage()+" "+e.toString());
                            }
                        }
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response){
                try {
                    if (call.isCanceled()) return;

                    final String responseStr = response.body().string();
                    final int statusCode = response.code();//status code
                    if(statusCode >= 200 && statusCode < 300){
                        //请求正常
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    callBackListener.onSuccess(call.isCanceled(), responseStr);//成功
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    callBackListener.onError(dataDealError);//失败
                                }
                            }
                        });
                    }else{
                        //有错误
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //请求状态有错误
                                try{
                                    IOkRequestError error = new OkRequestError(statusCode,responseStr);
                                    callBackListener.onError(error);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    //异常
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBackListener.onError(dataDealError);//失败
                        }
                    });
                }
            }
        };
    }
}
