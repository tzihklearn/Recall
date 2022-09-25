package com.example.recallbackend.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;


import java.io.InputStream;

/**
 * @author tzih
 * @date 2022.09.23
 */
public class QiniuUtil {


    //...生成上传凭证，然后准备上传
    private static final String accessKey = "xH_BWCsatoVzlN_dYz6c4SeRwDwFjMpLHr2RiskP";
    private static final String secretKey = "dnnGnEpmSl2fMpoGUZfs_Pt0Llql53VY81lv-DIK";
    private static final String bucket = "recall-huawu";

    //默认不指定key的情况下，以文件内容的hash值作为文件名
//    private static final String key = null;

    // domain   下载 domain, eg: qiniu.com【必须】
    private static final String domain = "qiniu.tzih.work";

    public static String UploadFiles(InputStream inputStream, String key) {

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huabei());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(inputStream, key, upToken,null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    public static String DownloadFiles(String key) throws QiniuException {
        boolean useHttps = true;
// useHttps 是否使用 https【必须】
// key      下载资源在七牛云存储的 key【必须】
        DownloadUrl url = new DownloadUrl(domain, useHttps, key);
//        url.setAttname(attname); // 配置 attname
//                .setFop(fop) // 配置 fop
//                .setStyle(style, styleSeparator, styleParam); // 配置 style
        //        System.out.println(urlString);
        return url.buildURL();
    }

    public static String DownloadFilesAndDownload(String attname, String key) throws QiniuException {
        boolean useHttps = true;
// useHttps 是否使用 https【必须】
// key      下载资源在七牛云存储的 key【必须】
        DownloadUrl url = new DownloadUrl(domain, useHttps, key);
        url.setAttname(attname); // 配置 attname
//                .setFop(fop) // 配置 fop
//                .setStyle(style, styleSeparator, styleParam); // 配置 style
        //        System.out.println(urlString);
        return url.buildURL();
    }


}
