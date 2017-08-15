package test_small.senble.china.com.libnetwork.request.ssl;

import com.sz.p2p.pjb.module.network.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okio.Buffer;

/**
 * Created by Administrator on 2017/6/9.
 */
public class SslHelper {
    private SSLSocketFactory mSslSocketFactory;
    private HostnameVerifier mHostnameVerifier;
    private X509TrustManager mTrustManager;

    public SslHelper(){
        mHostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //服务器证书的校验
                HostnameVerifier hostnameVerifier1 = HttpsURLConnection.getDefaultHostnameVerifier();
                if("debug".equals(BuildConfig.BUILD_TYPE)){
                    return true;
                }else{
                    return hostnameVerifier1.verify("www.pj.com",session) || hostnameVerifier1.verify("pj.com",session);
                }
            }
        };
        try {
            //trustedCertificatesInputStream()
            //getResources().openRawResource(R.raw.tomcat)
            mTrustManager = obtainTrustManager();
            SSLContext sslContext = SSLContext.getInstance("TLS");

            //取得KeyManagerFactory和TrustManagerFactory的X509密钥管理器实例
//            KeyManagerFactory keyManager = KeyManagerFactory.getInstance("x509");
//            //取得BKS密库实例
//            KeyStore kks= KeyStore.getInstance("BKS");
            //加客户端载证书和私钥,通过读取资源文件的方式读取密钥和信任证书
//            kks.load(getBaseContext()
//                    .getResources()
//                    .openRawResource(R.drawable.kclient),CLIENT_KET_PASSWORD.toCharArray());
//            tks.load(getBaseContext()
//                    .getResources()
//                    .openRawResource(R.drawable.lt_client),CLIENT_TRUST_PASSWORD.toCharArray());
            //初始化密钥管理器
//            keyManager.init(kks,"password".toCharArray());
            //keyManager.getKeyManagers()

            sslContext.init(null, new TrustManager[] { mTrustManager }, null);
            mSslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public SSLSocketFactory getSslSocketFactory(){
        return mSslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier(){
        return mHostnameVerifier;
    }

    public X509TrustManager getTrustManager(){
        return mTrustManager;
    }

    private X509TrustManager obtainTrustManager() throws GeneralSecurityException {
        if(mTrustManager==null){
            mTrustManager = trustManagerForCertificates(trustedCertificatesInputStream());
        }
        return mTrustManager;
    }

    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private InputStream trustedCertificatesInputStream() {
        // PEM files for root certificates of Comodo and Entrust. These two CAs are sufficient to view
        // https://publicobject.com (Comodo) and https://squareup.com (Entrust). But they aren't
        // sufficient to connect to most HTTPS sites including https://godaddy.com and https://visa.com.
        // Typically developers will need to get a PEM file from their organization's TLS administrator.
        String s = "-----BEGIN CERTIFICATE-----\n" +
                "MIIFKzCCBBOgAwIBAgIQfuFKb2/v8tN/P61lTTratDANBgkqhkiG9w0BAQsFADCB\n" +
                "yjELMAkGA1UEBhMCVVMxFzAVBgNVBAoTDlZlcmlTaWduLCBJbmMuMR8wHQYDVQQL\n" +
                "ExZWZXJpU2lnbiBUcnVzdCBOZXR3b3JrMTowOAYDVQQLEzEoYykgMjAwNiBWZXJp\n" +
                "U2lnbiwgSW5jLiAtIEZvciBhdXRob3JpemVkIHVzZSBvbmx5MUUwQwYDVQQDEzxW\n" +
                "ZXJpU2lnbiBDbGFzcyAzIFB1YmxpYyBQcmltYXJ5IENlcnRpZmljYXRpb24gQXV0\n" +
                "aG9yaXR5IC0gRzUwHhcNMTMxMDMxMDAwMDAwWhcNMjMxMDMwMjM1OTU5WjB3MQsw\n" +
                "CQYDVQQGEwJVUzEdMBsGA1UEChMUU3ltYW50ZWMgQ29ycG9yYXRpb24xHzAdBgNV\n" +
                "BAsTFlN5bWFudGVjIFRydXN0IE5ldHdvcmsxKDAmBgNVBAMTH1N5bWFudGVjIENs\n" +
                "YXNzIDMgRVYgU1NMIENBIC0gRzMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK\n" +
                "AoIBAQDYoWV0I+grZOIy1zM3PY71NBZI3U9/hxz4RCMTjvsR2ERaGHGOYBYmkpv9\n" +
                "FwvhcXBC/r/6HMCqo6e1cej/GIP23xAKE2LIPZyn3i4/DNkd5y77Ks7Imn+Hv9hM\n" +
                "BBUyydHMlXGgTihPhNk1++OGb5RT5nKKY2cuvmn2926OnGAE6yn6xEdC0niY4+wL\n" +
                "pZLct5q9gGQrOHw4CVtm9i2VeoayNC6FnpAOX7ddpFFyRnATv2fytqdNFB5suVPu\n" +
                "IxpOjUhVQ0GxiXVqQCjFfd3SbtICGS97JJRL6/EaqZvjI5rq+jOrCiy39GAI3Z8c\n" +
                "zd0tAWaAr7MvKR0juIrhoXAHDDQPAgMBAAGjggFdMIIBWTAvBggrBgEFBQcBAQQj\n" +
                "MCEwHwYIKwYBBQUHMAGGE2h0dHA6Ly9zMi5zeW1jYi5jb20wEgYDVR0TAQH/BAgw\n" +
                "BgEB/wIBADBlBgNVHSAEXjBcMFoGBFUdIAAwUjAmBggrBgEFBQcCARYaaHR0cDov\n" +
                "L3d3dy5zeW1hdXRoLmNvbS9jcHMwKAYIKwYBBQUHAgIwHBoaaHR0cDovL3d3dy5z\n" +
                "eW1hdXRoLmNvbS9ycGEwMAYDVR0fBCkwJzAloCOgIYYfaHR0cDovL3MxLnN5bWNi\n" +
                "LmNvbS9wY2EzLWc1LmNybDAOBgNVHQ8BAf8EBAMCAQYwKQYDVR0RBCIwIKQeMBwx\n" +
                "GjAYBgNVBAMTEVN5bWFudGVjUEtJLTEtNTMzMB0GA1UdDgQWBBQBWavn3ToLWaZk\n" +
                "Y9bPIAdX1ZHnajAfBgNVHSMEGDAWgBR/02Wnwt3su/AwCfNDOfoCrzMxMzANBgkq\n" +
                "hkiG9w0BAQsFAAOCAQEAQgFVe9AWGl1Y6LubqE3X89frE5SG1n8hC0e8V5uSXU8F\n" +
                "nzikEHzPg74GQ0aNCLxq1xCm+quvL2GoY/Jl339MiBKIT7Np2f8nwAqXkY9W+4nE\n" +
                "qLuSLRtzsMarNvSWbCAI7woeZiRFT2cAQMgHVHQzO6atuyOfZu2iRHA0+w7qAf3P\n" +
                "eHTfp61Vt19N9tY/4IbOJMdCqRMURDVLtt/JYKwMf9mTIUvunORJApjTYHtcvNUw\n" +
                "LwfORELEC5n+5p/8sHiGUW3RLJ3GlvuFgrsEL/digO9i2n/2DqyQuFa9eT/ygG6j\n" +
                "2bkPXToHHZGThkspTOHcteHgM52zyzaRS/6htO7w+Q==\n" +
                "-----END CERTIFICATE-----";
        return new Buffer()
                .writeUtf8(s)
                .inputStream();
    }
}
