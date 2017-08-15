package test_small.senble.china.com.libnetwork.request;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.sz.p2p.pjb.module.network.request.manager.OkRequestManagerRetriever;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created by Administrator on 2017/4/17.
 */
public class OkHttpSyncRequestHandler implements ISyncRequestHandler{
    private static final long serialVersionUID = 1L;

    private Call mCall;
    private Callback mCallback;
    public OkHttpSyncRequestHandler(Call call, Callback callback){
        mCall = call;
        mCallback = callback;
    }

    @Override
    public void cancelRequest() {
        if(mCall!=null){
            mCall.cancel();
            //清空请求池中的引用
            if(!mCall.isExecuted()){
                try {
                    mCall.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mCall = null;
        }
        if(mCallback != null){
            mCallback = null;
        }
    }

    @Override
    public void beginRequest(Fragment fragment){
        OkRequestManagerRetriever.get().get(fragment).startCallRequest(mCall, mCallback);
    }

    @Override
    public void beginRequest(FragmentActivity fragmentActivity){
        OkRequestManagerRetriever.get().get(fragmentActivity).startCallRequest(mCall, mCallback);
    }

    @Override
    public void beginRequest(Application application) {
        OkRequestManagerRetriever.get().get(application).startCallRequest(mCall,mCallback);
    }
}
