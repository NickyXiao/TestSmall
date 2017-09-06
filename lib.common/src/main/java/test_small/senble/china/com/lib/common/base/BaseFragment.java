package test_small.senble.china.com.lib.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.RxFragment;

/**
 * Created by Administrator on 2017/8/10.
 */

public abstract class BaseFragment extends com.trello.rxlifecycle2.components.support.RxFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return getInitialedView(inflater, container, savedInstanceState);
    }

    protected abstract View getInitialedView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
}
