package test_small.senble.china.com.appbank_repository.main;

import test_small.senble.china.com.appstub.base.BasePresenter;
import test_small.senble.china.com.appstub.base.BaseView;

/**
 * Created by Administrator on 2017/8/22.
 */

public interface MainPageContractor {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {

    }
}
