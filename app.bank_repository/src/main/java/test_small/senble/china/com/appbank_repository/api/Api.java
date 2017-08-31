package test_small.senble.china.com.appbank_repository.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import test_small.senble.china.com.appbank_repository.main.GetMainPageDataTask;

/**
 * 存管版接口（存管）<br>
 *  <strong>警告：<strong/>存管的前面不添加一个“/” <br>
 *
 * Created by Administrator on 2017/8/30.
 */

public interface Api {


    /**
     * 首页相关接口
     */
    interface MainPageApi{
        @POST("rest/frontPage/v1.7/getBannerIndex")
        Call<GetMainPageDataTask.ResponseValue> getMainPageInfo(@Body RequestBody requestBody);


    }





}
