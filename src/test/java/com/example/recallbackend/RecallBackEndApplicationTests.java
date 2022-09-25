package com.example.recallbackend;

import com.example.recallbackend.utils.QiniuUtil;
import com.sun.media.sound.WaveFileReader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.net.ssl.HttpsURLConnection;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

@SpringBootTest
class RecallBackEndApplicationTests {

    @Test
    void contextLoads() throws IOException, UnsupportedAudioFileException {

//        String s = QiniuUtil.DownloadFiles("20220923_022848.wave");
//        System.out.println(s);
//        s = QiniuUtil.DownloadFilesAndDownload("20220923_022848.wave", "20220923_022848.wave");
//        URL url = new URL(s);
//        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
//        WaveFileReader waveFileReader = new WaveFileReader();
//        AudioFileFormat audioFileFormat = waveFileReader.getAudioFileFormat(bufferedInputStream);
//        System.out.println(audioFileFormat.getFrameLength());

//        throw new NullPointerException();
    }

}
