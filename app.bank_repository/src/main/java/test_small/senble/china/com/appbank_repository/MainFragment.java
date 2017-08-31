package test_small.senble.china.com.appbank_repository;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import test_small.senble.china.com.appbank_repository.main.MainPageContractor;
import test_small.senble.china.com.appbank_repository.main.MainPagePresenterImpl;
import test_small.senble.china.com.lib.common.base.BaseFragment;

/**
 * Created by Administrator on 2017/8/8.
 */

public class MainFragment extends BaseFragment implements MainPageContractor.View {

    MainPageContractor.Presenter mPresenter;

    @Override
    protected View getInitialedView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = new MainPagePresenterImpl(this, this);
        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.loadData();
    }

    @Override
    public void showProgress() {
        //
        Toast.makeText(getActivity(),"显示加载对话框",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        Toast.makeText(getActivity(),"隐藏加载对话框",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadError() {
        Toast.makeText(getActivity(),"加载数据出错",Toast.LENGTH_SHORT).show();
    }
}
