package test_small.senble.china.com.lib.common;

import io.reactivex.Observable;
import test_small.senble.china.com.lib.common.data.source.IDataPolicy;

/**
 * Created by Administrator on 2017/8/24.
 *
 *      R   :   网络请求结果返回数据类型
 *
 *
 */

public interface IUserCase<I, R>{
    /**
     *     执行的一般是耗时操作<br>
     *     如：网络请求，数据库操作
     */
    R runAsync(I requestValues);

    /**
     *     通过请求数据，生成对应的被监听着对象<br>
     *
     * @return
     */
    Observable<R> generateObservable(I requestValues);

    interface ErrorValue{}

    interface CallBack<R>{
        void success(R responseValue);
        void failed(ErrorValue errorValue);
    }

    I getRequestValue();
    CallBack<R> getCallBack();
//    V getBindedRxView();
}
