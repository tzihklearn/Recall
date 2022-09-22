package com.example.recallbackend.utils;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;

import java.awt.*;
import java.nio.charset.StandardCharsets;

/**
 * @author tzih
 * @date 2022.09.19
 */
public class MyQRCodeUtil {

    private static final Integer width = 300;
    private static final Integer height = 300;
    private static final Integer margin = 3;

    public static String getQRCode(String md5Str) {

        QrConfig qrConfig = new QrConfig();

        qrConfig.setWidth(width).setHeight(height).setMargin(margin);

        qrConfig.setForeColor(Color.BLACK).setBackColor(Color.WHITE);

        qrConfig.setCharset(StandardCharsets.UTF_8);

        String png = QrCodeUtil.generateAsBase64(md5Str, qrConfig, "png");

        return png;
    }

}
