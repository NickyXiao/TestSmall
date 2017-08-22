package test_small.senble.china.com.appbank_repository;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/8/8.
 */

public class MainFragment extends test_small.senble.china.com.appstub.base.BaseFragment {

    @Override
    protected View getInitialedView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }


}
