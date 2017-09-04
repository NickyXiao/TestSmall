package test_small.senble.china.com.lib.common;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 统一接受处理所有与用户操作
 * Created by Administrator on 2017/8/24.
 */
public class UserCaseHandler {
    /**
     * 获接受用户的操作请求，并按用户指定格式填充数据并返回执行的结果数据
     * @param userCase
     * @param <I>
     * @param <R>
     */
    public <I, R> void executeUserTask(final IUserCase<I, R> userCase){
        userCase.generateObservable(userCase.getRequestValue()).subscribe(new Consumer<R>() {
            @Override
            public void accept(R t) throws Exception {
                userCase.getCallBack().success(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                userCase.getCallBack().failed(new IUserCase.ErrorValue() {});
            }
        });
    }
}
