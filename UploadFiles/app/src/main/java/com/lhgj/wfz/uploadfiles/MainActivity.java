package com.lhgj.wfz.uploadfiles;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lhgj.wfz.uploadfiles.BigUtils.BigBase64Util;
import com.lhgj.wfz.uploadfiles.BigUtils.BigRandomAccessFile;
import com.lhgj.wfz.uploadfiles.BigUtils.FileAccessI;
import com.lhgj.wfz.uploadfiles.utils.Base64Util;
import com.lhgj.wfz.uploadfiles.utils.Fileutil;
import com.lhgj.wfz.uploadfiles.utils.QueryUploadUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView tv1 = null;//上传的文件地址
    private TextView tv2 = null;//上传的文件名称
    private TextView tv3 = null;//上传是否成功提示
    private Button btn = null;//上传小图片按钮
    private ImageView img = null;//图片
    private Button bigBtn = null;//上传大图片的按钮
    int i = 0;

    private String filePath = "/data/data/com.lhgj.wfz.uploadfiles/";//手机中文件存储的位置
    private String fileName = "temp.jpg";//上传的图片
    private String bigFileName = "lyf.mp4";//上传的图片
    private String wsdl = "http://192.168.15.4:1122/service/vedios/GetVedios.asmx?WSDL";//WSDL
    private String url = "http://192.168.15.4:1122/service/vedios/GetVedios.asmx/FileUploadByBase64String";//上传小文件与webservice交互的地址
    private String bigUrl = "http://192.168.15.4:1122/service/vedios/GetVedios.asmx/BigFileUploadByBase64String";//上传大文件与webservice交互的地址

    /**
     * 由于上传文件是一个耗时操作，需要开一个异步，这里我们使用handle
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            String string = (String) msg.obj;
            Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG).show();
            tv3.setText(string + i++);
            if(!string.equals("上传失败")){
                upBigFile();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

        /**
         * 初始化view
         */
    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        btn = (Button) findViewById(R.id.btn);
        img = (ImageView) findViewById(R.id.iv);
        bigBtn = (Button) findViewById(R.id.big_btn);

        //设置显示的图片
        byte[] byteArray = null;
        byteArray = Fileutil.readFileToByteArray(new File(filePath + fileName));
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                byteArray.length);
        img.setImageBitmap(bitmap);

        //设置显示的文本
        tv1.setText("文件位置：" + filePath);
        tv2.setText("文件名称" + fileName);

        btn.setOnClickListener(new BtnOnclickListener());
        bigBtn.setOnClickListener(new BtnOnclickListener());
    }

    /**
     * ImageView的事件响应
     */
    private class BtnOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn://当是上传小文件的时候
                    upSmallFile();
                    break;
                case R.id.big_btn://当上传大文件的时候
                    upBigFile();
                    break;
            }

        }
    }

    /**
     * 上传小文件
     */
    public void upSmallFile() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message message = Message.obtain();
                String result = "";
                try {
                    QueryUploadUtil quu = new QueryUploadUtil(Base64Util.getBase64String(filePath + fileName), "temp.jpg");
                    result = quu.call(wsdl, url,"FileUploadByBase64String");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                message.obj = result;
                handler.sendMessage(message);
            }
        }).start();
    }

    /**
     * 上传大文件
     */
    public void upBigFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BigRandomAccessFile bigRandomAccessFile = new BigRandomAccessFile(filePath + bigFileName, 0);
                    long startPos = 0L;
                    Long length = bigRandomAccessFile.getFileLength();//得到文件的长度
                    int mBufferSize = 1024 * 100; //每次处理1024 * 100字节
                    byte[] buffer = new byte[mBufferSize];//创建一个mBufferSize大小的缓存数组
                    BigRandomAccessFile.Detail detail;//文件的详情类
                    long nRead = 0l;//读取文件的当前长度
                    String vedioFileName = fileName; //分配一个文件名
                    long nStart = startPos;//开始读的位置
                    int i = 0;
                   // while (nStart < length) {
                        detail = bigRandomAccessFile.getContent(startPos);//开始读取文件
                        nRead = detail.length;//读取的文件的长度
                        buffer = detail.b;//读取文件的缓存
                        Message message = Message.obtain();
                        String result = "";//上传的结果
                        try {
                            QueryUploadUtil quu = new QueryUploadUtil(BigBase64Util.getBase64String(buffer), bigFileName);
                            result = quu.call(wsdl, bigUrl,"BigFileUploadByBase64String");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    nStart += nRead;
                    startPos = nStart;
                    message.obj = result;
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendMessage(message);
                  //  }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
