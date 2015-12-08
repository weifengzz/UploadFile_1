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

import com.lhgj.wfz.uploadfiles.utils.Base64Util;
import com.lhgj.wfz.uploadfiles.utils.Fileutil;
import com.lhgj.wfz.uploadfiles.utils.QueryUploadUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private TextView tv1 = null;//上传的文件地址
    private TextView tv2 = null;//上传的文件名称
    private TextView tv3 = null;//上传是否成功提示
    private Button btn = null;//上传按钮
    private ImageView img = null;//图片
    private  String filePath="/data/data/com.lhgj.wfz.uploadfiles/";//手机中文件存储的位置
    private String fileName ="temp.jpg";//上传的图片
    private String wsdl ="http://192.168.15.4:1122/service/vedios/GetVedios.asmx?WSDL";//WSDL
    private String url ="http://192.168.15.4:1122/service/vedios/GetVedios.asmx/FileUploadByBase64String";//与webservice交互的地址

    /**
     * 由于上传文件是一个耗时操作，需要开一个异步，这里我们使用handle
     * */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            String string = (String) msg.obj;
            Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG).show();;
            tv3.setText(string);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化view
     * */
    private void initView(){
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        btn = (Button) findViewById(R.id.btn);
        img = (ImageView) findViewById(R.id.iv);

        //设置显示的图片
        byte[] byteArray = null;
        byteArray = Fileutil.readFileToByteArray(new File(filePath+fileName));
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                byteArray.length);
        img.setImageBitmap(bitmap);

        //设置显示的文本
        tv1.setText("文件位置：" + filePath);
        tv2.setText("文件名称" + fileName);

        btn.setOnClickListener(new BtnOnclickListener());
    }

    /**
     * ImageView的事件响应
     * */
    private class BtnOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Message message = Message.obtain();
                    String result="";
                    try {
                        QueryUploadUtil quu = new QueryUploadUtil(Base64Util.getBase64String(filePath+fileName), "temp.png");
                        result= quu.call(wsdl,wsdl);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    message.obj=result;
                    handler.sendMessage(message);
                }
            }).start();
        }
    }
}
