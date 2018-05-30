package com.hxy.util.http;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * 此类用做http请求
 * Created by HUANGXIYAO on 2018/1/6.
 */
public class HttpClientUtils {

   static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static boolean ignoreSSLCheck = true; // 忽略SSL检查
    private static boolean ignoreHostCheck = true; // 忽略HOST检查
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_ENCODING_GZIP = "gzip";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    /**
     * 读取超时
     */
    public static final int READTIME_OUT = 60;
    /**
     * 连接超时
     */
    public static final int CONNECTTIME_OUT = 10;


    public static class TrustAllTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private HttpClientUtils() {
    }

    public static void setIgnoreSSLCheck(boolean ignoreSSLCheck) {
        HttpClientUtils.ignoreSSLCheck = ignoreSSLCheck;
    }

    public static void setIgnoreHostCheck(boolean ignoreHostCheck) {
        HttpClientUtils.ignoreHostCheck = ignoreHostCheck;
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url 请求地址
     * @param params 请求参数
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String, Object> params, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url 请求地址
     * @param params 请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     */
   public static String doPost(String url, Map<String, Object> params, String charset, int connectTimeout, int readTimeout) throws IOException {
       String ctype = "application/x-www-form-urlencoded;charset=" + charset;
       String query = buildQuery(params, charset);
       byte[] content = {};
       if (query != null) {
           content = query.getBytes(charset);
       }
        return _doPost(url, ctype, content, connectTimeout, readTimeout);
    }

    public static String doPost(String url, String apiBody, String charset, int connectTimeout, int readTimeout) throws IOException {
        String ctype = "application/json;charset=" + charset;
        byte[] content = apiBody.getBytes(charset);
        return _doPost(url, ctype, content, connectTimeout, readTimeout);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url 请求地址
     * @param ctype 请求类型
     * @param content 请求字节数组
     * @return 响应字符串
     */
    public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout) throws IOException {
        return _doPost(url, ctype, content, connectTimeout, readTimeout);
    }


    /**
     * post基础类方法
     * @param url
     * @param ctype
     * @param content
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    private static String _doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            //获取post连接
            conn = getConnection(new URL(url), METHOD_POST, ctype);
            conn.setRequestProperty("", "");
            conn.setConnectTimeout(connectTimeout * 1000);
            conn.setReadTimeout(readTimeout * 1000);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    /**
     * 获取connetion
     * @param url
     * @param method
     * @param ctype
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getConnection(URL url, String method, String ctype) throws IOException {
        logger.info("查询请求参数：url:{}",url.toString());
        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection connHttps = (HttpsURLConnection) conn;
            if (ignoreSSLCheck) {
                try {
                    SSLContext ctx = SSLContext.getInstance("TLS");
                    ctx.init(null, new TrustManager[] { new HttpClientUtils.TrustAllTrustManager() }, new SecureRandom());
                    connHttps.setSSLSocketFactory(ctx.getSocketFactory());
                    connHttps.setHostnameVerifier(new HostnameVerifier() {
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
                } catch (Exception e) {
                    logger.error("http请求发生异常",e);
                    throw new IOException(e.toString());
                }
            } else {
                if (ignoreHostCheck) {
                    connHttps.setHostnameVerifier(new HostnameVerifier() {
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
                }
            }
            conn = connHttps;
        }
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "text/xml,text/javascript,application/json");
        conn.setRequestProperty("Content-Type", ctype);
        return conn;
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url 请求地址
     * @param params 请求参数
     * @return 响应字符串
     */
    public static String doGet(String url, Map<String, Object> params,String charset) throws IOException {
        return doGet(url, params, charset,CONNECTTIME_OUT,READTIME_OUT);
    }

    /**
     * 执行HTTP GET请求。
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static String doGet(String url, Map<String, Object> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET,CONNECTTIME_OUT,READTIME_OUT);
    }

    /**
     * 执行HTTP GET请求。
     * @param url
     * @param params
     * @param charset 编码
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读取超时时间
     * @return
     * @throws IOException
     */
    public static String doGet(String url, Map<String, Object> params, String charset, int connectTimeout, int readTimeout) throws IOException {
        return _doGet(url, params, charset,connectTimeout,readTimeout);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url 请求地址
     * @param params 请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读取数据超时时间
     * @return 响应字符串
     */
    public static String _doGet(String url, Map<String, Object> params, String charset, int connectTimeout, int readTimeout) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;
        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params, charset);
            conn = getConnection(buildGetUrl(url, query), METHOD_GET, ctype);
            if(connectTimeout != 0){
                conn.setConnectTimeout(connectTimeout * 1000);
            }
            if(readTimeout != 0){
                conn.setReadTimeout(readTimeout * 1000);
            }

            rsp = getResponseAsString(conn);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }


