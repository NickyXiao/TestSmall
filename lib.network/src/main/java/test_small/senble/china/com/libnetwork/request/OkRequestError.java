package test_small.senble.china.com.libnetwork.request;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import test_small.senble.china.com.libnetwork.events.ForceLogoutEvent;

/**
 * Created by Administrator on 2017/4/17.
 */
public class OkRequestError implements IOkRequestError {
    /**
     * 返回码
     */
    private final int statusCode;
    /**
     * 错误信息
     */
    private final String responseStr;

    public OkRequestError(int statusCode, String responseStr) {
        this.statusCode = statusCode;
        this.responseStr = responseStr;
    }

    @Override
    public void dealError(Activity curActivity) {
        try {
            if (curActivity != null) {
                if (statusCode == 400) {
                    JSONObject errorInfoObject = new JSONObject(responseStr);
                    String error_description = errorInfoObject.optString("error_description");
                    String errorMessage = errorInfoObject.optString("errorMessage");
                    if ("Bad credentials".equalsIgnoreCase(error_description)) {
                        //登录的时候账户密码不匹配
                        Toast toast = Toast.makeText(curActivity, "用户名与密码不匹配", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (!TextUtils.isEmpty(error_description) && error_description.startsWith("Invalid refresh token")) {
                        //刷新手势密码时，刷新token超过有效期
                        //强制登出当前账户,清除本地所有的缓存数据
                        EventBus.getDefault().post(new ForceLogoutEvent(curActivity));//强制登出事件
                    } else {
                        //其他的400，显示后台异常信息
                        Toast toast = Toast.makeText(curActivity, errorMessage, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else if (statusCode == 401) {//未授权，获取的token已经失效了
                    EventBus.getDefault().post(new ForceLogoutEvent(curActivity));//强制登出事件
                    Toast toast = Toast.makeText(curActivity, "登录超时，请重新登录", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    JSONObject errorInfoObject = new JSONObject(responseStr);
                    int errorCode = errorInfoObject.optInt("errorCode");
                    String errorMessage = errorInfoObject.optString("errorMessage");
                    if (!TextUtils.isEmpty(errorMessage)) {
                        Toast toast = Toast.makeText(curActivity, errorMessage, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(curActivity, "请联系客服", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(curActivity, "数据获取出错~", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getResponseStr() {
        return responseStr;
    }
}
