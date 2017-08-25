package test_small.senble.china.com.appbank_repository.main;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/24.
 */

public interface IMainPageInfoAPI {
    @POST("frontPage/v1.7/getBannerIndex")
    GetMainPageDataTask.ResponseValue getMainPageInfo(@Body RequestBody requestBody);
}
