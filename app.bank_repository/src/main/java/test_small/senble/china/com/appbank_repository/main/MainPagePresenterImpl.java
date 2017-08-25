package test_small.senble.china.com.appbank_repository.main;

import java.util.HashMap;
import java.util.Map;

import test_small.senble.china.com.appstub.RxView;
import test_small.senble.china.com.appstub.UserCaseHandler;

/**
 * Created by Administrator on 2017/8/22.
 */

public class MainPagePresenterImpl implements MainPageContractor.Presenter {

    private MainPageContractor.View view;
    private RxView rxView;
    UserCaseHandler userCaseHandler;

    public MainPagePresenterImpl(RxView rxView, MainPageContractor.View view) {
        this.view = view;
        this.rxView = rxView;
        userCaseHandler = new UserCaseHandler();
    }


    @Override
    public void loadData() {
        //构造用户获取数据的操作实例对象  UserCase
        Map<String,String> requestParams = new HashMap<>();

        GetMainPageDataTask getMainPageDataTask = new GetMainPageDataTask(
                new GetMainPageDataTask.RequestValue(requestParams),
                new UserCaseHandler.CallBack<GetMainPageDataTask.ResponseValue>() {
                    @Override
                    public void success(GetMainPageDataTask.ResponseValue responseValue) {
                        //获取数据成功
                        view.hideProgress();
                    }
                    @Override
                    public void failed(UserCaseHandler.ErrorValue errorValue) {
                        //获取数据失败
                        view.loadError();
                    }
                },
                rxView);
        userCaseHandler.executeUserTask(getMainPageDataTask);
    }
}
