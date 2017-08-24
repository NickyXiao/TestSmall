package test_small.senble.china.com.appstub;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/8/24.
 */

public interface IUserCase<O>{
    /**
     * 执行的一般是耗时操作
     */
    void run();

    UserCaseHandler.CallBack<O> getCallBack();

    /**
     * 自定义异步操作逻辑
     * @return
     */
    Observable<O> getObservable();
}
