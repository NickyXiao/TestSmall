package test_small.senble.china.com.appbank_repository.main;

import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.HashMap;
import java.util.Map;

import test_small.senble.china.com.lib.common.IUserCase;
import test_small.senble.china.com.lib.common.RxView;
import test_small.senble.china.com.lib.common.UserCaseHandler;


/**
 * Created by Administrator on 2017/8/22.
 */

public class MainPagePresenterImpl implements MainPageContractor.Presenter {

    private MainPageContractor.View view;
    private RxView<FragmentEvent> rxView;
    private UserCaseHandler userCaseHandler;

    public MainPagePresenterImpl(RxView<FragmentEvent> rxView, MainPageContractor.View view) {
        this.view = view;
        this.rxView = rxView;
        userCaseHandler = new UserCaseHandler();
    }


    @Override
    public void loadData() {
        //构造用户获取数据的操作实例对象  UserCase
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("referral","应用宝");
        requestMap.put("clientId","3");

        GetMainPageDataTask getMainPageDataTask = new GetMainPageDataTask(
                new GetMainPageDataTask.RequestValue(requestMap),
                new IUserCase.CallBack<GetMainPageDataTask.ResponseValue>() {
                    @Override
                    public void success(GetMainPageDataTask.ResponseValue responseValue) {
                        //获取数据成功
                        view.hideProgress();
                    }
                    @Override
                    public void failed(IUserCase.ErrorValue errorValue) {
                        //获取数据失败
                        view.loadError();
                    }
                },
                rxView);
        userCaseHandler.executeUserTask(getMainPageDataTask);
    }
}
