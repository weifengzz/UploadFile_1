package com.lhgj.wfz.uploadfiles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv1 = null;//上传的文件地址
    private TextView tv2 = null;//上传的文件名称
    private Button btn = null;//上传按钮
    private ImageView img = null;//图片
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
        btn = (Button) findViewById(R.id.btn);
        img = (ImageView) findViewById(R.id.iv);
    }
}
