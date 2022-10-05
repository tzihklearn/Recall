package com.example.recallbackend.utils;

import com.example.recallbackend.mapper.ScheduleMapper;
import com.example.recallbackend.mapper.TimeTableMapper;
import com.example.recallbackend.pojo.dto.param.NewlyBuildParam;
import com.example.recallbackend.pojo.dto.param.SubmitVideoParam;
import com.example.recallbackend.pojo.po.VideoPo;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.sun.media.sound.WaveFileReader;
import com.sun.media.sound.WaveFloatFileReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author tzih
 * @date 2022.09.23
 */

@Slf4j
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

    public static boolean setVideo(ScheduleMapper scheduleMapper, MultipartFile multipartFile,
                                   Integer userId, String data) {


        try {
            //注：上传文件之后流会被关闭
            assert multipartFile != null;
            InputStream inputStream = multipartFile.getInputStream();

            log.info("获取音频文件帧率");
            WaveFileReader waveFileReader = new WaveFileReader();

            log.info("获取音频文件播放时间");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            AudioFileFormat audioFileFormat = waveFileReader.getAudioFileFormat(bufferedInputStream);

            int length = (int) ( (double)audioFileFormat.getFrameLength() / audioFileFormat.getFormat().getFrameRate());
//            int length = 6;

            log.info("将文件上传");
            log.info("以当前时间戳为文件名");
            String key = userId + TimeUtils.getNowTime() + ".wav";
            String filesName = QiniuUtil.UploadFiles(inputStream, key);

            if (filesName == null) {
                log.warn("未获得文件名，文件上传失败");
                return false;
            }

            log.info("关闭流");
//            bufferedInputStream.close();

            //
            log.info("将音频文件信息放入数据库");

            log.info("获取音频播放链接");
            String videoUrl = QiniuUtil.DownloadFiles(filesName);

            VideoPo videoPo = new VideoPo();

            videoPo.setUserId(userId);
            videoPo.setData(data);
            videoPo.setVideoUrl(videoUrl);
            videoPo.setLength(length);

            int r;
            if (data != null) {
                r = scheduleMapper.insertVideoByUserId(videoPo);
            }
            else {
                r = scheduleMapper.insertVideoNoDataByUserId(videoPo);
            }

            return r != 0;

        } catch (IOException e) {
            log.warn("数据流获取异常");
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            log.info("文件不包含可识别文件类型和格式的有效数据");
            throw new RuntimeException(e);
        }
    }

//    public static boolean Memorandum(ScheduleMapper scheduleMapper, MultipartHttpServletRequest multipartHttpServletRequest,
//                              NewlyBuildParam newlyBuildParam, TimeTableMapper timeTableMapper) {
//        SubmitVideoParam submitVideoParam = new SubmitVideoParam();
//
//        submitVideoParam.setUserId(newlyBuildParam.getUserId());
//        submitVideoParam.setData(newlyBuildParam.getData());
//
//        MultipartFile multipartFile = multipartHttpServletRequest.getFile("video");
//
//        String videoUrl;
//        try {
//            log.info("将文件上传");
//            assert multipartFile != null;
//            InputStream inputStream = multipartFile.getInputStream();
//            log.info("以当前时间戳为文件名");
//            String key = submitVideoParam.getUserId() + TimeUtils.getNowTime() + ".wav";
//            String filesName = QiniuUtil.UploadFiles(inputStream, key);
//
//            if (filesName == null) {
//                log.warn("未获得文件名，文件上传失败");
//                return false;
//            }
//
//            log.info("获取音频播放链接");
//            videoUrl = QiniuUtil.DownloadFiles(filesName);
//
//            log.info("获取音频文件帧率");
//            WaveFileReader waveFileReader = new WaveFileReader();
//
//            //
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//
//            AudioFileFormat audioFileFormat = waveFileReader.getAudioFileFormat(bufferedInputStream);
//
//            int length = (int) ( (double)audioFileFormat.getFrameLength() / audioFileFormat.getFormat().getFrameRate());
//
//            VideoPo videoPo = new VideoPo();
//
//            videoPo.setUserId(submitVideoParam.getUserId());
//            videoPo.setData(submitVideoParam.getData());
//            videoPo.setVideoUrl(videoUrl);
//            videoPo.setLength(length);
//
//            int i = scheduleMapper.insertVideoByUserId(videoPo);
//
//
//        } catch (IOException e) {
//            log.warn("数据流获取异常");
//            throw new RuntimeException(e);
//        } catch (UnsupportedAudioFileException e) {
//            throw new RuntimeException(e);
//        }
//        int i = timeTableMapper.insertMemorandum(newlyBuildParam, videoUrl);
//
//        return i != 0;
//    }

    public static boolean setVideo(ScheduleMapper scheduleMapper, MultipartFile multipartFile,
                                   Integer userId, String data, Integer length) {


        try {
            //注：上传文件之后流会被关闭
            assert multipartFile != null;
            InputStream inputStream = multipartFile.getInputStream();

//            log.info("获取音频文件帧率");
//            WaveFileReader waveFileReader = new WaveFileReader();
//
//            log.info("获取音频文件播放时间");
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//
//            AudioFileFormat audioFileFormat = waveFileReader.getAudioFileFormat(bufferedInputStream);
//
//            int length = (int) ( (double)audioFileFormat.getFrameLength() / audioFileFormat.getFormat().getFrameRate());
//            int length = 6;

            log.info("将文件上传");
            log.info("以当前时间戳为文件名");
            String key = userId + TimeUtils.getNowTime() + ".wav";
            String filesName = QiniuUtil.UploadFiles(inputStream, key);

            if (filesName == null) {
                log.warn("未获得文件名，文件上传失败");
                return false;
            }

            log.info("关闭流");
//            bufferedInputStream.close();

            //
            log.info("将音频文件信息放入数据库");

            log.info("获取音频播放链接");
            String videoUrl = QiniuUtil.DownloadFiles(filesName);

            VideoPo videoPo = new VideoPo();

            videoPo.setUserId(userId);
            videoPo.setVideoUrl(videoUrl);
            videoPo.setLength(length);

            int r;
            if (data != null) {
                videoPo.setData(data);
                r = scheduleMapper.insertVideoByUserId(videoPo);
            }
            else {
                r = scheduleMapper.insertVideoNoDataByUserId(videoPo);
            }

            return r != 0;

        } catch (IOException e) {
            log.warn("数据流获取异常");
            throw new RuntimeException(e);
        }
    }

}
