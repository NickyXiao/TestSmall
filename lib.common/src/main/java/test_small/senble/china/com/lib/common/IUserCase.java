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
     * 执行的一般是耗时操作
     */
    R run();


    CallBack<R> getCallBack();

    Observable<R> extraOption(Observable observable);

    /**
     * 通过请求数据，生成对应的被监听着对象
     * @return
     */
    Observable getObservable(I requestValues);
    /**设置数据缓存策略*/
    void setPolicy(IDataPolicy dataPolicy);

//    interface RequestValue{}
//
//    interface ResponseValue{}
//
    interface ErrorValue{}

    interface CallBack<T>{
        void success(T responseValue);
        void failed(ErrorValue errorValue);
    }
}
