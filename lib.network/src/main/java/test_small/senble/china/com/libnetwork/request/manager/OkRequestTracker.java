package test_small.senble.china.com.libnetwork.request.manager;

import com.bumptech.glide.util.Util;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created by Administrator on 2017/4/20.
 */
public class OkRequestTracker {
//    private final Set<Call> requests = Collections.newSetFromMap(new WeakHashMap<Call, Boolean>());
    // A set of requests that have not completed and are queued to be run again. We use this list to maintain hard
    // references to these requests to ensure that they are not garbage collected before they start running or
    // while they are paused. See #346.
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
//    private final List<Call> pendingRequests = new ArrayList<Call>();

    private final Set<Call> requests = Collections.newSetFromMap(new WeakHashMap<Call, Boolean>());

    /**
     * Starts tracking the given request.
     */
    public void runRequest(Call callRequest, Callback callback) {
        //将请求入队列
        callRequest.enqueue(callback);
        requests.add(callRequest);
    }

    public void clearRequests() {
        //清除所有的
//        for (Call callRequest : requests) {
//            callRequest.cancel();//取消请求
//            //清空请求池中的引用
//            if(!callRequest.isExecuted()){
//                try {
//                    callRequest.execute();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        for (Call callRequest : Util.getSnapshot(requests)) {
            callRequest.cancel();//取消请求
            //清空请求池中的引用
            if(!callRequest.isExecuted()){
                try {
                    callRequest.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        requests.clear();
    }

}
