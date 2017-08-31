package test_small.senble.china.com.appp2p.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 普通版接口（非存管）<br>
 *<strong>警告：<strong/>非存管的前面需要多添加一个“/”保证链接正确 <br>
 *
 * Created by Administrator on 2017/8/30.
 */

public interface Api {


    /**
     * 首页相关接口
     */
    interface MainPageApi{
        @POST("/rest/frontPage/v1.7/getBannerIndex")
        Call<?> getMainPageInfo(@Body RequestBody requestBody);


    }





}
