package test_small.senble.china.com.libnetwork.request;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/6.
 */
public interface INetworkRequestClient {
    abstract class CallBackListener{
        public void onStart(ISyncRequestHandler requestHandler){};
        public void onError(IOkRequestError e){};
        /**当抛出异常的时候直接进入onError回调*/
        public void onSuccess(final boolean canceled, String response) throws Exception {};
    }

    /**
     * 自定义添加请求头部字段
     * 所有的请求都将添加此头部字段
     * @param headers
     */
    INetworkRequestClient addHeaders(HashMap<String, String> headers);

//    ISyncRequestHandler getRequestBundleLife(FragmentActivity fragmentActivity, final String url, final CallBackListener callBackListener);
//    ISyncRequestHandler getRequestBundleLife(Fragment fragment, final String url, final CallBackListener callBackListener);
//
//    ISyncRequestHandler jsonRequestBundleLife(Fragment fragment, final String url, final String requestParams,
//                                              final HashMap<String, String> requestHeaders, CallBackListener callBackListener);
//    ISyncRequestHandler jsonRequestBundleLife(FragmentActivity fragmentActivity, final String url, final String requestParams,
//                                    final HashMap<String, String> requestHeaders, CallBackListener callBackListener);
//
//    ISyncRequestHandler postRequestBundleLife(Fragment fragment, final String url, final String contentType, final String requestParams,
//                                    final HashMap<String, String> requestHeaders, final CallBackListener callBackListener);
//    ISyncRequestHandler postRequestBundleLife(FragmentActivity fragmentActivity, final String url, final String contentType, final String requestParams,
//                                              final HashMap<String, String> requestHeaders, final CallBackListener callBackListener);

    ISyncRequestHandler getRequestBundleLife(final String url, HashMap<String, String> requestHeaders, final CallBackListener callBackListener);
//    ISyncRequestHandler getRequestBundleLife(final String url, final CallBackListener callBackListener);

    ISyncRequestHandler jsonRequestBundleLife(final String url, final String requestParams,
                                              final HashMap<String, String> requestHeaders, CallBackListener callBackListener);
//    ISyncRequestHandler jsonRequestBundleLife(final String url, final String requestParams,
//                                              final HashMap<String, String> requestHeaders, CallBackListener callBackListener);

    ISyncRequestHandler postRequestBundleLife(final String url, final String contentType, final HashMap<String, String> requestParams,
                                              final HashMap<String, String> requestHeaders, final CallBackListener callBackListener);
//    ISyncRequestHandler postRequestBundleLife(FragmentActivity fragmentActivity, final String url, final String contentType, final String requestParams,
//                                              final HashMap<String, String> requestHeaders, final CallBackListener callBackListener);
}
