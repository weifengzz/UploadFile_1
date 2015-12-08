package com.lhgj.wfz.uploadfiles.BigUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 * Created by WFZ on 2015/12/8.
 */
public class BigRandomAccessFile implements Serializable {

    RandomAccessFile randomAccessFile = null;
    long nPos;//从文件的；哪一个地方开始读

    public BigRandomAccessFile(String fName, long nPos) throws IOException {
        randomAccessFile = new RandomAccessFile(fName, "rw");//创建一个随机访问文件类，可读写模式
        this.nPos = nPos;
        randomAccessFile.seek(nPos);
    }

    /**
     * 写文件，
     * 写b数组长的文件，
     * 从哪个地方开始写，
     * 写的长度
     * 返回写的长度
     */
    public synchronized int write(byte[] b, int nStart, int nLen) {
        int n = -1;
        try {
            randomAccessFile.write(b, nStart, nLen);
            n = nLen;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return n;
    }

    /**
     * 读取文件
     * 每次读取102400字节
     */
    public synchronized Detail getContent(long nStart) {
        Detail detail = new Detail();
        detail.b = new byte[102400];
        try {
            randomAccessFile.seek(nStart);
            detail.length = randomAccessFile.read(detail.b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * 读取文件的信息
     */
    public class Detail {
        public byte[] b;//读取文件的存放数据的byte数组
        public int length;//读取的文件的长度
    }

    /**
     * 获取文件长度
     */
    public long getFileLength() {
        Long length = 0L;
        try {
            length = randomAccessFile.length();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return length;
    }

}
