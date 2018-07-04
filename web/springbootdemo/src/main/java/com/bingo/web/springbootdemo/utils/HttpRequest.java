///*
//package com.bingo.web.springbootdemo.utils;
//
//import cn.richinfo.common.enumeration.Encoding;
//import cn.richinfo.common.enumeration.HttpMethod;
//
//import javax.net.ssl.*;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.Charset;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.Map;
//
//public class HttpRequest {
//    private static int connectTimeout = 20000;
//    private static int timeout = 20000;
//
//    private static String defaultContentEncoding;
//    static HostnameVerifier sslHostnameVerifier;
//
//    static synchronized void initSslHostnameVerifier() {
//        if (sslHostnameVerifier != null) {
//            return;
//        }
//        sslHostnameVerifier = new HostnameVerifier() {
//            @Override
//            public boolean verify(String urlHostName, SSLSession session) {
//                return urlHostName != null
//                        && urlHostName.equals(session.getPeerHost());
//            }
//        };
//    }
//
//    static SSLSocketFactory sslSocketFactory;
//
//    */
///**
//     * 忽略SSL证书
//     *//*
//
//    static synchronized void initSslSocketFactory() {
//        if (sslSocketFactory != null) {
//            return;
//        }
//        InputStream in = null;
//        try {
//            SSLContext context = SSLContext.getInstance("TLS");
//            final X509TrustManager trustManager = new X509TrustManager() {
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//
//                @Override
//                public void checkClientTrusted(X509Certificate[] chain,
//                                               String authType) throws CertificateException {
//                }
//
//                @Override
//                public void checkServerTrusted(X509Certificate[] chain,
//                                               String authType) throws CertificateException {
//
//                }
//
//            };
//            context.init(null, new TrustManager[]{trustManager}, null);
//            sslSocketFactory = context.getSocketFactory();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    static {
//
//        defaultContentEncoding = Charset.defaultCharset().name();
//
//    }
//
//    */
///**
//     * @param urlString
//     * @param httpMethod
//     * @return
//     * @throws Exception
//     *//*
//
//    public static HttpResponse GetResponse(String urlString,
//
//                                           HttpMethod httpMethod, Encoding encoding) throws Exception {
//        return GetResponse(urlString, null, null, httpMethod, encoding);
//
//    }
//
//    */
///**
//     * @param urlString
//     * @param parameters
//     * @param httpMethod
//     * @return
//     * @throws Exception
//     *//*
//
//    public static HttpResponse GetResponse(String urlString,
//                                           Map<String, String> parameters, HttpMethod httpMethod,
//                                           Encoding encoding) throws Exception {
//        return GetResponse(urlString, parameters, null, httpMethod, encoding);
//
//    }
//
//    */
///**
//     * @param urlString
//     * @param parameters
//     * @param headparameters
//     * @param httpMethod
//     * @return
//     * @throws Exception
//     *//*
//
//    public static HttpResponse GetResponse(String urlString,
//                                           Map<String, String> parameters, Map<String, String> headparameters,
//                                           HttpMethod httpMethod, Encoding encoding) throws Exception {
//
//        defaultContentEncoding = encoding.toString();
//        if (encoding == Encoding.UTF8) {
//            defaultContentEncoding = "UTF-8";
//        }
//        HttpURLConnection urlConnection = null;
//        if (httpMethod == HttpMethod.GET) {
//            if (parameters != null) {
//                StringBuffer param = new StringBuffer();
//                int i = 0;
//                for (String key : parameters.keySet()) {
//                    if (i == 0)
//                        param.append("?");
//                    else
//                        param.append("&");
//                    param.append(key).append("=").append(parameters.get(key));
//                    i++;
//                }
//                urlString += param;
//            }
//        }
//        URL url = new URL(urlString);
//        urlConnection = (HttpURLConnection) url.openConnection();
//
//        // 设置 URL 请求的方法，
//        urlConnection.setRequestMethod(httpMethod.toString());
//
//        // 启用缓存
//        urlConnection.setUseCaches(false);
//
//        if (headparameters != null)
//            for (String key : headparameters.keySet()) {
//                urlConnection.addRequestProperty(key, headparameters.get(key));
//            }
//
//        if (httpMethod == HttpMethod.POST) {
//
//            urlConnection.setDoOutput(true);
//            urlConnection.setDoInput(true);
//
//            if (parameters != null) {
//                StringBuffer param = new StringBuffer();
//                for (String key : parameters.keySet()) {
//                    param.append("&");
//                    param.append(key).append("=").append(parameters.get(key));
//                }
//
//                urlConnection.getOutputStream().write(
//                        param.toString().getBytes());
//            }
//            urlConnection.getOutputStream().flush();
//            urlConnection.getOutputStream().close();
//        }
//        urlConnection.setReadTimeout(timeout);
//        urlConnection.setConnectTimeout(connectTimeout);
//
//        if ("https".equalsIgnoreCase(urlString.substring(0, 5))) {
//            if (sslSocketFactory == null) {
//                initSslSocketFactory();
//            }
//            ((HttpsURLConnection) urlConnection)
//
//                    .setSSLSocketFactory(sslSocketFactory);
//            if (sslHostnameVerifier == null) {
//                initSslHostnameVerifier();
//            }
//            ((HttpsURLConnection) urlConnection)
//                    .setHostnameVerifier(sslHostnameVerifier);
//        }
//
//        return makeContent(urlConnection);
//
//    }
//
//    */
///**
//     * 得到响应对象
//     *
//     * @paramurlConnection
//     * @return响应对象
//     * @throwsIOException
//     *//*
//
//    private static HttpResponse makeContent(HttpURLConnection urlConnection)
//            throws IOException {
//
//        HttpResponse httpResponser = new HttpResponse();
//        try {
//            int statusCode = urlConnection.getResponseCode();
//            if (statusCode == 200 || statusCode == 302 || statusCode == 301) {
//                InputStream in = urlConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(
//                        new InputStreamReader(in, defaultContentEncoding));
//                StringBuffer code = new StringBuffer();
//                String line = bufferedReader.readLine();
//                while (line != null) {
//                    code.append(line).append("/r/n");
//                    line = bufferedReader.readLine();
//                }
//                bufferedReader.close();
//
//                String ecod = urlConnection.getContentEncoding();
//                if (ecod == null)
//                    ecod = defaultContentEncoding;
//
//                httpResponser.setCode(code.toString());
//
//                httpResponser.setEncoding(ecod);
//            }
//            httpResponser.setStatusCode(urlConnection.getResponseCode());
//
//            httpResponser.setReadTimeout(urlConnection.getConnectTimeout());
//            httpResponser.setConnectTimeout(urlConnection.getReadTimeout());
//
//            return httpResponser;
//        } catch (IOException e) {
//            throw e;
//        } finally {
//            if (urlConnection != null)
//                urlConnection.disconnect();
//        }
//    }
//}*/
