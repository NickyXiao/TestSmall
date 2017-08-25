package test_small.senble.china.com.appstub;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/8/24.
 *
 *      T   :   网络请求结果返回数据类型
 *
 *
 */

public interface IUserCase<T>{
    /**
     * 执行的一般是耗时操作
     */
    T run();

    UserCaseHandler.CallBack<T> getCallBack();

    /**
     * 自定义异步操作逻辑
     * @return
     */
    Observable<T> getObservable();
}
