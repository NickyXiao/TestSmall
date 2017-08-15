package test_small.senble.china.com.libnetwork.image_request;


/**
 * Created by Administrator on 2017/4/14.
 */
public class ImageLoaderFactory {
    public static IImageLoaderClient getImageLoaderClient(){
        return new GlideImageLoaderClient();
    }
}
