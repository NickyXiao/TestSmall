package test_small.senble.china.com.appstub.base;

/**
 * Created by Administrator on 2017/8/22.
 */

public interface BaseView<T> {
    /**
     * 加载数据，界面相关提示
     */
    void showProgress();

    /**
     * 隐藏加载界面相关
     */
    void hideProgress();

    /**
     * 加载数据错误
     */
    void loadError();
}
