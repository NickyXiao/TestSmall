package test_small.senble.china.com.lib.common.data.source;

import javax.inject.Inject;
import javax.sql.DataSource;

import test_small.senble.china.com.lib.common.data.source.local.LocalDataSource;
import test_small.senble.china.com.lib.common.data.source.remote.RemoteDataSource;

/**
 * Created by Administrator on 2017/8/31.
 */

public class DataRepository {

    private DataRepository(){

    }

    public static class SingleInstance{
        private static DataRepository repository = new DataRepository();
        public static DataRepository getInstance(){
            if(repository == null){
                repository = new DataRepository();
            }
            return repository;
        }
    }

    private IDataPolicy mDataPolicy;

    public void setDataPolicy(IDataPolicy dataPolicy){
        this.mDataPolicy = dataPolicy;
    }


    public void obtainData(){

    }

}
