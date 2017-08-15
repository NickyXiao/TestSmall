package test_small.senble.china.com.libnetwork.image_request;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

/**
 * 默认全部都是淡入淡出效果
 * Created by Administrator on 2017/4/11.
 */
public interface IImageLoaderClient {
    void loadImage(Fragment fragment, String url, int placeholderResId, ImageView targetImageView);
    /**圆角图片*/
    void loadRoundCornerImage(Fragment fragment, String url, int placeholderResId, ImageView targetImageView, int cornerSize);
    /**圆形图片*/
    void loadCircleImageView(Fragment fragment, String url, int placeholderResId, ImageView targetImageView);

    void loadImage(FragmentActivity fragmentActivity, String url, int placeholderResId, ImageView targetImageView);
    void loadRoundCornerImage(FragmentActivity fragmentActivity, String url, int placeholderResId, ImageView targetImageView, int cornerSize);
    void loadCircleImageView(FragmentActivity fragmentActivity, String url, int placeholderResId, ImageView targetImageView);
}
