package com.lhgj.wfz.uploadfiles.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * 读取文件的工具类
 * Created by WFZ on 2015/12/7.
 */
public class Fileutil {
	public static byte[] readFileToByteArray(File file) {
		FileInputStream fileInputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] bt = new byte[1024];
			int len = 0;
			while ((len = fileInputStream.read(bt)) != -1) {
				byteArrayOutputStream.write(bt, 0, len);
			}
			return byteArrayOutputStream.toByteArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
