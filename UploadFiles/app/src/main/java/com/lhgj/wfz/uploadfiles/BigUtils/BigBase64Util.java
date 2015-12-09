package com.lhgj.wfz.uploadfiles.BigUtils;

import android.util.Base64;
import com.lhgj.wfz.uploadfiles.utils.Fileutil;
import java.io.File;

/**
 *得到经过处理的Base64字符串
 */
public class BigBase64Util {
    public static String getBase64String( byte[] byteArray) {
        String strBase64 = new String(Base64.encode(byteArray,0));
        return strBase64;
    }
}
