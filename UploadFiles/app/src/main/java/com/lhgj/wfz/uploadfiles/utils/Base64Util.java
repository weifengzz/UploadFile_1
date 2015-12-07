package com.lhgj.wfz.uploadfiles.utils;

import android.util.Base64;

import java.io.File;

/**
 *得到经过处理的Base64字符串
 * Created by WFZ on 2015/12/7.
 */
public class Base64Util {
    public static String getBase64String(String path) {
        byte[] byteArray = null;
//        byteArray = Fileutil.readFileToByteArray(new File(
//                "/data/data/com.songximing.fileuploaddemo/lyf.mp4"));
        byteArray = Fileutil.readFileToByteArray(new File(path));
        String strBase64 = new String(Base64.encode(byteArray,0));
        return strBase64;
    }
}
