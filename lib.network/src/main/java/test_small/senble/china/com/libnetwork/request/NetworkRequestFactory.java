package test_small.senble.china.com.libnetwork.request;

/**
 *
 * Created by Administrator on 2017/4/6.
 */
public class NetworkRequestFactory {
    //支持设置缓存
    //支持取消
    //定制化请求
    //设置请求的解析器
    public static INetworkRequestClient getNetworkRequestDispatcher(){
        return OkHttpRequestClient.getSingleInstance();
    }
}
