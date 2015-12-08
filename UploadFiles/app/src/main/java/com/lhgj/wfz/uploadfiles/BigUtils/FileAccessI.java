package com.lhgj.wfz.uploadfiles.BigUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 * Created by WFZ on 2015/12/8.
 */
public class FileAccessI  {
    RandomAccessFile oSavedFile;
    long nPos;

    public FileAccessI() throws IOException {
        this("", 0);
    }

    public FileAccessI(String sName, long nPos) throws IOException {
        oSavedFile = new RandomAccessFile(sName, "rw");//创建一个随机访问文件类，可读写模式
        this.nPos = nPos;
        oSavedFile.seek(nPos);//将指针指到文件的nPos处
    }

   /**
    * 写文件，
    * 写b数组长的文件，
    * 从哪个地方开始写，
    * 写的长度
    * 返回写的长度
    * */
    public synchronized int write(byte[] b, int nStart, int nLen) {
        int n = -1;
        try {
            oSavedFile.write(b, nStart, nLen);
            n = nLen;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return n;
    }

    /*
    * 读取文件
    * 每次读取102400字节
    * */
    public synchronized Detail getContent(long nStart) {
        Detail detail = new Detail();
        detail.b = new byte[102400];
        try {
            oSavedFile.seek(nStart);
            detail.length = oSavedFile.read(detail.b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * 读取文件的信息
     * */
    public class Detail {
        public byte[] b;//读取文件的存放数据的byte数组
        public int length;//读取的文件的长度
    }

    //获取文件长度
    public long getFileLength() {
        Long length = 0L;
        try {
            length = oSavedFile.length();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return length;
    }
}