    /**
     * 获取返回字符串
     * @param conn
     * @return
     * @throws IOException
     */
    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
            String contentEncoding = conn.getContentEncoding();
            if (CONTENT_ENCODING_GZIP.equalsIgnoreCase(contentEncoding)) {
                return getStreamAsString(new GZIPInputStream(conn.getInputStream()), charset);
            } else {
                return getStreamAsString(conn.getInputStream(), charset);
            }
        } else {
                InputStream error = conn.getErrorStream();
                if (error != null) {
                    return getStreamAsString(error, charset);
                }
            // Client Error 4xx and Server Error 5xx
            logger.info("请求返回结果转换失败：responseCode:{}，message:{}",conn.getResponseCode() , conn.getResponseMessage());
            throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
        }
    }

    public static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();
            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }
            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    //获取返回编码格式
    public static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;
        if (!StringUtils.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!StringUtils.isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    private static URL buildGetUrl(String url, String query) throws IOException {
        if (StringUtils.isEmpty(query)) {
            return new URL(url);
        }

        return new URL(buildRequestUrl(url, query));
    }

    public static String buildRequestUrl(String url, String... queries) {
        if (queries == null || queries.length == 0) {
            return url;
        }
        StringBuilder newUrl = new StringBuilder(url);
        boolean hasQuery = url.contains("?");
        boolean hasPrepend = url.endsWith("?") || url.endsWith("&");

        for (String query : queries) {
            if (!StringUtils.isEmpty(query)) {
                if (!hasPrepend) {
                    if (hasQuery) {
                        newUrl.append("&");
                    } else {
                        newUrl.append("?");
                        hasQuery = true;
                    }
                }
                newUrl.append(query);
                hasPrepend = false;
            }
        }
        return newUrl.toString();
    }

    public static String buildQuery(Map<String, Object> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        boolean hasParam = false;

        for (Map.Entry<String, Object> entry : entries) {
            String name = entry.getKey();
            Object value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (!(value == null || "".equals(value)) && StringUtils.isNotEmpty(name)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                query.append(name).append("=").append(URLEncoder.encode(String.valueOf(value), charset));
            }
        }
        return query.toString();
    }
    /**
     * 使用默认的UTF-8字符集反编码请求参数值。
     *
     * @param value 参数值
     * @return 反编码后的参数值
     */
    public static String decode(String value) {
        return decode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用默认的UTF-8字符集编码请求参数值。
     *
     * @param value 参数值
     * @return 编码后的参数值
     */
    public static String encode(String value) {
        return encode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用指定的字符集反编码请求参数值。
     *
     * @param value 参数值
     * @param charset 字符集
     * @return 反编码后的参数值
     */
    public static String decode(String value, String charset) {
        String result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = URLDecoder.decode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 使用指定的字符集编码请求参数值。
     *
     * @param value 参数值
     * @param charset 字符集
     * @return 编码后的参数值
     */
    public static String encode(String value, String charset) {
        String result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = URLEncoder.encode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 从URL中提取所有的参数。
     *
     * @param query URL地址
     * @return 参数映射
     */
    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap<String, String>();

        String[] pairs = query.split("&");
        if (pairs != null && pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }

        return result;
    }
}
