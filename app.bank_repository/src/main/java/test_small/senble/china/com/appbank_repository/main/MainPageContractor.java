package test_small.senble.china.com.appbank_repository.main;


import test_small.senble.china.com.lib.common.base.BasePresenter;
import test_small.senble.china.com.lib.common.base.BaseView;

/**
 * Created by Administrator on 2017/8/22.
 */

public interface MainPageContractor {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {
        void loadData();
    }
}
