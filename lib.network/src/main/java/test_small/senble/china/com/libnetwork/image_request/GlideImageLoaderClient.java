package test_small.senble.china.com.libnetwork.image_request;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import test_small.senble.china.com.libnetwork.image_request.transform.GlideCircleImageTransform;
import test_small.senble.china.com.libnetwork.image_request.transform.GlideRoundCornerImageTransform;

/**
 * Created by Administrator on 2017/4/14.
 */
class GlideImageLoaderClient implements IImageLoaderClient {

    @Override
    public void loadImage(Fragment fragment, String url, int placeholderResId, ImageView targetImageView) {
        Glide.with(fragment).load(url).crossFade().centerCrop().placeholder(placeholderResId).into(targetImageView);
    }

    @Override
    public void loadRoundCornerImage(Fragment fragment, String url, int placeholderResId, ImageView targetImageView, final int cornerSize) {
        Glide.with(fragment).load(url).crossFade()
                .placeholder(placeholderResId)
                .transform(new GlideRoundCornerImageTransform(fragment.getActivity(), cornerSize))
                .into(targetImageView);
    }

    @Override
    public void loadCircleImageView(Fragment fragment, String url, int placeholderResId, ImageView targetImageView) {
        Glide.with(fragment).load(url).crossFade()
                .transform(new GlideCircleImageTransform(fragment.getActivity()))
                .placeholder(placeholderResId)
                .into(targetImageView);
    }

    @Override
    public void loadImage(FragmentActivity fragmentActivity, String url, int placeholderResId, ImageView targetImageView) {
        Glide.with(fragmentActivity).load(url).crossFade().placeholder(placeholderResId).into(targetImageView);
    }

    @Override
    public void loadRoundCornerImage(FragmentActivity fragmentActivity, String url, int placeholderResId, ImageView targetImageView, int cornerSize) {
        Glide.with(fragmentActivity).load(url).crossFade()
                .placeholder(placeholderResId)
                .transform(new GlideRoundCornerImageTransform(fragmentActivity, cornerSize))
                .into(targetImageView);
    }

    @Override
    public void loadCircleImageView(FragmentActivity fragmentActivity, String url, int placeholderResId, ImageView targetImageView) {
        Glide.with(fragmentActivity).load(url).crossFade()
                .placeholder(placeholderResId)
                .transform(new GlideCircleImageTransform(fragmentActivity))
                .into(targetImageView);
    }
}
