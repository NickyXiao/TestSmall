package test_small.senble.china.com.appp2p;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test_small.senble.china.com.lib.common.base.BaseFragment;

/**
 * Created by Administrator on 2017/8/10.
 */

public class MainFragment extends BaseFragment {

    @Override
    protected View getInitialedView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }
}
